package com.demon.wx.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.demon.wx.utils.MessageHandler;

@WebServlet(urlPatterns="/wechat")	
public class WechatServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private final String TOKEN = "demon";
	
	/**
	 * 微信接入 
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("WechatServlet.doGet()>开始验证签名...");
	
		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		String echostr = request.getParameter("echostr");
		
		String sortString = sort(TOKEN, timestamp, nonce);
		String mySignature = sha1(sortString);
		if(StringUtils.isNoneBlank(mySignature) && mySignature.equals(signature)) {
			System.out.println(">>>签名校验通过");
			response.getWriter().write(echostr);
		} else {
			response.getWriter().write("签名校验失败");
		}
		
		
	}

	/**
	 * 处理微信服务器发来的消息
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		long start = System.currentTimeMillis();
		System.out.println("处理微信请求..." + start);
		// 设置编码防止乱码问题
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String result = "";
		try {
			Map<String, String> map = MessageHandler.parseXML(request);
			result = MessageHandler.getResponse(map);
			System.out.println("return message >" + result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		PrintWriter out = response.getWriter();  
        out.print(result);  
        long end = System.currentTimeMillis();
        System.out.println("结束：" + end);
        System.out.println("处理完成！耗时：" + (end-start) + "ms");
        out.close(); 
	}
	

	/**
	 * <p>Description: 字典排序</p>
	 * @author dmeng
	 * @date 2016年7月14日 下午2:26:59
	 * @param token
	 * @param timestamp
	 * @param nonce
	 * @return
	 */
	private String sort(String token, String timestamp, String nonce) {
		String[] strArray = {token, timestamp, nonce};
		Arrays.sort(strArray);
		StringBuilder sb = new StringBuilder();
		for(String str : strArray) {
			sb.append(str);
		}
		return sb.toString();
	}
	
	/**
	 * <p>Description: sha1加密</p>
	 * @author dmeng
	 * @date 2016年7月14日 下午2:26:42
	 * @param str
	 * @return
	 */
	public String sha1(String str) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-1");
			digest.update(str.getBytes());
			byte messageDigest[] = digest.digest();
			// Create Hex String
			StringBuffer hexString = new StringBuffer();
			// 字节数组转换为 十六进制 数
			for (int i = 0; i < messageDigest.length; i++) {
				String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
				if (shaHex.length() < 2) {
					hexString.append(0);
				}
				hexString.append(shaHex);
			}
			return hexString.toString();

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "";
 }
	
}
