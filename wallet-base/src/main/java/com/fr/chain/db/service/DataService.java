package com.fr.chain.db.service;



public interface DataService {
	
	Object doBySQL(String sql) throws Exception;
	
	String doBySQL2jsonstr(String sql) throws Exception;
	
	public int getCount(String sql) throws Exception;
	
}
