package com.example.demo.beanTest;

import org.springframework.stereotype.Component;
//https://www.cnblogs.com/gede/p/10889762.html
@Component
public class testServiceImpl implements  testService{


    @Override
    public void test(String str) {
        System.out.println(str);
    }
}
