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
		//д��xml���� 
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
		//������� 
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
	//����xml��ʽ�ļ�
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
				//������� 
				StringBuffer sb = new StringBuffer(); 
				int c; 
				InputStream in = acon.getInputStream(); 

				while ((c = in.read()) != -1) 
				sb.append((char)c); 

				in.close();   
				rtn = sb.toString();    
				rtn = new String(rtn.getBytes("ISO8859-1"), "UTF-8"); 
				////System.out.println("��� rtn="+rtn);
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
	 * �����ַ��ͳ�ֵȷ��            
	 * @param userName            �û��ڵ���ע����û���
	 * @param amount              �û�֧����ʵ�ʽ���λΪԪ
	 * @param pc_id               �û�֧��ʹ�õ�ͨ��ID��֧��ͨ�����ձ��ɵ����ṩ���̻�,���:��� 3 pc-id���������������ձ�
	 * @param unique_str          Ψһ���,�����Ż�ϵͳ���ɵ�Ψһ���кţ�����Ϸ��������(��ֹ�ظ��ύ)ϵͳ�л����merchant-idgame-idserver-idseq-strΨһƥ������
	 * @return
	 */
	public static String sendPostToDangle(String userName,String amount,String pc_id,String unique_str)
	{
		String url = "http://189hi.cn/plaf/xml/gi.consume";
		String methed = "post";
		
		String merchant_key = "xWx86231";
		
		//�̻����,�̻��ڵ��ֵ���ݱ�ʶ���ɵ��ָ�֪�̻�
		String merchant_id = "17";
		//��Ϸ���
		String game_id = "1";
		//�û����ڵ���Ϸ������ID��Ĭ��Ϊ1
		String server_id = "1";
		//�û��ڵ���ע����û���
		//String userName = "";
		//�û�֧����ʵ�ʽ���λΪԪ
		//String amount = "";
		//�û�֧��ʹ�õ�ͨ��ID��֧��ͨ�����ձ��ɵ����ṩ���̻�,���:��� 3 pc-id���������������ձ�
		//String pc_id = "";
		//Ψһ���,�����Ż�ϵͳ���ɵ�Ψһ���кţ�����Ϸ��������(��ֹ�ظ��ύ)ϵͳ�л����merchant-idgame-idserver-idseq-strΨһƥ������
		//String unique_str = "";
		//ʱ���ǩ����ʽ:yyyymmddHH24mmss����Чʱ�䣨�ݶ�Ϊ��Сʱ����ע��˫��������ʱ��ͳһ���£���Сʱ���֣���С��10ǰ�油��0������:200901011208
		String timestamp = new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date());
		//MD5��֤��,32λСдMD5��֤�������ɹ���μ��·�˵��
		String verify_string = "";
		//��������,Ĭ��Ϊ0�������ֶξ�������
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
		String unique_str = "123";//������
		String timestamp = new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date());
		String verify_string = "";
		String verify_type = "0";
		
		SendPostXml.sendPostToDangle(userName, amount, pc_id, unique_str);*/
		
		String url = "http://122.224.217.162:40001/authssid?ssid=6Gp4xwuk";
		String test = SendPostXml.send(url, "ssid:RVtQjuLq", "post");
		System.out.print("test:"+test);
	}
}
