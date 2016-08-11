package com.demon.wx.web;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.demon.wx.common.AccessTokenInfo;
import com.demon.wx.entity.AccessToken;
import com.demon.wx.utils.NetWorkHelper;

@WebServlet(
		urlPatterns = "/accessToken",
		loadOnStartup = 1,
		initParams = {
				@WebInitParam(name="appId", value="wxa44fcbe4e00d3a39"),
				@WebInitParam(name="appSecret", value="f96abe54abcdb82a2c8ea3e5a69652a1")
		}
	)
public class AccessTokenServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void init() throws ServletException {
		System.out.println("AccessTokenServlet.init()...");
		super.init();
		
		final String appId = getInitParameter("appId");
		final String appSecret = getInitParameter("appSecret");
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				while(true) {
					try {
						// 获取access_token
						System.out.println("开始获取access_token...");
						AccessTokenInfo.accessToken = getAccessToken(appId, appSecret);
						// 获取成功
						if(AccessTokenInfo.accessToken != null) {
							// 获取到access_token,休眠7000s,微信服务器的过期时间是7200s,保留200s安全时间
							Thread.sleep(7000 * 1000);
						} else {
							Thread.sleep(3 * 1000);
						}
					} catch(Exception e) {
						try {
							Thread.sleep(3 * 1000);
						} catch (InterruptedException e1) {
							e1.printStackTrace();
						}
						e.printStackTrace();
					}
				}
			}
			
		}).start();
		
	}
	
	/**
	 * <p>Description: 请求接口获取access_token</p>
	 * @author dmeng
	 * @date 2016年7月21日 上午9:36:14
	 * @param appId
	 * @param appSecret
	 * @return
	 */
	private AccessToken getAccessToken(String appId, String appSecret) {
		String url = String.format("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s", appId, appSecret);
		String result = NetWorkHelper.sendRequest(url);
		System.out.println("获取到的AccessToken>" + result);
		JSONObject json = JSON.parseObject(result);
		AccessToken accessToken = new AccessToken();
		accessToken.setAccessToken(json.getString("access_token"));
		accessToken.setExpireIn(json.getIntValue("expires_in"));
		return accessToken;
	}

}
