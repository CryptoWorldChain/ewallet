package com.fr.chain.utils;

import lombok.extern.slf4j.Slf4j;


import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;



/**
 * bean容器
 */
@Slf4j
public class BeanFactory implements ApplicationContextAware {
	private static ApplicationContext ctx;

	public static Object getBean(String beanName) {
		return ctx.getBean(beanName);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		ctx = applicationContext;
	}
	
	public static ApplicationContext getCtx() {
		return ctx;
	}

}
