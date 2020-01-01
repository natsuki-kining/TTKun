package com.natsuki_kining.ttkun.context.variables;

/**
 * 项目全局变量
 *
 * @Author natsuki_kining
 * @Date 2019/12/15 16:04
 * @Version 1.0.0
 **/
public class ProjectVariables {

    static {
        String path = ProjectVariables.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        if (SystemVariables.OS.contains("dows")) {
            path = path.substring(1);
        }
        if (path.contains("jar")) {
            path = path.substring(0, path.lastIndexOf("."));
            CLASS_PATH = path.substring(0, path.lastIndexOf("/"));
        } else {
            CLASS_PATH = path;
        }
    }

    public static String CLASS_PATH;

    public interface Arguments {
        String PARAM_FLAG = "-D";
        String PROPERTIES = "-Dproperties=";
    }
}
