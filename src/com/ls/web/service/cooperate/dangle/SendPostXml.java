package com.ls.web.service.cooperate.dangle;

import java.io.PrintWriter;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;

import com.ls.pub.util.encrypt.MD5Util;
public class SendPostXml {
	
	public static String getReponse(String url, String para, String method){ 
		String rtn = ""; 
		boolean isGet = (method == null) || method.trim().equals("") 
		|| method.trim().equalsIgnoreCase("get"); 

		PrintWriter xmlOut = null; 
		URL urlObj = null; 
		HttpURLConnection acon = null; 
		try{ 
		urlObj = new URL(url); 
		acon = (HttpURLConnection) urlObj.openConnection(); 
		acon.setAllowUserInteraction(false); 

		if (isGet) { 
		acon.setRequestMethod("GET");                
		} else { 
		acon.setRequestMethod("POST"); 
		acon.setRequestProperty("Content-Type", "application/xml"); 
		} 

		acon.setDoOutput(true); 
		acon.setDoInput(true); 
		acon.setUseCaches(false);            

		if(!isGet){ 
		//写入xml参数 
		xmlOut = new java.io.PrintWriter(acon.getOutputStream()); 
		xmlOut.write(para); 
		xmlOut.flush(); 
		} 
		long st = System.currentTimeMillis(); 
		if (acon.getResponseCode() >= 400) {             
		    rtn = " <?xml version=\"1.0\" encoding=\"UTF-8\"?> <servlet-exception>"; 
		    rtn += "HTTP response: " + acon.getResponseCode() + "\n" 
		        + URLDecoder.decode(acon.getResponseMessage(), "UTF-8"); 
		    rtn += " </servlet-exception>";                         
		} else { 
		//读出结果 
		StringBuffer sb = new StringBuffer(); 
		int c; 
		InputStream in = acon.getInputStream(); 

		while ((c = in.read()) != -1) 
		sb.append((char)c); 

		in.close();   
		rtn = sb.toString(); 
		} 

		rtn = new String(rtn.getBytes("ISO8859-1"), "UTF-8"); 
		////System.out.println("GisRequester>>:" + (System.currentTimeMillis() - st)); 
		}catch(Exception e){ 
		e.printStackTrace(); 
		} 


		return rtn; 
		} 
	//发送xml格式文件
	public static String send(String url, String para, String method){ 
		
		String rtn = ""; 
		boolean isGet = (method == null) || method.trim().equals("") 
		|| method.trim().equalsIgnoreCase("get"); 
	
		PrintWriter xmlOut = null; 
		URL urlObj = null; 
		HttpURLConnection acon = null; 
		try{ 
			urlObj = new URL(url); 
			acon = (HttpURLConnection) urlObj.openConnection(); 
			acon.setDoOutput(true);
			if (isGet) { 
				acon.setRequestMethod("GET");                
			} else { 
				////System.out.println("POST para="+para);
				acon.setRequestMethod("POST"); 
				acon.setRequestProperty("Content-Type", "application/xml"); 
			} 
			acon.getOutputStream().write(para.getBytes()); 
			acon.getOutputStream().flush(); 
			acon.getOutputStream().close();
			int code=acon.getResponseCode();
			////System.out.println("code num="+code);
			
			if (acon.getResponseCode() == 200) {             
				//读出结果 
				StringBuffer sb = new StringBuffer(); 
				int c; 
				InputStream in = acon.getInputStream(); 

				while ((c = in.read()) != -1) 
				sb.append((char)c); 

				in.close();   
				rtn = sb.toString();    
				rtn = new String(rtn.getBytes("ISO8859-1"), "UTF-8"); 
				////System.out.println("结果 rtn="+rtn);
			} 
		}catch(Exception e){ 
		e.printStackTrace(); 
		} finally{
			if(acon!=null)
			{
				acon.disconnect();
			}
		}
		return rtn; 
	} 
	
