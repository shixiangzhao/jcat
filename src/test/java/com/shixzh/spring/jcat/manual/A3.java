package com.shixzh.spring.jcat.manual;

import java.util.Set;

class A3 implements A {

    private final A a;

    public A3(A a) {
        this.a = a;
    }

    public Set<String> getAllIds() {
        System.out.println(this.getClass().getName() + "getAllIds()");
        return a.getAllIds();
    }
}
