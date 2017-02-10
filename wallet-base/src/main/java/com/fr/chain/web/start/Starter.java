package com.fr.chain.web.start;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 启动器
 * 
 */
public class Starter implements ServletContextListener {

	private Logger log = LoggerFactory.getLogger(Starter.class);
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		log.warn("WEB启动成功");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		log.warn("WEB关闭成功");
	}

}
