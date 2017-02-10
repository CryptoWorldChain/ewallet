package com.fr.chain.db.iface;

import java.util.List;

public interface StaticTableDaoSupport<T,D,K> {
	
    int countByExample(D example);

    int deleteByExample(D example);

    int deleteByPrimaryKey(K key);

    int insert(T record);

    int insertSelective(T record);
    
    int batchInsert(List<T> records) ;
    
    int batchUpdate(List<T> records) ;
    
    int batchDelete(List<T> records) ;

    List<T> selectByExample(D example) ;

    T selectByPrimaryKey(K key);
    
    List<T> findAll(List<T> records) ;

    int updateByExampleSelective(T record, D example);

    int updateByExample(T record, D example);

    int updateByPrimaryKeySelective(T record);

    int updateByPrimaryKey(T record);

    int sumByExample(D example);
    
    void deleteAll();
    
	D getExample(T record);

}