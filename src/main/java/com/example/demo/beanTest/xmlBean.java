package com.example.demo.beanTest;



import com.example.demo.model.Coffee;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class xmlBean {


    @Test
    public void test() {
        ApplicationContext context = new ClassPathXmlApplicationContext("xml/application-context.xml");
        Coffee coffee = context.getBean("coffee", Coffee.class);

        coffee.test("test xml bean");
    }
}