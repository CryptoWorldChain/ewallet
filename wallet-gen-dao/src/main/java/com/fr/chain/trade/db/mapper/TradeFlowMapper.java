package com.fr.chain.trade.db.mapper;

import com.fr.chain.db.iface.StaticTableDaoSupport;
import com.fr.chain.trade.db.entity.TradeFlow;
import com.fr.chain.trade.db.entity.TradeFlowExample;
import com.fr.chain.trade.db.entity.TradeFlowKey;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

public interface TradeFlowMapper extends StaticTableDaoSupport<TradeFlow, TradeFlowExample, TradeFlowKey> {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TRADE_FLOW
     *
     * @mbggenerated Wed Feb 22 15:15:57 CST 2017
     */
    @SelectProvider(type=TradeFlowSqlProvider.class, method="countByExample")
    int countByExample(TradeFlowExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TRADE_FLOW
     *
     * @mbggenerated Wed Feb 22 15:15:57 CST 2017
     */
    @DeleteProvider(type=TradeFlowSqlProvider.class, method="deleteByExample")
    int deleteByExample(TradeFlowExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TRADE_FLOW
     *
     * @mbggenerated Wed Feb 22 15:15:57 CST 2017
     */
    @Delete({
        "delete from TRADE_FLOW",
        "where FLOW_ID = #{flowId,jdbcType=VARCHAR}"
    })
    int deleteByPrimaryKey(TradeFlowKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TRADE_FLOW
     *
     * @mbggenerated Wed Feb 22 15:15:57 CST 2017
     */
    @Insert({
        "insert into TRADE_FLOW (FLOW_ID, ORDER_ID, ",
        "MERCHANT_ID, APP_ID, ",
        "OPEN_ID, TALLY_TAG, ",
        "ORIGIN_OPENID, PRODUCT_ID, ",
        "PROPERTY_TYPE, IS_SELF_SUPPORT, ",
        "PRODUCT_DESC, IS_DIGIT, ",
        "SIGNTYPE, PROPERTY_NAME, ",
        "UNIT, MINCOUNT, ",
        "COUNT, URL, AMOUNT, ",
        "DESCRIPTION, ADDRESS, ",
        "TRADE_TYPE, CREATE_TIME, ",
        "UPDATE_TIME)",
        "values (#{flowId,jdbcType=VARCHAR}, #{orderId,jdbcType=VARCHAR}, ",
        "#{merchantId,jdbcType=VARCHAR}, #{appId,jdbcType=VARCHAR}, ",
        "#{openId,jdbcType=VARCHAR}, #{tallyTag,jdbcType=INTEGER}, ",
        "#{originOpenid,jdbcType=VARCHAR}, #{productId,jdbcType=VARCHAR}, ",
        "#{propertyType,jdbcType=VARCHAR}, #{isSelfSupport,jdbcType=VARCHAR}, ",
        "#{productDesc,jdbcType=VARCHAR}, #{isDigit,jdbcType=VARCHAR}, ",
        "#{signtype,jdbcType=VARCHAR}, #{propertyName,jdbcType=VARCHAR}, ",
        "#{unit,jdbcType=VARCHAR}, #{mincount,jdbcType=VARCHAR}, ",
        "#{count,jdbcType=VARCHAR}, #{url,jdbcType=VARCHAR}, #{amount,jdbcType=DECIMAL}, ",
        "#{description,jdbcType=VARCHAR}, #{address,jdbcType=VARCHAR}, ",
        "#{tradeType,jdbcType=INTEGER}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{updateTime,jdbcType=TIMESTAMP})"
    })
    int insert(TradeFlow record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TRADE_FLOW
     *
     * @mbggenerated Wed Feb 22 15:15:57 CST 2017
     */
    @InsertProvider(type=TradeFlowSqlProvider.class, method="insertSelective")
    int insertSelective(TradeFlow record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TRADE_FLOW
     *
     * @mbggenerated Wed Feb 22 15:15:57 CST 2017
     */
    @SelectProvider(type=TradeFlowSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="FLOW_ID", property="flowId", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="ORDER_ID", property="orderId", jdbcType=JdbcType.VARCHAR),
        @Result(column="MERCHANT_ID", property="merchantId", jdbcType=JdbcType.VARCHAR),
        @Result(column="APP_ID", property="appId", jdbcType=JdbcType.VARCHAR),
        @Result(column="OPEN_ID", property="openId", jdbcType=JdbcType.VARCHAR),
        @Result(column="TALLY_TAG", property="tallyTag", jdbcType=JdbcType.INTEGER),
        @Result(column="ORIGIN_OPENID", property="originOpenid", jdbcType=JdbcType.VARCHAR),
        @Result(column="PRODUCT_ID", property="productId", jdbcType=JdbcType.VARCHAR),
        @Result(column="PROPERTY_TYPE", property="propertyType", jdbcType=JdbcType.VARCHAR),
        @Result(column="IS_SELF_SUPPORT", property="isSelfSupport", jdbcType=JdbcType.VARCHAR),
        @Result(column="PRODUCT_DESC", property="productDesc", jdbcType=JdbcType.VARCHAR),
        @Result(column="IS_DIGIT", property="isDigit", jdbcType=JdbcType.VARCHAR),
        @Result(column="SIGNTYPE", property="signtype", jdbcType=JdbcType.VARCHAR),
        @Result(column="PROPERTY_NAME", property="propertyName", jdbcType=JdbcType.VARCHAR),
        @Result(column="UNIT", property="unit", jdbcType=JdbcType.VARCHAR),
        @Result(column="MINCOUNT", property="mincount", jdbcType=JdbcType.VARCHAR),
        @Result(column="COUNT", property="count", jdbcType=JdbcType.VARCHAR),
        @Result(column="URL", property="url", jdbcType=JdbcType.VARCHAR),
        @Result(column="AMOUNT", property="amount", jdbcType=JdbcType.DECIMAL),
        @Result(column="DESCRIPTION", property="description", jdbcType=JdbcType.VARCHAR),
        @Result(column="ADDRESS", property="address", jdbcType=JdbcType.VARCHAR),
        @Result(column="TRADE_TYPE", property="tradeType", jdbcType=JdbcType.INTEGER),
        @Result(column="CREATE_TIME", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="UPDATE_TIME", property="updateTime", jdbcType=JdbcType.TIMESTAMP)
    })
    List<TradeFlow> selectByExample(TradeFlowExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TRADE_FLOW
     *
     * @mbggenerated Wed Feb 22 15:15:57 CST 2017
     */
    @Select({
        "select",
        "FLOW_ID, ORDER_ID, MERCHANT_ID, APP_ID, OPEN_ID, TALLY_TAG, ORIGIN_OPENID, PRODUCT_ID, ",
        "PROPERTY_TYPE, IS_SELF_SUPPORT, PRODUCT_DESC, IS_DIGIT, SIGNTYPE, PROPERTY_NAME, ",
        "UNIT, MINCOUNT, COUNT, URL, AMOUNT, DESCRIPTION, ADDRESS, TRADE_TYPE, CREATE_TIME, ",
        "UPDATE_TIME",
        "from TRADE_FLOW",
        "where FLOW_ID = #{flowId,jdbcType=VARCHAR}"
    })
    @Results({
        @Result(column="FLOW_ID", property="flowId", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="ORDER_ID", property="orderId", jdbcType=JdbcType.VARCHAR),
        @Result(column="MERCHANT_ID", property="merchantId", jdbcType=JdbcType.VARCHAR),
        @Result(column="APP_ID", property="appId", jdbcType=JdbcType.VARCHAR),
        @Result(column="OPEN_ID", property="openId", jdbcType=JdbcType.VARCHAR),
        @Result(column="TALLY_TAG", property="tallyTag", jdbcType=JdbcType.INTEGER),
        @Result(column="ORIGIN_OPENID", property="originOpenid", jdbcType=JdbcType.VARCHAR),
        @Result(column="PRODUCT_ID", property="productId", jdbcType=JdbcType.VARCHAR),
        @Result(column="PROPERTY_TYPE", property="propertyType", jdbcType=JdbcType.VARCHAR),
        @Result(column="IS_SELF_SUPPORT", property="isSelfSupport", jdbcType=JdbcType.VARCHAR),
        @Result(column="PRODUCT_DESC", property="productDesc", jdbcType=JdbcType.VARCHAR),
        @Result(column="IS_DIGIT", property="isDigit", jdbcType=JdbcType.VARCHAR),
        @Result(column="SIGNTYPE", property="signtype", jdbcType=JdbcType.VARCHAR),
        @Result(column="PROPERTY_NAME", property="propertyName", jdbcType=JdbcType.VARCHAR),
        @Result(column="UNIT", property="unit", jdbcType=JdbcType.VARCHAR),
        @Result(column="MINCOUNT", property="mincount", jdbcType=JdbcType.VARCHAR),
        @Result(column="COUNT", property="count", jdbcType=JdbcType.VARCHAR),
        @Result(column="URL", property="url", jdbcType=JdbcType.VARCHAR),
        @Result(column="AMOUNT", property="amount", jdbcType=JdbcType.DECIMAL),
        @Result(column="DESCRIPTION", property="description", jdbcType=JdbcType.VARCHAR),
        @Result(column="ADDRESS", property="address", jdbcType=JdbcType.VARCHAR),
        @Result(column="TRADE_TYPE", property="tradeType", jdbcType=JdbcType.INTEGER),
        @Result(column="CREATE_TIME", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="UPDATE_TIME", property="updateTime", jdbcType=JdbcType.TIMESTAMP)
    })
    TradeFlow selectByPrimaryKey(TradeFlowKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TRADE_FLOW
     *
     * @mbggenerated Wed Feb 22 15:15:57 CST 2017
     */
    @UpdateProvider(type=TradeFlowSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") TradeFlow record, @Param("example") TradeFlowExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TRADE_FLOW
     *
     * @mbggenerated Wed Feb 22 15:15:57 CST 2017
     */
    @UpdateProvider(type=TradeFlowSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") TradeFlow record, @Param("example") TradeFlowExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TRADE_FLOW
     *
     * @mbggenerated Wed Feb 22 15:15:57 CST 2017
     */
    @UpdateProvider(type=TradeFlowSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(TradeFlow record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TRADE_FLOW
     *
     * @mbggenerated Wed Feb 22 15:15:57 CST 2017
     */
    @Update({
        "update TRADE_FLOW",
        "set ORDER_ID = #{orderId,jdbcType=VARCHAR},",
          "MERCHANT_ID = #{merchantId,jdbcType=VARCHAR},",
          "APP_ID = #{appId,jdbcType=VARCHAR},",
          "OPEN_ID = #{openId,jdbcType=VARCHAR},",
          "TALLY_TAG = #{tallyTag,jdbcType=INTEGER},",
          "ORIGIN_OPENID = #{originOpenid,jdbcType=VARCHAR},",
          "PRODUCT_ID = #{productId,jdbcType=VARCHAR},",
          "PROPERTY_TYPE = #{propertyType,jdbcType=VARCHAR},",
          "IS_SELF_SUPPORT = #{isSelfSupport,jdbcType=VARCHAR},",
          "PRODUCT_DESC = #{productDesc,jdbcType=VARCHAR},",
          "IS_DIGIT = #{isDigit,jdbcType=VARCHAR},",
          "SIGNTYPE = #{signtype,jdbcType=VARCHAR},",
          "PROPERTY_NAME = #{propertyName,jdbcType=VARCHAR},",
          "UNIT = #{unit,jdbcType=VARCHAR},",
          "MINCOUNT = #{mincount,jdbcType=VARCHAR},",
          "COUNT = #{count,jdbcType=VARCHAR},",
          "URL = #{url,jdbcType=VARCHAR},",
          "AMOUNT = #{amount,jdbcType=DECIMAL},",
          "DESCRIPTION = #{description,jdbcType=VARCHAR},",
          "ADDRESS = #{address,jdbcType=VARCHAR},",
          "TRADE_TYPE = #{tradeType,jdbcType=INTEGER},",
          "CREATE_TIME = #{createTime,jdbcType=TIMESTAMP},",
          "UPDATE_TIME = #{updateTime,jdbcType=TIMESTAMP}",
        "where FLOW_ID = #{flowId,jdbcType=VARCHAR}"
    })
    int updateByPrimaryKey(TradeFlow record);
}