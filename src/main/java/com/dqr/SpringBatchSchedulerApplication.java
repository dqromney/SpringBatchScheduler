package com.dqr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.dqr")
public class SpringBatchSchedulerApplication {

//    public static void main(String[] args) {
//        SpringApplication.run(SpringBatchSchedulerApplication.class, args);
//    }

//    public static void main(String[] args) {
//   		String[] str = {"classpath:META-INF/spring/job-config.xml","classpath:META-INF/spring/context-config.xml"};
//   		ApplicationContext ctx = new ClassPathXmlApplicationContext(str);
//   	}

	public static void main(String[] args) {
		SpringApplication.run(new Object[]{SpringBatchSchedulerApplication.class}, args);
	}
}
