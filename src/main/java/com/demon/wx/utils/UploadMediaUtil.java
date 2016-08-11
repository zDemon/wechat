package com.demon.wx.utils;

import java.io.File;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.demon.wx.common.Constants;

public class UploadMediaUtil {
	
	public static String uploadImage(String url, String accessToken, File file, String type) {
		HttpClient client = new HttpClient();
		url = String.format(url, accessToken, type);
		System.out.println(url);
		PostMethod post = new PostMethod(url);
		post.setRequestHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.9; rv:30.0) Gecko/20100101 Firefox/30.0");
		post.setRequestHeader("Host", "file.api.weixin.qq.com");
		post.setRequestHeader("Connection", "Keep-Alive");
		post.setRequestHeader("Cache-Control", "no-cache");
		String result = "";
		try {
			if(file == null || !file.exists()) {
				throw new Exception("文件不存在");
			}
			// FilePart：用来上传文件的类,file即要上传的文件
			FilePart filePart = new FilePart("media", file, "iamge/jpeg", "UTF-8");
			Part[] parts = new Part[]{filePart};
			// 对于MIME类型的请求，httpclient建议全用MulitPartRequestEntity进行包装
			MultipartRequestEntity entity = new MultipartRequestEntity(parts, post.getParams());
			post.setRequestEntity(entity);
			// 如果文件很大可以设置超时时间，默认为0表示不超时，单位是毫秒
			client.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
			int status = client.executeMethod(post);
			if(status == HttpStatus.SC_OK) {
				String retStr = post.getResponseBodyAsString();
				JSONObject json = JSON.parseObject(retStr);
				if(json != null && json.getInteger("errcode") == null) {
					System.out.println("上传成功！");
					result = json.getString("media_id");
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static void main(String[] args) {
		String accessTokenUrl = Constants.GET_ACCESS_TOKEN_URL;
		accessTokenUrl = String.format(accessTokenUrl, Constants.APP_ID, Constants.APP_SECRET);
		String result = NetWorkHelper.sendRequest(accessTokenUrl);
		System.out.println("获取到的AccessToken>" + result);    
		JSONObject json = JSON.parseObject(result);
		String accessToken = json.getString("access_token");
		File file = new File("E:\\pic\\下限酱171.jpg");
		String mediaId = uploadImage(Constants.UPLOAD_IMAGE_URL, accessToken, file, "image");
		System.out.println(mediaId);
	}
}
