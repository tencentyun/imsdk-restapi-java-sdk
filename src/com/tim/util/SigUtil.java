package com.tim.util;
import java.io.*;

import com.tim.config.SigConfig;
import com.tim.exception.SigException;
import com.tls.tls_sigature.tls_sigature;
import com.tls.tls_sigature.tls_sigature.*;

public class SigUtil { 
	
	private static String privatekey = "";
	private static String usersig = "";
	private static long usersig_gentime = 0;
	
	public static String GetSignature()	throws SigException{
		boolean needGenSig = false;
		do
		{
			if(usersig == "" || usersig_gentime == 0)
			{
				needGenSig = true;
				break;
			}

			long expect_expire_time = usersig_gentime + SigConfig.expire;
			long nowtime = System.currentTimeMillis();
			if(expect_expire_time < nowtime)
			{
				needGenSig = true;
				break;
			}

		}while(false);

		if(needGenSig)
		{
			GenSignature();
		}
		
		if(usersig == "")
		{
			throw new SigException("invalid signature");
		}
		return usersig;
	}
	
    private static void GenSignature() throws SigException {
    	
    	if(privatekey == "") {
    		try {
        	//read pem file
        	File priKeyFile = new File(SigConfig.privateKey);
            StringBuilder strBuilder = new StringBuilder();
            String line = "";
            BufferedReader br = new BufferedReader(new FileReader(priKeyFile));
            while ((line = br.readLine()) != null) {
                strBuilder.append(line + '\n');
            }
            br.close();
            privatekey = strBuilder.toString();
    		} catch (Exception e) {
    			throw new SigException(e.getMessage());
    		}
    	}

    	GenTLSSignatureResult result = null;
    	try {
    		
    	/**
    	 * @brief 生成 tls 票据，精简参数列表
    	 * @param skdAppid 应用的 sdkappid
    	 * @param identifier 用户 id
    	 * @param privStr 私钥文件内容
    	 * @param expire 有效期，以秒为单位，推荐时长一个月
    	 * @return
    	 * @throws IOException
    	 */
    	result = tls_sigature.GenTLSSignatureEx(
    			SigConfig.sdkappid, 
    			SigConfig.identifier,
    			privatekey,
    			SigConfig.expire
    			);
    	} catch (IOException e) {
    		throw new SigException("tls_sigature.GenTLSSignatureEx failed: " + e.getMessage());
    	}
    	
        if (0 == result.urlSig.length()) {
    		usersig = "";
    		throw new SigException("tls_sigature.GenTLSSignatureEx failed: " + result.errMessage);
        } else {
   		 	usersig = result.urlSig;
   		 	usersig_gentime = System.currentTimeMillis();
        }
    }
}
