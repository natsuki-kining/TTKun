package com.natsuki_kining.ttkun.context;

import com.natsuki_kining.ttkun.context.annotation.*;
import com.natsuki_kining.ttkun.context.bean.factory.AnnotationBeanFactory;
import com.natsuki_kining.ttkun.context.bean.reader.BeanDefinitionReader;
import com.natsuki_kining.ttkun.context.bean.wrapper.AnnotationBeanWrapper;
import com.natsuki_kining.ttkun.context.bean.wrapper.BeanWrapper;
import com.natsuki_kining.ttkun.context.utils.DateUtil;
import com.natsuki_kining.ttkun.context.variables.ProjectVariables;
import com.natsuki_kining.ttkun.context.variables.SystemVariables;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * ApplicationContext
 *
 * @Author natsuki_kining
 * @Date 2019/12/15 17:18
 * @UpdateDate 2020/1/4 16:25:00
 * @Version 1.1.0
 **/
@Slf4j
public class ApplicationContext {

    public static ApplicationContext getInstance() {
        return applicationContextInstance;
    }

    private static ApplicationContext applicationContextInstance = new ApplicationContext();

    //IOC容器
    private Map<Class, BeanWrapper> inversionOfControlMap = new ConcurrentHashMap<>();

    private String[] properties;
    private String[] scanPackages;
    private BeanDefinitionReader reader;
    private String[] args;
    private List<Class<?>> hasComponentAnnotationClasses;
    private List<String> applicationFiles;

    private ApplicationContext() {
    }

    /**
     * 初始化容器
     *
     * @param applicationClass
     * @param args
     */
    public void init(Class<?> applicationClass, String[] args) throws Exception {
        log.info("开始初始化容器……");
        loadApplicationFiles();
        initContext(applicationClass, args);
        initProperties();
        doScanner();
        registerBean();
        initBeanFieldsByValue();
        initBeanFieldsByAutowired();
        runAnnotationMethod(RunInit.class);
        log.info("初始化容器完成……");
    }

    /**
     * 调用含有run注解的方法
     */
    public void run() {
        runAnnotationMethod(Run.class);
    }

    /**
     * 通过class 获取bean
     *
     * @param beanClass
     * @param <T>
     * @return
     */
    public <T> T getBean(Class<T> beanClass) {
        BeanWrapper beanWrapper = inversionOfControlMap.get(beanClass);
        if (beanWrapper == null) {
            return null;
        }
        return (T) beanWrapper.getBeanInstance();
    }

    /**
     * 通过beanName获取bean
     *
     * @param beanName
     * @return
     */
    public Object getBean(String beanName) {
        for (BeanWrapper beanWrapper : inversionOfControlMap.values()) {
            if (beanWrapper.getBeanName().equals(beanName)) {
                return beanWrapper.getBeanInstance();
            }
        }
        return null;
    }

