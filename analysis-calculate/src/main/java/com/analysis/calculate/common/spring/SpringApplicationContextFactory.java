package com.analysis.calculate.common.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringApplicationContextFactory {

	private static AnnotationConfigApplicationContext applicationContext;

	public static ApplicationContext instance() {
		if (applicationContext == null) {
			synchronized (AnnotationConfigApplicationContext.class) {
				applicationContext = new AnnotationConfigApplicationContext(RedisConfiguration.class);
			}
		}

		return applicationContext;
	}
}