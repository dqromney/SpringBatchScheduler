package com.dqr;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@SpringBootApplication
public class SpringBatchSchedulerApplication {

//    public static void main(String[] args) {
//        SpringApplication.run(SpringBatchSchedulerApplication.class, args);
//    }

    public static void main(String[] args) {
   		String[] str = {"classpath:META-INF/spring/job-config.xml","classpath:META-INF/spring/context-config.xml"};
   		ApplicationContext ctx = new ClassPathXmlApplicationContext(str);
   	}
}
