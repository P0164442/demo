package com.example.demo.beanTest;

import com.example.demo.model.Coffee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@Configuration
@ComponentScan("com.example.demo.beanTest2")
public class annotationBean {

//11
//2
    //33
 
//44
    //55

 //`12
//31
    @Test
    public void test() {
        AnnotationConfigApplicationContext context =
                   new AnnotationConfigApplicationContext(com.example.demo.beanTest.annotationBean.class);

        testService itestService=context.getBean(testService.class);
        itestService.test("test Autowired bean");
       
    }
}
