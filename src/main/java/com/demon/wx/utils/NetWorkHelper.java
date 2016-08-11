package com.demon.wx.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.lang3.StringUtils;

/**
 * <p>Description: http请求工具类</p>
 * @author dmeng
 * @date 2016年7月14日 下午3:22:10
 */
public class NetWorkHelper {
	
	/**
	 * <p>Description: 发送http请求,默认使用get方法</p>
	 * @author dmeng
	 * @date 2016年7月20日 下午3:16:39
	 * @param reqUrl
	 * @return
	 */
	public static String sendRequest(String reqUrl) {
		return sendRequest(reqUrl, "GET");
	}
	
	/**
	 * <p>Description: 发送http请求</p>
	 * @author dmeng
	 * @date 2016年7月14日 下午4:07:35
	 * @param reqUrl
	 * @param reqMethod
	 * @return
	 */
	public static String sendRequest(String reqUrl, String reqMethod) {
		URL url = null;
		InputStream is = null;
		String result = "";
		
		try {
			url = new URL(reqUrl);
			HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
			 // 创建SSLContext对象，并使用我们指定的信任管理器初始化  
			TrustManager[] tm = {xtm};
			
			SSLContext ctx = SSLContext.getInstance("TLS");
			ctx.init(null, tm, null);

			con.setSSLSocketFactory(ctx.getSocketFactory());
			con.setHostnameVerifier(new HostnameVerifier() {
				@Override
				public boolean verify(String arg0, SSLSession arg1) {
					return true;
				}
			});
			
			con.setDoInput(true); 		// 允许输入流，即允许下载
			con.setDoOutput(true);		// 允许输出流，即允许上传
			con.setUseCaches(false);	// 不使用缓存
			
			// 设置请求方式(GET/POST)
			con.setRequestMethod(StringUtils.isBlank(reqMethod) ? "GET" : reqMethod);
			
			// 获取输入流,真正建立请求
			is = con.getInputStream();
			InputStreamReader isr = new InputStreamReader(is); 
			BufferedReader br = new BufferedReader(isr);
			String inputLine;
			while((inputLine = br.readLine()) != null) {
				result += inputLine + "\n";
			}
			System.out.println("result > " + result);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	 static X509TrustManager xtm = new X509TrustManager() {
		@Override
		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}

		@Override
		public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {

		}

		@Override
		public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {

		}
	};
	
}
