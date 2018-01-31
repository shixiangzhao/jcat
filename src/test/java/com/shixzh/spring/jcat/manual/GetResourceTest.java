package com.shixzh.spring.jcat.manual;

import com.shixzh.spring.jcat.beanadapter.BeanAdapter;

public class GetResourceTest {

    public static void main(String[] args) {
        System.out.println(BeanAdapter.class.getResource("1.properties"));
        System.out.println(BeanAdapter.class.getResource("/"));
    }
}
