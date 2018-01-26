package com.shixzh.spring.jcat.manual;

class TestA {

    // 验证为何可以A3.getAllIds可以回调到A2.getAllIds，仅仅是因为构造器里默默的变换了对象
    public static void main(String[] args) {
        A a = null;
        A a2 = new A2(a);
        a2 = new A3(a2);
        a2.getAllIds();

    }

}
