package com.spring.mongo;

import com.spring.config.SpringConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MongoApplication {
    public static void main(String[] args){
        System.out.println("Hello jova");
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(SpringConfig.class);
        ctx.refresh();
    }
}
