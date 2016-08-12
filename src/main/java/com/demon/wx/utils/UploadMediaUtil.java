package com.demon.wx.utils;

import java.io.File;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.demon.wx.common.Constants;
import com.demon.wx.common.MediaType;

public class UploadMediaUtil {
	
	/**
	 * <p>Description: 微信服务器素材上传</p>
	 * @author dmeng
	 * @date 2016年8月11日 下午4:59:37
	 * @param url
	 * @param accessToken
	 * @param file
	 * @param type type只支持四种类型素材(video/image/voice/thumb)
	 * @return
	 */
	public static JSONObject uploadMedia(String accessToken, File file, String type) {
		HttpClient client = new HttpClient();
		String url = String.format(Constants.UPLOAD_IMAGE_URL, accessToken, type);
		PostMethod post = new PostMethod(url);
		post.setRequestHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.9; rv:30.0) Gecko/20100101 Firefox/30.0");
		post.setRequestHeader("Host", "file.api.weixin.qq.com");
		post.setRequestHeader("Connection", "Keep-Alive");
		post.setRequestHeader("Cache-Control", "no-cache");
		JSONObject json = new JSONObject(); 
		try {
			if(file == null || !file.exists()) {
				throw new Exception("文件不存在");
			}
			// FilePart：用来上传文件的类,file即要上传的文件
			FilePart media = new FilePart("media", file);
			Part[] parts = new Part[]{new StringPart("access_token", accessToken), new StringPart("type", type), media};
			// 对于MIME类型的请求，httpclient建议全用MulitPartRequestEntity进行包装
			MultipartRequestEntity entity = new MultipartRequestEntity(parts, post.getParams());
			post.setRequestEntity(entity);
			// 如果文件很大可以设置超时时间，默认为0表示不超时，单位是毫秒
			//client.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
			System.out.println(">>>>准备就绪，开始上传。。。");
			int status = client.executeMethod(post);
			if(status == HttpStatus.SC_OK) {
				String retStr = post.getResponseBodyAsString();
				json = JSON.parseObject(retStr);
				if(json != null && json.getInteger("errcode") == null) {
					System.out.println("上传成功！");
				}
			} else {
				System.out.println("上传失败，状态：" + status);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return json;
	}
	
	public static JSONObject uploadMedia(String filePath, String mediaType) {
		JSONObject json = new JSONObject();
		if(StringUtils.isBlank(filePath) || StringUtils.isBlank(mediaType)) {
			json.put("errcode", 1);
			json.put("errmsg", "参数错误");
		} else {
			File file = new File(filePath);
			String accessToken = getAccessToken();
			json = uploadMedia(accessToken, file, mediaType);
		}
		return json;
	}
	
	public static void main(String[] args) {
		String path = "E:\\pic\\WallPapers\\miku.jpg"; // DMA3mMrpY7tQL1kofI7GTBRFeavMpZEsh285ZlzrhYKEWehQyNBFLRmF40RfnfIS
		//String path = "D:\\Workspaces\\wechat\\src\\main\\webapp\\material\\yanyuan.mp3";
		//String path = "C:\\Users\\365\\Documents\\狸窝\\全能视频转换器\\RememberTheName_3.mp4";
		JSONObject json = uploadMedia(path, MediaType.IMAGE.getType());
		System.out.println(json);
	}
	
	/**
	 * <p>Description: 获取access_token</p>
	 * @author dmeng
	 * @date 2016年8月11日 下午3:49:41
	 * @return
	 */
	public static String getAccessToken() {
		String accessTokenUrl = String.format(Constants.GET_ACCESS_TOKEN_URL, Constants.APP_ID, Constants.APP_SECRET);
		String result = NetWorkHelper.sendRequest(accessTokenUrl);
		JSONObject json = JSON.parseObject(result);
		return json.getString("access_token");
	}
}
