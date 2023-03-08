package com.tallninja;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class HelloSpring {

    public static void main(String[] args) {

        // load the spring configuration file

        ClassPathXmlApplicationContext appCtx = new ClassPathXmlApplicationContext("applicationContext.xml");

        // retrieve bean from spring container
        Vehicle truck = appCtx.getBean("truck", Vehicle.class);
        Vehicle miniVan = appCtx.getBean("miniVan", Vehicle.class);

        // call methods on bean
        System.out.println(truck.drive());
        System.out.println(miniVan.drive());

        // close the application context
        appCtx.close();
    }

}
