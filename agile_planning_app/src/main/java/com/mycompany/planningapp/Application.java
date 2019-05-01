/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.planningapp;

import com.mycompany.controllers.AppController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 *
 * @author ciprian
 */
@SpringBootApplication
@ComponentScan(basePackageClasses = AppController.class)
@ComponentScan(basePackages = "com.mycompany.dao")
@EnableMongoRepositories("com.mycompany.dao")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
