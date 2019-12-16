package com.natsuki_kining.ttkun.context.bean.reader;

import org.apache.commons.lang3.ArrayUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 对配置文件进行查找、读取、解析
 *
 * @Author natsuki_kining
 * @Date 2019/12/15 17:57
 **/
public class BeanDefinitionReader {

    private List<String> registryBeanClasses = new ArrayList<>();
    private Properties config = new Properties();

    public BeanDefinitionReader(String ... properties) throws Exception {
        if (properties == null || properties.length == 0){
            load(true,"application.properties");
        }else{
            load(true,properties);
        }
    }

    /**
     * @param flag true,加载内部文件，false加载外部文件
     * @param properties
     */
    public void load(boolean flag,String ... properties) throws Exception {
        if (ArrayUtils.isEmpty(properties)){
            throw new Exception("读取的配置文件不能为空");
        }
        List<InputStream> inputStreams = new ArrayList<>();
        InputStream is = null;
        try {
            if (flag){
                for (String property : properties) {
                    is = this.getClass().getClassLoader().getResourceAsStream(property);
                    inputStreams.add(is);
                }
            }else{
                for (String property : properties) {
                    is = new FileInputStream(property);
                    inputStreams.add(is);
                }
            }

            Properties p = null;
            for (InputStream inputStream : inputStreams) {
                p = new Properties();
                p.load(inputStream);
                p.entrySet().forEach(entry->{
                    Object key = entry.getKey();
                    Object value = entry.getValue();
                    Object configValue = config.get(key);
                    if (configValue != null){
                        if (configValue instanceof List){
                            List<String> configValues = (List<String>)configValue;
                            configValues.add(value.toString());
                            config.put(key,configValues);
                        }else{
                            List<String> newConfigValue = new ArrayList<>();
                            newConfigValue.add(configValue.toString());
                            newConfigValue.add(value.toString());
                            config.put(key,newConfigValue);
                        }
                    }else{
                        config.put(key,value);
                    }
                });

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            for (InputStream inputStream : inputStreams) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void load(String ... properties) throws Exception {
        load(false,properties);
    }

    public Properties getConfig() {
        return config;
    }

    /**
     * 将首字母转为小写
     * @param simpleName
     * @return
     */
    private String toLowerFirstCase(String simpleName){
        char[] chars = simpleName.toCharArray();
        chars[0] += 32;
        return String.valueOf(chars);
    }
}
