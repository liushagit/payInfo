package com.orange.platform.bill.common.domain;

import java.io.Serializable;

/**
 * 
 * @author author
 *
 */
public class FeeCommonInfo implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String cpId;//商户提供对CP的标识号
	private String orderId;//订单编号
	private String feeCode;//计费代码
	private String appId;//游戏ID
	private String channel;//渠道
	private String propId;//道具ID
	private int fee;//信息费	本次订单充值金额
	private String channelName;//渠道名字
	private String cm;//渠道编号
	private String resultCode;//支付结果	
	private String notifyId;//通知校验 ID
	private String tradeId;//交易号
	private String actiontime;//通知时间
	private String userId;//商户用户 ID
	private String account;//商户用户账号
	private String extdata;//自定义字段	
	private String port;//短信发送端口号
	private String mobile;//手机号
	private String weimiOrderId;//自生成订单号（发起计费时流水号）
	private String channelId;//渠道分配的id
	private String provinceId;//省份Id
	private String provinceName;//省份名称
	private String paymentType;//支付类型
	private String partnerType;//合作方类型
	private String appName;//游戏名称
	private String content;//短信内容
	private String description;
	
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}
	public String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	public String getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	public String getPartnerType() {
		return partnerType;
	}
	public void setPartnerType(String partnerType) {
		this.partnerType = partnerType;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getChannelId() {
		return channelId;
	}
	public void setChannelId(String channelId) {
		this.channelId = channelId;
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
	public String getweimiOrderId() {
		return weimiOrderId;
	}
	public void setweimiOrderId(String weimiOrderId) {
		this.weimiOrderId = weimiOrderId;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getCpId() {
		return cpId;
	}
	public void setCpId(String cpId) {
		this.cpId = cpId;
	}
	public String getFeeCode() {
		return feeCode;
	}
	public void setFeeCode(String feeCode) {
		this.feeCode = feeCode;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	public String getPropId() {
		return propId;
	}
	public void setPropId(String propId) {
		this.propId = propId;
	}
	public int getFee() {
		return fee;
	}
	public void setFee(int fee) {
		this.fee = fee;
	}
	public String getCm() {
		return cm;
	}
	public void setCm(String cm) {
		this.cm = cm;
	}
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public String getNotifyId() {
		return notifyId;
	}
	public void setNotifyId(String notifyId) {
		this.notifyId = notifyId;
	}
	public String getActiontime() {
		return actiontime;
	}
	public void setActiontime(String actiontime) {
		this.actiontime = actiontime;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getExtdata() {
		return extdata;
	}
	public void setExtdata(String extdata) {
		this.extdata = extdata;
	}
	

}
