package com.fr.chain.trade.db.entity;

public class TradeOrderKey {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TRADE_ORDER.ORDER_ID
     *
     * @mbggenerated Fri Feb 24 10:10:33 CST 2017
     */
    private String orderId;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TRADE_ORDER.ORDER_ID
     *
     * @return the value of TRADE_ORDER.ORDER_ID
     *
     * @mbggenerated Fri Feb 24 10:10:33 CST 2017
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TRADE_ORDER.ORDER_ID
     *
     * @param orderId the value for TRADE_ORDER.ORDER_ID
     *
     * @mbggenerated Fri Feb 24 10:10:33 CST 2017
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TRADE_ORDER
     *
     * @mbggenerated Fri Feb 24 10:10:33 CST 2017
     */
    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        TradeOrderKey other = (TradeOrderKey) that;
        return (this.getOrderId() == null ? other.getOrderId() == null : this.getOrderId().equals(other.getOrderId()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TRADE_ORDER
     *
     * @mbggenerated Fri Feb 24 10:10:33 CST 2017
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getOrderId() == null) ? 0 : getOrderId().hashCode());
        return result;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TRADE_ORDER
     *
     * @mbggenerated Fri Feb 24 10:10:33 CST 2017
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", orderId=").append(orderId);
        sb.append("]");
        return sb.toString();
    }
}