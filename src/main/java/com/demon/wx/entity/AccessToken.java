package com.demon.wx.entity;

public class AccessToken {

	// access_token凭证
	private String accessToken;
	// 凭证过期时间，单位秒
	private int expireIn;
	
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public int getExpireIn() {
		return expireIn;
	}
	public void setExpireIn(int expireIn) {
		this.expireIn = expireIn;
	}
	
	
	
}
