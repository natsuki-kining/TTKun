package com.natsuki_kining.ttkun.crawler.core.url;

import lombok.Data;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 管理url
 *
 * @Author natsuki_kining
 * @Date 2019/12/15 11:37
 **/
public class UrlManager {

    private List<String> list;
    private String[] array;

    public static void main(String[] args) {
        Field[] fields = UrlManager.class.getDeclaredFields();
        for (Field field : fields) {
            System.out.println(field.getGenericType());
        }
    }

//    public static String getUrl(){
//
//    }


    public void aaa(String a){

    }

    @Data
    private class A{
        String code;
        String value;

    }

    @Data
    private class B {
        String code;
        String value;
    }


    public void bbb(String[] b){
        A a = new A();
        List<A> as = new ArrayList<>();
        Map<String, A> collect = as.stream().collect(Collectors.toMap(A::getCode, e -> e));
        Set<String> collect1 = as.stream().map(e -> e.getCode()).collect(Collectors.toSet());
        collect1.contains(a.code);
        List<B> collect2 = as.stream().map(e -> {
            B b1 = new B();
            b1.setCode(e.code);
            return b1;
        }).collect(Collectors.toList());
    }
}
