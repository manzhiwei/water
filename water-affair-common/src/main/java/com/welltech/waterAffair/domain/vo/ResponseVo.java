package com.welltech.waterAffair.domain.vo;

public class ResponseVo<T> {
	/**返回码*/
	private String responseCode;
	/**返回描述*/
	private String responseMsg;
	/**返回数据对象*/
	private T data;
	public String getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}
	public String getResponseMsg() {
		return responseMsg;
	}
	public void setResponseMsg(String responseMsg) {
		this.responseMsg = responseMsg;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	
	
}
