package com.attendee.attendee.sms;

import org.springframework.stereotype.Service;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;

@Service
public class SmsService {

	private static final String domain = "dysmsapi.ap-southeast-1.aliyuncs.com";
	private static final String regionId="ap-southeast-1";
	private static final String accessKeyId = "LTAIgIip5nffvLF5";
	private static final String accessKeySecret = "HhLtxTftFss1tEevTDMQRmbxyFwJue";
    	
	public void sendSms(Sms sms) throws Exception{
		
		DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
		IAcsClient client = new DefaultAcsClient(profile);

		CommonRequest request = new CommonRequest();

		request.setMethod(MethodType.POST);
	    request.setDomain(domain);
	    request.setVersion("2018-05-01");
	    request.setAction("SendMessageToGlobe");	    request.putQueryParameter("To", sms.getPhoneNumber());
	    request.putQueryParameter("Message", sms.getMessage());
	    
	    try {
	    	
	        CommonResponse response = client.getCommonResponse(request);
	        System.out.println(response.getData());
	        
	    } catch (ServerException e) {
	        e.printStackTrace();
	        throw new Exception(e.getMessage());
	    } catch (ClientException e) {
	        e.printStackTrace();
	        throw new Exception(e.getMessage());
	    }
	}

}
