package com.natsuki_kining.ttkun.context.variables;

/**
 * 系统全局常量
 *
 * @Author natsuki_kining
 * @Date 2019/12/15 16:06
 **/
public class SystemVariables {

    /**
     * 系统文件夹分割符
     */
    public final static String FILE_SEPARATOR = System.getProperty("file.separator");

    /**
     * 系统字符编码
     */
    public final static String FILE_ENCODING = System.getProperty("file.encoding");

    /**
     * 系统操作类型
     */
    public final static String OS = System.getProperty("os.name").toLowerCase();

    /**
     * 项目所在地址
     */
    public final static String USER_DIR = System.getProperty("user.dir");

}
