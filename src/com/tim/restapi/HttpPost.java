package com.tim.restapi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import org.json.JSONObject;

import com.tim.config.SigConfig;
import com.tim.exception.SigException;
import com.tim.util.SigUtil;;

public class HttpPost {
	private static final String HOST = "https://console.tim.qq.com";

	public static JSONObject doPost(String serviceName, String cmdName, JSONObject reqBody) 
			throws SigException{
		String usersig = SigUtil.GetSignature();
		String url = formatUrl(serviceName, cmdName, usersig);
		return post(url, reqBody);
	}

	private static String formatUrl(String serviceName, String cmdName, String usersig) {
		String url = String.format("%s/v4/%s/%s?usersig=%s&identifier=%s&sdkappid=%s&contenttype=json", 
				HOST, serviceName, cmdName, usersig, SigConfig.identifier, SigConfig.sdkappid);
		return url;
	}

	private static JSONObject post(String url, JSONObject reqBody) {
		PrintWriter out = null;
		BufferedReader in = null;
		JSONObject json = null;        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(reqBody);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            String result = "";
            while ((line = in.readLine()) != null) {
                result += line;
            }
            
            json = new JSONObject(result);
            
        } catch (Exception e) {
            e.printStackTrace();
        }

        //使用finally块来关闭输出流、输入流
		finally{
			try{
				if(out!=null){
					out.close();
				}
				if(in!=null){
					in.close();
				}
			}
			catch(IOException ex){
				ex.printStackTrace();
			}
		}
		return json;
	} 

}
