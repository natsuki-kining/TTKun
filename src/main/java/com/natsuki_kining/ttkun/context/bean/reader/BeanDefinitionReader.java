package com.natsuki_kining.ttkun.context.bean.reader;

import com.natsuki_kining.ttkun.crawler.common.excption.TtIOException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * 对配置文件进行查找、读取、解析
 *
 * @Author natsuki_kining
 * @Date 2019/12/15 17:57
 * @UpdateDate 2020/1/4 16:25:00
 * @Version 1.1.0
 **/
@Slf4j
public class BeanDefinitionReader {

    private List<String> registryBeanClasses = new ArrayList<>();
    private Properties config = new Properties();

    public BeanDefinitionReader(String... properties) throws Exception {
        load(true, "application.properties");
        if (properties != null && properties.length > 0) {
            load(false, properties);
        }
    }

    /**
     * @param flag       true,加载内部文件，false加载外部文件
     * @param properties
     */
    private void load(boolean flag, String... properties) throws Exception {
        if (ArrayUtils.isEmpty(properties)) {
            throw new Exception("读取的配置文件不能为空");
        }
        List<InputStream> inputStreams = new ArrayList<>();
        InputStream is = null;
        try {
            if (flag) {
                for (String property : properties) {
                    is = this.getClass().getClassLoader().getResourceAsStream(property);
                    inputStreams.add(is);
                }
            } else {
                for (String property : properties) {
                    is = new FileInputStream(property);
                    inputStreams.add(is);
                }
            }

            Properties p = null;
            for (InputStream inputStream : inputStreams) {
                p = new Properties();
                p.load(inputStream);
                p.entrySet().forEach(entry -> {
                    Object key = entry.getKey();
                    Object value = entry.getValue();
                    config.put(key, value);
                });

            }
        } catch (Exception e) {
            throw new TtIOException(e.getMessage(), e);
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

    public Properties getConfig() {
        return config;
    }

}
