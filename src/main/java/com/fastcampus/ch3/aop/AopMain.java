package com.fastcampus.ch3.aop;


import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AopMain {
    public static void main(String[] args) throws Exception{
        MyAdvice myAdvice = new MyAdvice();

        Class myClass = Class.forName("com.fastcampus.ch3.aop.MyClass");
        Object obj = myClass.newInstance();

        for(Method m : myClass.getDeclaredMethods()){
            myAdvice.invoke(m, obj, null);
        }


    }
}

class MyAdvice{
    Pattern p = Pattern.compile("a.*");

    boolean match(Method m){
        Matcher matcher = p.matcher(m.getName());
        return matcher.matches();
    }

    void invoke(Method m, Object obj, Object... args) throws Exception{
        if(match(m))
            System.out.println("[before]{");

        m.invoke(obj,args);
        if(match(m))
            System.out.println("}[after]");
    }
}
class MyClass{
    void aaa(){
        System.out.println("aaa() is called");
    }
    void bbb(){
        System.out.println("bbb() is called");
    }
    void ccc(){
        System.out.println("ccc() is called");
    }
}
