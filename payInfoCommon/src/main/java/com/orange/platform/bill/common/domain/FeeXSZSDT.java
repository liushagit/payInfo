/**
 * ChinaMobileMarket
 * com.orange.game.china.mobile.market.domain
 * MarketOrder.java
 */
package com.orange.platform.bill.common.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 移动MM
 * 
 * @author author
 * 
 *         2012-10-23
 */
public class FeeXSZSDT implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//
	private int id;
	private String orderId;
	private String tradeId;
	private String appId;
	private String channelId;
	private int actionId;
	private Date actionTime;
	private String msisdn;
	private String msisdnFee;
	private String payCode;
	private int price;
	private int num;
	private int totalPrice;
	private String ext;
	private String prefixAppId;
	private String prefixChannel;
	private String prefixProp;
	private String channel;
	private String propId;
	private String result;
	private String linkId;

	public String getLinkId() {
		return linkId;
	}

	public void setLinkId(String linkId) {
		this.linkId = linkId;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getPropId() {
		return propId;
	}

	public void setPropId(String propId) {
		this.propId = propId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getTradeId() {
		return tradeId;
	}

	public void setTradeId(String tradeId) {
		this.tradeId = tradeId;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public int getActionId() {
		return actionId;
	}

	public void setActionId(int actionId) {
		this.actionId = actionId;
	}

	public Date getActionTime() {
		return actionTime;
	}

	public void setActionTime(Date actionTime) {
		this.actionTime = actionTime;
	}

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public String getMsisdnFee() {
		return msisdnFee;
	}

	public void setMsisdnFee(String msisdnFee) {
		this.msisdnFee = msisdnFee;
	}

	public String getPayCode() {
		return payCode;
	}

	public void setPayCode(String payCode) {
		this.payCode = payCode;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public int getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

	public String getPrefixAppId() {
		return prefixAppId;
	}

	public void setPrefixAppId(String prefixAppId) {
		this.prefixAppId = prefixAppId;
	}

	public String getPrefixChannel() {
		return prefixChannel;
	}

	public void setPrefixChannel(String prefixChannel) {
		this.prefixChannel = prefixChannel;
	}

	public String getPrefixProp() {
		return prefixProp;
	}

	public void setPrefixProp(String prefixProp) {
		this.prefixProp = prefixProp;
	}
}
