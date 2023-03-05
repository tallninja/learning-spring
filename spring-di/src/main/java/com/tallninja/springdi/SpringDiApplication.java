package com.tallninja.springdi;

import com.tallninja.springdi.controllers.AppController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SpringDiApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(SpringDiApplication.class, args);

		AppController appController = context.getBean(AppController.class);

		System.out.println(appController.sayHello());
	}

}