    private void loadApplicationFiles() {
        URL resource = ApplicationContext.class.getResource("");
        String jarPaht = resource.getFile();
        if (jarPaht.contains(".jar")) {
            jarPaht = jarPaht.substring(0, jarPaht.lastIndexOf("!")).replace("file:/", "");
            applicationFiles = new ArrayList<>();
            JarFile jarFile = null;
            try {
                jarFile = new JarFile(jarPaht);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            Enumeration<JarEntry> ee = jarFile.entries();
            while (ee.hasMoreElements()) {
                JarEntry entry = (JarEntry) ee.nextElement();
                applicationFiles.add(entry.getName());
            }
        }
    }

    private List<String> foundFileByApplicationFiles(String path) {
        String filePath = path.replaceAll("\\.", "/");
        List<String> foundFiles = new ArrayList<>();
        applicationFiles.stream()
                .filter(applicationFile -> applicationFile.startsWith(filePath))
                .forEach(foundFiles::add);
        return foundFiles;
    }

    /**
     * 初始化参数
     */
    private void initContext(Class<?> applicationClass, String[] args) {
        this.args = args;
        hasComponentAnnotationClasses = new ArrayList<>();
        //扫描包路径
        ComponentScan componentScan = applicationClass.getAnnotation(ComponentScan.class);
        if (componentScan != null) {
            scanPackages = componentScan.scanPackages();
        }
    }

    /**
     * 初始化配置文件
     */
    private void initProperties() throws Exception {
        //配置文件
        if (ArrayUtils.isNotEmpty(args)) {
            for (String arg : args) {
                if (arg.startsWith(ProjectVariables.Arguments.PROPERTIES)) {
                    properties = arg.replace(ProjectVariables.Arguments.PROPERTIES, "").split(",");
                    break;
                }
            }
        }
        reader = new BeanDefinitionReader(properties);
        //读取arg的参数，设置到properties里面
        for (String arg : args) {
            String[] split = arg.replace(ProjectVariables.Arguments.PARAM_FLAG, "").split("=");
            if (split.length != 2) {
                log.error(arg + "配置参数格式错误");
                System.exit(1);
            }
            reader.getConfig().put(split[0], split[1]);
        }
    }

    /**
     * 扫描需要加入到容器的类
     */
    private void doScanner() {
        log.debug("开始扫描包……");
        if (ArrayUtils.isNotEmpty(scanPackages)) {
            List<String> scanPackageList = Arrays.asList(scanPackages);
            if (applicationFiles == null) {
                scanPackageList.forEach(applicationContextInstance::doScanner);
            } else {
                scanPackageList.forEach(scanPackage -> {
                    List<String> foundFiles = foundFileByApplicationFiles(scanPackage);
                    foundFiles.stream()
                            .filter(foundFile -> foundFile.endsWith(".class"))
                            .forEach(foundFile -> {
                                String clazzPath = foundFile.replaceAll("/", ".").replace(".class", "");
                                addClassToAnnotationClasses(clazzPath);
                            });
                });
            }
        }
    }

    /**
     * 扫描需要加入到容器的类
     *
     * @param scanPackage
     */
    private void doScanner(String scanPackage) {
        String fileUrl = ProjectVariables.CLASS_PATH + SystemVariables.FILE_SEPARATOR + scanPackage.replaceAll("\\.", "/");
        File classPath = new File(fileUrl);
        for (File file : classPath.listFiles()) {
            if (file.isDirectory()) {
                doScanner(scanPackage + "." + file.getName());
            } else {
                if (file.getName().endsWith(".class")) {
                    String className = scanPackage + "." + file.getName().replace(".class", "");
                    addClassToAnnotationClasses(className);
                }
            }
        }
    }

    private void addClassToAnnotationClasses(String className) {
        Class<?> clazz = null;
        try {
            clazz = Class.forName(className);
        } catch (ClassNotFoundException e) {
            log.error(e.getMessage(), e);
            System.exit(1);
        }
        Component component = clazz.getAnnotation(Component.class);
        if (component != null) {
            log.debug("找到类：{}", clazz);
            hasComponentAnnotationClasses.add(clazz);
        }
    }

    /**
     * 扫描包，初始化bean,并注册bean
     */
    private void registerBean() {
        hasComponentAnnotationClasses.forEach(clazz -> {
            Component component = clazz.getAnnotation(Component.class);
            String key = component.value();
            if (StringUtils.isBlank(key)) {
                String simpleName = clazz.getSimpleName();
                key = toLowerFirstCase(simpleName);
            }
            Object beanInstance = AnnotationBeanFactory.getInstance().create(clazz);
            AnnotationBeanWrapper beanWrapper = AnnotationBeanFactory.getInstance().createBeanWrapper();
            beanWrapper.setBeanInstance(beanInstance);
            beanWrapper.setAnnotations(clazz.getAnnotations());
            beanWrapper.setBeanClass(clazz);
            beanWrapper.setBeanName(key);
            log.debug("往ioc容器里注册：{}", clazz);
            inversionOfControlMap.put(beanWrapper.getBeanClass(), beanWrapper);
        });
    }

    /**
     * 扫描，给标有Autowired注解的属性注入值
     */
    private void initBeanFieldsByAutowired() {
        hasComponentAnnotationClasses.forEach(clazz -> {
            List<Field> fields = getDeclaredFields(clazz);
            fields.stream()
                    .filter(field -> field.isAnnotationPresent(Autowired.class))
                    .forEach(field -> {
                        log.debug("DI Autowired自动往{}依赖注入：{}", clazz, field.getName());
                        field.setAccessible(true);
                        initFiledByAutowired(field, clazz);
                    });
        });
    }

    /**
     * 获取父类字段
     *
     * @param clazz
     * @return
     */
    private List<Field> getDeclaredFields(Class clazz) {
        Class tempClass = clazz;
        List<Field> fieldList = new ArrayList<>();
        while (tempClass != null) {
            fieldList.addAll(Arrays.asList(tempClass.getDeclaredFields()));
            tempClass = tempClass.getSuperclass();
        }
        return fieldList;
    }

    /**
     * 扫描，给标有Value注解的属性注入值
     */
    private void initBeanFieldsByValue() {
        hasComponentAnnotationClasses.forEach(clazz -> {

            List<Field> fieldList = getDeclaredFields(clazz);

            fieldList.stream()
                    .filter(field -> field.isAnnotationPresent(Value.class))
                    .forEach(field -> {
                        field.setAccessible(true);
                        log.debug("DI Value自动往{}依赖注入：{}", clazz, field.getName());
                        initFiledByValue(field, clazz);
                    });
        });
    }

    /**
     * DI 自动注入
     * 读取配置文件设置value
     *
     * @param field
     * @param clazz
     */
    private void initFiledByValue(Field field, Class<?> clazz) {
        Value value = field.getAnnotation(Value.class);
        String propertiesName = value.value();
        if (StringUtils.isBlank(propertiesName)) {
            String fieldName = field.getName();
            StringBuilder builder = new StringBuilder();
            for (char c : fieldName.toCharArray()) {
                if (Character.isUpperCase(c)) {
                    c += 32;
                    builder.append(".");
                }
                builder.append(c);
            }
            propertiesName = builder.toString();
        }
        Object object = getBean(clazz);
        Object propertiesValue = reader.getConfig().get(propertiesName);
        if (propertiesValue == null) {
            log.error("没找到该配置信息：" + propertiesName);
            System.exit(1);
        }
        //判断字段类型
        String fieldType = field.getGenericType().toString();
        // 如果类型是String
        try {
            if (fieldType.equals("class java.lang.String")) {
                propertiesValue = getPropertiesValueByParam(propertiesValue.toString());
                field.set(object, propertiesValue);
            }
            // 如果类型是字符串数组
            else if (fieldType.equals("class [Ljava.lang.String;")) {
                if (propertiesValue instanceof String) {
                    String propertyValue = propertiesValue.toString();
                    if (propertyValue.contains(",")) {
                        String[] split = propertyValue.split(",");
                        field.set(object, split);
                    } else {
                        field.set(object, propertyValue);
                    }
                } else if (propertiesValue instanceof List) {
                    List<String> propertyValue = (List<String>) propertiesValue;
                    field.set(object, propertyValue.toArray(new String[propertyValue.size()]));
                }
            }
            // 如果类型是字符串集合
            else if (fieldType.equals("java.util.List<java.lang.String>")) {
                List<String> propertyValue = (List<String>) propertiesValue;
                field.set(object, propertyValue);
            }
            // 如果类型是Integer
            else if (fieldType.equals("class java.lang.Integer")) {
                int fieldValue = Integer.parseInt(propertiesValue.toString());
                field.set(object, fieldValue);
            }
            // 如果类型是Double
            else if (fieldType.equals("class java.lang.Double")) {
                double fieldValue = Double.parseDouble(propertiesValue.toString());
                field.set(object, fieldValue);
            }
            // 如果类型是Boolean 是封装类
            else if (fieldType.equals("class java.lang.Boolean")) {
                boolean fieldValue = false;
                if ("true".equalsIgnoreCase(propertiesValue.toString())) {
                    fieldValue = true;
                }
                field.set(object, fieldValue);
            } else {
                log.error("没找到类型：" + fieldType);
                System.exit(1);
            }
        } catch (IllegalAccessException e) {
            log.error(e.getMessage(), e);
            System.exit(1);
        }
    }

    private String getPropertiesValueByParam(String propertiesValue) {
        String pValue = "";
        String flag = "";
        if (propertiesValue.contains("$")) {
            flag = "$";
        } else if (propertiesValue.contains("%fileName")) {
            flag = "%fileName";
        } else if (propertiesValue.contains("%")) {
            flag = "%";
        } else {
            return propertiesValue;
        }

        int index1 = propertiesValue.indexOf(flag);
        int index2 = propertiesValue.indexOf("}");
        String value1 = propertiesValue.substring(0, index1);
        String key = propertiesValue.substring(index1 + (flag.length() + 1), index2);
        String value3 = propertiesValue.substring(index2 + 1);
        String value2 = "";
        if ("$".equals(flag)) {
            value2 = reader.getConfig().get(key) + "";
        } else if ("%fileName".equals(flag)) {
            File file = new File(reader.getConfig().get(key) + "");
            File[] dirs = new File(file.getParentFile().getPath()).listFiles((FileFilter) new WildcardFileFilter(file.getName()));
            for (File dir : dirs) {
                value2 = dir.getName().substring(0, dir.getName().lastIndexOf("."));
                break;
            }
        } else if ("%".equals(flag)) {
            if ("date".equals(key)) {
                value2 = DateUtil.getToday();
            }
        }
        pValue = value1 + value2 + value3;

        if (pValue.contains("$") || pValue.contains("%")) {
            pValue = getPropertiesValueByParam(pValue);
        }
        return pValue;
    }

    /**
     * DI 自动注入bean
     * autowired
     *
     * @param field
     * @param clazz
     */
    private void initFiledByAutowired(Field field, Class<?> clazz) {
        Object object = getBean(clazz);
        Object fieldValue = null;

        Autowired autowired = field.getAnnotation(Autowired.class);
        String autowiredValue = autowired.value();

        String fieldType = field.getGenericType().toString();
        if (fieldType.startsWith("java.util.Map")) {

            Type type = field.getGenericType();
            if (type instanceof ParameterizedType) {

                Map<Object, Object> fieldValueMap = new HashMap<>();

                ParameterizedType parameterizedType = (ParameterizedType) type;
                Type[] actualTypes = parameterizedType.getActualTypeArguments();
                Class valueType = (Class) actualTypes[1];
                inversionOfControlMap.forEach((k, v) -> {
                    Object beanInstance = v.getBeanInstance();
                    if (valueType.isAssignableFrom(beanInstance.getClass())) {
                        Component component = v.getBeanClass().getAnnotation(Component.class);
                        String beanMapKey = component.beanMapKey();
                        if (StringUtils.isBlank(beanMapKey)) {
                            beanMapKey = v.getBeanName();
                        }
                        fieldValueMap.put(beanMapKey, beanInstance);
                    }
                });
                fieldValue = fieldValueMap;
            } else {
                log.error("处理java.util.Map，field.getGenericType() 不是 ParameterizedType");
                System.exit(1);
            }
        } else if (fieldType.startsWith("java.util.List")) {
            Type type = field.getGenericType();
            if (type instanceof ParameterizedType) {
                List<Object> fieldValueList = new ArrayList<>();
                ParameterizedType parameterizedType = (ParameterizedType) type;
                Type[] actualTypes = parameterizedType.getActualTypeArguments();
                Class listType = (Class) actualTypes[0];
                inversionOfControlMap.forEach((k, v) -> {
                    Object beanInstance = v.getBeanInstance();
                    if (listType.isAssignableFrom(beanInstance.getClass())) {
                        fieldValueList.add(beanInstance);
                    }
                });
                fieldValue = fieldValueList;
            } else {
                log.error("处理java.util.List，field.getGenericType() 不是 ParameterizedType");
                System.exit(1);
            }
        } else {
            if (StringUtils.isBlank(autowiredValue)) {
                fieldValue = getBean(field.getType());
            } else {
                fieldValue = getBean(field.getName());
            }
        }
        if (fieldValue == null) {
            log.error("注入 " + clazz + " 属性" + field.getName() + "失败");
            System.exit(1);
        }
        try {
            field.set(object, fieldValue);
        } catch (IllegalAccessException e) {
            log.error(e.getMessage(), e);
            System.exit(1);
        }
    }

    /**
     * 执行指定注解方法
     */
    private <T extends Annotation> void runAnnotationMethod(Class<T> annotationClass) {
        hasComponentAnnotationClasses.forEach(clazz -> {
            Method[] methods = clazz.getMethods();
            for (Method method : methods) {
                Annotation runAnnotation = method.getAnnotation(annotationClass);
                if (runAnnotation != null) {
                    Object object = getBean(clazz);
                    try {
                        method.invoke(object);
                    } catch (Exception e) {
                        log.error(e.getMessage(), e);
                        System.exit(1);
                    }
                }
            }
        });

    }

    /**
     * 将首字母转为小写
     *
     * @param simpleName
     * @return
     */
    private String toLowerFirstCase(String simpleName) {
        char[] chars = simpleName.toCharArray();
        chars[0] += 32;
        return String.valueOf(chars);
    }

    public BeanDefinitionReader getReader() {
        return reader;
    }
}