	/**
	 * 给当乐发送充值确认            
	 * @param userName            用户在当乐注册的用户名
	 * @param amount              用户支付的实际金额，单位为元
	 * @param pc_id               用户支付使用的通道ID，支付通道对照表由当乐提供给商户,详见:表格 3 pc-id（付款渠道）对照表
	 * @param unique_str          唯一编号,订单号或系统生成的唯一序列号，由游戏厂商生成(防止重复提交)系统中会根据merchant-idgame-idserver-idseq-str唯一匹配数据
	 * @return
	 */
	public static String sendPostToDangle(String userName,String amount,String pc_id,String unique_str)
	{
		String url = "http://189hi.cn/plaf/xml/gi.consume";
		String methed = "post";
		
		String merchant_key = "xWx86231";
		
		//商户编号,商户在当乐的身份标识，由当乐告知商户
		String merchant_id = "17";
		//游戏编号
		String game_id = "1";
		//用户所在的游戏服务器ID，默认为1
		String server_id = "1";
		//用户在当乐注册的用户名
		//String userName = "";
		//用户支付的实际金额，单位为元
		//String amount = "";
		//用户支付使用的通道ID，支付通道对照表由当乐提供给商户,详见:表格 3 pc-id（付款渠道）对照表
		//String pc_id = "";
		//唯一编号,订单号或系统生成的唯一序列号，由游戏厂商生成(防止重复提交)系统中会根据merchant-idgame-idserver-idseq-str唯一匹配数据
		//String unique_str = "";
		//时间标签，格式:yyyymmddHH24mmss，有效时间（暂定为半小时），注意双方服务器时间统一，月，日小时，分，秒小于10前面补充0，例如:200901011208
		String timestamp = new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date());
		//MD5验证串,32位小写MD5验证串，生成规则参见下方说明
		String verify_string = "";
		//加密类型,默认为0，所有字段均不加密
		String verify_type = "0";
		
		String callback = "merchant-id="+merchant_id+"&game-id="+game_id+"&server-id="+server_id+"&username="+userName+"&amount="+amount+"&pc-id="+pc_id+"&merchant-key="+merchant_key+"&unique-str="+unique_str+"&timestamp="+timestamp+"&encrypt-type="+verify_type;
		
		String newHmac =MD5Util.md5Hex(callback);
		verify_string = newHmac;
		
		StringBuffer para = new StringBuffer();;
		para.append("<?xml version='1.0' encoding='UTF-8'?>");
		para.append("<request>");
		para.append("<merchant-id>"+merchant_id+"</merchant-id>");
		para.append("<game-id>"+game_id+"</game-id>");
		para.append("<server-id>"+server_id+"</server-id>");
		para.append("<username>"+userName+"</username>");
		para.append("<amount>"+amount+"</amount>");
		para.append("<pc-id>"+pc_id+"</pc-id>");
		para.append("<unique-str>"+unique_str+"</unique-str>");
		para.append("<timestamp>"+timestamp+"</timestamp>");
		para.append("<verify-string>"+verify_string+"</verify-string>");
		para.append("<encrypt-type>"+verify_type+"</encrypt-type>");
		para.append("</request>");
		
		////System.out.println(para.toString());
		return SendPostXml.send(url,para.toString(),methed);	    
	}
	
	public static void main( String[] agrs )
	{
		/*String url = "http://189hi.cn/plaf/xml/gi.consume";
		String methed = "post";
		
		String merchant_id = "17";
		String game_id = "1";
		String server_id = "1";
		String userName = "devil";
		String amount = "30";
		String pc_id = "1";
		String unique_str = "123";//订单号
		String timestamp = new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date());
		String verify_string = "";
		String verify_type = "0";
		
		SendPostXml.sendPostToDangle(userName, amount, pc_id, unique_str);*/
		
		String url = "http://122.224.217.162:40001/authssid?ssid=6Gp4xwuk";
		String test = SendPostXml.send(url, "ssid:RVtQjuLq", "post");
		System.out.print("test:"+test);
	}
}
