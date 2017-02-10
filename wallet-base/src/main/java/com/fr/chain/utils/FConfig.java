package com.fr.chain.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FConfig {

	private static String		CONFIG_PROPERTIES	= "config.properties";
	private static Properties	properties			= null;
	static {

		try {
			if ( properties == null ) {
				log.info( "=============开始加载" + CONFIG_PROPERTIES + "===========" );
				properties = new Properties();
				InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream( CONFIG_PROPERTIES );
				properties.load( is );
				log.info( "=============加载完成" + CONFIG_PROPERTIES + "===========" );
			}
		} catch ( FileNotFoundException e ) {
			e.printStackTrace();
		} catch ( IOException e ) {
			e.printStackTrace();
		}
	}

	public static String getProValue( String proName ) {
		return properties.getProperty( proName );
	}
}
