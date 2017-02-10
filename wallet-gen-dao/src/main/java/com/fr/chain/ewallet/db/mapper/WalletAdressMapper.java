package com.fr.chain.ewallet.db.mapper;

import com.fr.chain.db.iface.StaticTableDaoSupport;
import com.fr.chain.ewallet.db.entity.WalletAdress;
import com.fr.chain.ewallet.db.entity.WalletAdressExample;
import com.fr.chain.ewallet.db.entity.WalletAdressKey;
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

public interface WalletAdressMapper extends StaticTableDaoSupport<WalletAdress, WalletAdressExample, WalletAdressKey> {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WALLET_ADDRESS
     *
     * @mbggenerated Wed Feb 08 16:39:02 CST 2017
     */
    @SelectProvider(type=WalletAdressSqlProvider.class, method="countByExample")
    int countByExample(WalletAdressExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WALLET_ADDRESS
     *
     * @mbggenerated Wed Feb 08 16:39:02 CST 2017
     */
    @DeleteProvider(type=WalletAdressSqlProvider.class, method="deleteByExample")
    int deleteByExample(WalletAdressExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WALLET_ADDRESS
     *
     * @mbggenerated Wed Feb 08 16:39:02 CST 2017
     */
    @Delete({
        "delete from WALLET_ADDRESS",
        "where WALLET_ID = #{walletId,jdbcType=CHAR}"
    })
    int deleteByPrimaryKey(WalletAdressKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WALLET_ADDRESS
     *
     * @mbggenerated Wed Feb 08 16:39:02 CST 2017
     */
    @Insert({
        "insert into WALLET_ADDRESS (WALLET_ID, MERCHANT_ID, ",
        "APP_ID, OPEN_ID, PRODUCT_ID, ",
        "WALLET_CODE, WALLET_ADDRESS, ",
        "CREATE_TIME)",
        "values (#{walletId,jdbcType=CHAR}, #{merchantId,jdbcType=VARCHAR}, ",
        "#{appId,jdbcType=VARCHAR}, #{openId,jdbcType=VARCHAR}, #{productId,jdbcType=VARCHAR}, ",
        "#{walletCode,jdbcType=VARCHAR}, #{walletAddress,jdbcType=VARCHAR}, ",
        "#{createTime,jdbcType=TIMESTAMP})"
    })
    int insert(WalletAdress record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WALLET_ADDRESS
     *
     * @mbggenerated Wed Feb 08 16:39:02 CST 2017
     */
    @InsertProvider(type=WalletAdressSqlProvider.class, method="insertSelective")
    int insertSelective(WalletAdress record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WALLET_ADDRESS
     *
     * @mbggenerated Wed Feb 08 16:39:02 CST 2017
     */
    @SelectProvider(type=WalletAdressSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="WALLET_ID", property="walletId", jdbcType=JdbcType.CHAR, id=true),
        @Result(column="MERCHANT_ID", property="merchantId", jdbcType=JdbcType.VARCHAR),
        @Result(column="APP_ID", property="appId", jdbcType=JdbcType.VARCHAR),
        @Result(column="OPEN_ID", property="openId", jdbcType=JdbcType.VARCHAR),
        @Result(column="PRODUCT_ID", property="productId", jdbcType=JdbcType.VARCHAR),
        @Result(column="WALLET_CODE", property="walletCode", jdbcType=JdbcType.VARCHAR),
        @Result(column="WALLET_ADDRESS", property="walletAddress", jdbcType=JdbcType.VARCHAR),
        @Result(column="CREATE_TIME", property="createTime", jdbcType=JdbcType.TIMESTAMP)
    })
    List<WalletAdress> selectByExample(WalletAdressExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WALLET_ADDRESS
     *
     * @mbggenerated Wed Feb 08 16:39:02 CST 2017
     */
    @Select({
        "select",
        "WALLET_ID, MERCHANT_ID, APP_ID, OPEN_ID, PRODUCT_ID, WALLET_CODE, WALLET_ADDRESS, ",
        "CREATE_TIME",
        "from WALLET_ADDRESS",
        "where WALLET_ID = #{walletId,jdbcType=CHAR}"
    })
    @Results({
        @Result(column="WALLET_ID", property="walletId", jdbcType=JdbcType.CHAR, id=true),
        @Result(column="MERCHANT_ID", property="merchantId", jdbcType=JdbcType.VARCHAR),
        @Result(column="APP_ID", property="appId", jdbcType=JdbcType.VARCHAR),
        @Result(column="OPEN_ID", property="openId", jdbcType=JdbcType.VARCHAR),
        @Result(column="PRODUCT_ID", property="productId", jdbcType=JdbcType.VARCHAR),
        @Result(column="WALLET_CODE", property="walletCode", jdbcType=JdbcType.VARCHAR),
        @Result(column="WALLET_ADDRESS", property="walletAddress", jdbcType=JdbcType.VARCHAR),
        @Result(column="CREATE_TIME", property="createTime", jdbcType=JdbcType.TIMESTAMP)
    })
    WalletAdress selectByPrimaryKey(WalletAdressKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WALLET_ADDRESS
     *
     * @mbggenerated Wed Feb 08 16:39:02 CST 2017
     */
    @UpdateProvider(type=WalletAdressSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") WalletAdress record, @Param("example") WalletAdressExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WALLET_ADDRESS
     *
     * @mbggenerated Wed Feb 08 16:39:02 CST 2017
     */
    @UpdateProvider(type=WalletAdressSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") WalletAdress record, @Param("example") WalletAdressExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WALLET_ADDRESS
     *
     * @mbggenerated Wed Feb 08 16:39:02 CST 2017
     */
    @UpdateProvider(type=WalletAdressSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(WalletAdress record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WALLET_ADDRESS
     *
     * @mbggenerated Wed Feb 08 16:39:02 CST 2017
     */
    @Update({
        "update WALLET_ADDRESS",
        "set MERCHANT_ID = #{merchantId,jdbcType=VARCHAR},",
          "APP_ID = #{appId,jdbcType=VARCHAR},",
          "OPEN_ID = #{openId,jdbcType=VARCHAR},",
          "PRODUCT_ID = #{productId,jdbcType=VARCHAR},",
          "WALLET_CODE = #{walletCode,jdbcType=VARCHAR},",
          "WALLET_ADDRESS = #{walletAddress,jdbcType=VARCHAR},",
          "CREATE_TIME = #{createTime,jdbcType=TIMESTAMP}",
        "where WALLET_ID = #{walletId,jdbcType=CHAR}"
    })
    int updateByPrimaryKey(WalletAdress record);
}