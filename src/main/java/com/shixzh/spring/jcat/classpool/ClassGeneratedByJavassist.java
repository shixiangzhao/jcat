package com.shixzh.spring.jcat.classpool;

import java.io.File;
import java.io.FileOutputStream;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.Modifier;

public class ClassGeneratedByJavassist {

    public static void main(String[] args) throws Exception {

        ClassPool pool = ClassPool.getDefault();

        CtClass ctClass = pool.makeClass("com.shixzh.spring.jcat.classpool.MyCC");

        // 添加一个参数
        CtField ctField = new CtField(CtClass.intType, "id", ctClass);
        ctField.setModifiers(Modifier.PUBLIC);
        ctClass.addField(ctField);

        // 把生成的class文件写入文件
        byte[] byteArr = ctClass.toBytecode();
        FileOutputStream fos = new FileOutputStream(new File("D://MyCC.class"));
        fos.write(byteArr);
        fos.close();
        System.out.println("over!!");
    }
}
