package com.onkonfeton;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Test {
    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<TestClass> testClass = TestClass.class;
        TestClass instance = testClass.getConstructor().newInstance();
        Field[] fields = testClass.getDeclaredFields();
        for (Field field : fields) {
            String name = field.getName();
            String methodName = "set" + name.replace(name.charAt(0), Character.toUpperCase(name.charAt(0)));
            Method method = testClass.getMethod(methodName, String.class);
            method.invoke(instance, "ssttrr");
        }


        Method method = testClass.getMethod("doSmth");
        method.invoke(instance);

        System.out.println(instance.getStr());
    }
}

class TestClass{
    private String str;

    public TestClass() {
    }

    public void doSmth(){
        System.out.println("do smth");
    }

    public void setStr(String str) {
        this.str = str;
    }

    public String getStr() {
        return str;
    }
}
