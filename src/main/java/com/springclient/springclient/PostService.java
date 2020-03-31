package com.springclient.springclient;

import org.springframework.boot.CommandLineRunner;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.cloud.client.serviceregistry.ServiceRegistry;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class PostService implements CommandLineRunner {

    @Resource
    Registration registration;
    @Resource
    ServiceRegistry serviceRegistry;
    @Override
    public void run(String... args) throws Exception {
        System.out.println("-----------------after start-------------------------");
        serviceRegistry.setStatus(registration, "DOWN");
    }
}
