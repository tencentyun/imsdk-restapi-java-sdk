JavaServerSDK
[NOTE] README.txt and all src files are in utf8 encoding

1.目录结构及作用说明:
	com.tim.config:
		SigConfig.java:主要是加载配置文件,具体配置内容见sigconfig.properties
	
	com.tim.exception:
		ConfigFileException.java:加载配置文件失败时抛出的异常
		SigException.java:生成signature时失败抛出的异常
	
	com.tim.restapi:
		HttpPost.java:主要发送http的post请求
		RestApi.java:提供发送RestApi的接口
	
	com.tim.util:
		SigUtil.java:调用tls_sigature生成签名
	
	demo:
		demo.java:例子,具体讲解了怎样发送RestApi请求
		
	lib:
		TLS后台API，用于生成signature
		关于TLS的详细说明请参考 https://www.qcloud.com/doc/product/269/1510#2.4-java.E5.8E.9F.E7.94.9F.E6.8E.A5.E5.8F.A3
		
2.配置文件sigconfig.properties说明:
	identifier:顾名思义
	sdkappid:顾名思义
	expire:设置生成的sig的过期时间
	privateKey:私钥文件

3.使用方法:
	a.实例化RestApi,如下:
		RestApi restApi = new RestApi();
	b.设置配置文件路径,如下:
		String configFile = "D:/jee-workspace/ServerSDK/src/sigconfig.properties";
		restApi.SetConfigFile(configFile);
	c.根据serviceName和cmdName,对具体不同的请求,封装json格式的reqBody,这里以openim/sendmsg为例:
		请参考demo.java文件里的JSONObject GetReqBody(String fromAccount, String toAccount, String text)方法
	d.调用RestApi的DoRequest方法发送post请求,返回值为json格式(或者抛出异常),如下:
		JSONObject json = restApi.DoRequest(serviceName, cmdName, reqBody);
	e.根据RestApi.DoRequest的返回值判断发送是否ok,若是失败,请根据返回码查询具体失败原因:
		错误码: https://www.qcloud.com/doc/product/269/1671
	
	
	