package com.tallninja.springdi;

import com.tallninja.springdi.controllers.AppController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
class SpringDiApplicationTests {

	@Autowired
	ApplicationContext appContext;

	@Autowired
	AppController controller;

	@Test
	void testAutowiredOfController() {
		System.out.println(controller.sayHello());
	}

	@Test
	void getControllerFromContext() {
		AppController controller = appContext.getBean(AppController.class);
		System.out.println(controller.sayHello());
	}

	@Test
	void contextLoads() {
	}

}
