package com.tallninja.springdi.controllers;

import org.springframework.stereotype.Controller;

@Controller
public class AppController {
    public String sayHello() {
        String reply = "Hello from " + this.getClass().getSimpleName();
        System.out.println(reply);
        return reply;
    }
}
