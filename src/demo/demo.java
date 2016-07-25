package demo;

import java.util.Random;
import org.json.JSONArray;
import org.json.JSONObject;

import com.tim.restapi.RestApi;

public class demo {

	public static void main(String[] args) {
		Test_RestApi_sendMsg();
	}
	
	public static void Test_RestApi_sendMsg() {
		String fromAccount = "admin";
		String toAccount = "admin";
		String text = "hi, tim";
		RestApi_SendMsg(fromAccount, toAccount, text);
	}
	
	public static void RestApi_SendMsg(String fromAccount, String toAccount, String text) {
		//get reqBody
		JSONObject reqBody = GetReqBody(fromAccount, toAccount, text);
		
		//set serviceName and cmdName
		String serviceName = "openim";
		String cmdName = "sendmsg";
		
		//set configure file, use the absolute path
		String configFile = "/Users/c9/Downloads/JavaServerSDK/src/sigconfig.properties";
		
		//use RestApi instance to send request
		RestApi restApi = new RestApi();
		restApi.SetConfigFile(configFile);
		JSONObject json = restApi.DoRequest(serviceName, cmdName, reqBody);
		
		//output the DoRequest result
		System.out.println(String.format("result:%s", json.toString()));
	}
	
	public static JSONObject GetReqBody(String fromAccount, String toAccount, String text) {
		Random rand = new Random();

		long msgRandom = rand.nextInt() & 0xffffffffl;
		long msgTimeStamp = System.currentTimeMillis()/1000;
		//text object
		JSONObject textObject = new JSONObject();
		textObject.put("Text", text);
		
		//msgbody object
		JSONObject msgbodyObject = new JSONObject();
		msgbodyObject.put("MsgContent", textObject);
		msgbodyObject.put("MsgType", "TIMTextElem");
		
		JSONArray msgbodyArray = new JSONArray();
		
		msgbodyArray.put(msgbodyObject);
		
		JSONObject reqBody = new JSONObject();
		reqBody.put("From_Account", fromAccount);
		reqBody.put("To_Account", toAccount);
		reqBody.put("MsgRandom", msgRandom);
		reqBody.put("MsgTimeStamp", msgTimeStamp);
		reqBody.put("MsgBody", msgbodyArray);
		System.out.println(String.format("reqBody:%s", reqBody.toString()));
		
		return reqBody;
	}
}
