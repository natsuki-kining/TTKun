package com.natsuki_kining.ttkun.crawler.common.utils;

import com.natsuki_kining.ttkun.crawler.common.excption.TtIOException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;

/**
 * 文件工具类
 *
 * @Author natsuki_kining
 * @Date 2019/12/18 16:34
 **/
@Slf4j
public class FileUtil {

    /**
     * 如果文件夹不存在则创建
     *
     * @param path 文件夹路径
     */
    public static File createPath(String path) {
        File file = new File(path);
        if (!file.exists() && !file.isDirectory()) {
            file.mkdirs();
        }
        return file;
    }

    /**
     * 格式化路径
     *
     * @param filePath 路径
     * @return String
     */
    public static String formatPath(String filePath) {
        if (StringUtils.isEmpty(filePath)) {
            return filePath;
        }
        return filePath.replaceAll("(\\\\|/)+", "/");
    }


    public static void copyDirectory(final File srcDir, final File destDir) {
        try {
            FileUtils.copyDirectory(srcDir, destDir);
        } catch (IOException e) {
            log.error(e.getMessage(),e);
            throw new TtIOException(e.getMessage(),e);
        }
    }

    public static void copyFile(final File srcDir, final File destDir) {
        try {
            FileUtils.copyFile(srcDir, destDir);
        } catch (IOException e) {
            log.error(e.getMessage(),e);
            throw new TtIOException(e.getMessage(),e);
        }
    }

    public static void forceDelete(final File file) {
        try {
            FileUtils.forceDelete(file);
        } catch (IOException e) {
            log.error(e.getMessage(),e);
            throw new TtIOException(e.getMessage(),e);
        }
    }

}
