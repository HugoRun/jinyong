package com.ls.web.service.cooperate.air;

import java.io.PrintWriter;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import com.ls.pub.util.encrypt.MD5Util;

public class SendPostXml
{
	/***
	 * �����������������õ����ص�XML�ļ�
	 * @param urlString
	 * @param params
	 * @param method
	 * @return
	 */
	public static String getReponseFromAir(String urlString, String params,
			String method)
	{
		String rtn = "";
		boolean isGet = (method == null) || method.trim().equals("")
				|| method.trim().equalsIgnoreCase("get");

		PrintWriter xmlOut = null;
		URL urlObj = null;
		HttpURLConnection acon = null;
		try
		{
			urlObj = new URL(urlString);
			acon = (HttpURLConnection) urlObj.openConnection();
			acon.setAllowUserInteraction(false);

			if (isGet)
			{
				acon.setRequestMethod("GET");
			}
			else
			{
				acon.setRequestMethod("POST");
				acon.setRequestProperty("kauth_signature",
						"application/x-www-form-urlencoded");
			}

			acon.setDoOutput(true);
			acon.setDoInput(true);
			acon.setUseCaches(false);

			if (!isGet)
			{
				// д��xml����
				xmlOut = new java.io.PrintWriter(acon.getOutputStream());
				xmlOut.write(params);
				xmlOut.flush();
			}
			long kauth_timestamp = System.currentTimeMillis();

			if (acon.getResponseCode() >= 400)
			{
				rtn = " <?xml version=\"1.0\" encoding=\"UTF-8\"?> <servlet-exception>";
				rtn += "HTTP response: " + acon.getResponseCode() + "\n"
						+ URLDecoder.decode(acon.getResponseMessage(), "UTF-8");
				rtn += " </servlet-exception>";
			}
			else
			{
				// �������
				StringBuffer sb = new StringBuffer();
				int c;
				InputStream in = acon.getInputStream();

				while ((c = in.read()) != -1)
					sb.append((char) c);

				in.close();
				rtn = sb.toString();
			}

			rtn = new String(rtn.getBytes("ISO8859-1"), "UTF-8");

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return rtn;
	}	

	// ����xml��ʽ�ļ�
	public static String send(String urlString, String params, String method)
	{

		String rtn = "";
		boolean isGet = (method == null) || method.trim().equals("")
				|| method.trim().equalsIgnoreCase("get");

		PrintWriter xmlOut = null;
		URL urlObj = null;
		HttpURLConnection acon = null;
		try
		{
			urlObj = new URL(urlString);
			acon = (HttpURLConnection) urlObj.openConnection();
			acon.setDoOutput(true);
			if (isGet)
			{
				acon.setRequestMethod("GET");
			}
			else
			{
				acon.setRequestMethod("POST");
				acon.setRequestProperty("kauth_signature",
						"application/x-www-form-urlencoded");
			}
			acon.getOutputStream().write(params.getBytes());
			acon.getOutputStream().flush();
			acon.getOutputStream().close();
			int code = acon.getResponseCode();

			if (acon.getResponseCode() == 200)
			{
				// �������
				StringBuffer sb = new StringBuffer();
				InputStream in = acon.getInputStream();
				int c;
				while ((c = in.read()) != -1)
					sb.append((char) c);

				in.close();
				rtn = sb.toString();
				rtn = new String(rtn.getBytes("ISO8859-1"), "UTF-8");
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (acon != null)
			{
				acon.disconnect();
			}
		}
		return rtn;
	}

	/**
	 * �����������ͳ�ֵȷ��
	 * 
	 * @param order_id ������            
	 * @param amount ��������
	 * @param pay_type �������� 1 - K��
	 * @param desc �û������������Ʒ������           
	 * @param v API�İ汾�ţ������ó� 1.0            
	 * @return Ӧ����֧����ȫ��Token
	 */
	public static String sendPostToAir(String userName, String amount,
			String pc_id, String unique_str)
	{
		String url = "http://kong.net/apps/service/pay/regorder";
		String methed = "POST";

		String merchant_key = "xWx86231";

		// �̻����,�̻��ڵ��ֵ���ݱ�ʶ���ɵ��ָ�֪�̻�
		String merchant_id = "17";
		// ��Ϸ���
		String game_id = "1";
		// �û����ڵ���Ϸ������ID��Ĭ��Ϊ1
		String server_id = "1";
		// �û��ڵ���ע����û���
		// String userName = "";
		// �û�֧����ʵ�ʽ���λΪԪ
		// String amount = "";
		// �û�֧��ʹ�õ�ͨ��ID��֧��ͨ�����ձ��ɵ����ṩ���̻�,���:��� 3 pc-id���������������ձ�
		// String pc_id = "";
		// Ψһ���,�����Ż�ϵͳ���ɵ�Ψһ���кţ�����Ϸ��������(��ֹ�ظ��ύ)ϵͳ�л����merchant-idgame-idserver-idseq-strΨһƥ������
		// String unique_str = "";
		// ʱ���ǩ����ʽ:yyyymmddHH24mmss����Чʱ�䣨�ݶ�Ϊ��Сʱ����ע��˫��������ʱ��ͳһ���£���Сʱ���֣���С��10ǰ�油��0������:200901011208
		String timestamp = new java.text.SimpleDateFormat("yyyyMMddHHmmss")
				.format(new java.util.Date());
		// MD5��֤��,32λСдMD5��֤�������ɹ���μ��·�˵��
		String verify_string = "";
		// ��������,Ĭ��Ϊ0�������ֶξ�������
		String verify_type = "0";

		String callback = "merchant-id=" + merchant_id + "&game-id=" + game_id
				+ "&server-id=" + server_id + "&username=" + userName
				+ "&amount=" + amount + "&pc-id=" + pc_id + "&merchant-key="
				+ merchant_key + "&unique-str=" + unique_str + "&timestamp="
				+ timestamp + "&encrypt-type=" + verify_type;

		String newHmac = MD5Util.md5Hex(callback);
		verify_string = newHmac;

		StringBuffer para = new StringBuffer();
		
		para.append("<?xml version='1.0' encoding='UTF-8'?>");
		para.append("<request>");
		para.append("<merchant-id>" + merchant_id + "</merchant-id>");
		para.append("<game-id>" + game_id + "</game-id>");
		para.append("<server-id>" + server_id + "</server-id>");
		para.append("<username>" + userName + "</username>");
		para.append("<amount>" + amount + "</amount>");
		para.append("<pc-id>" + pc_id + "</pc-id>");
		para.append("<unique-str>" + unique_str + "</unique-str>");
		para.append("<timestamp>" + timestamp + "</timestamp>");
		para.append("<verify-string>" + verify_string + "</verify-string>");
		para.append("<encrypt-type>" + verify_type + "</encrypt-type>");
		para.append("</request>");

		return SendPostXml.send(url, para.toString(), methed);
	}

	
	public static void main(String[] agrs)
	{
//		 String urlString = "http://kong.net/apps/service/users/loggedinuser";
//		 String test = SendPostXml.send(urlString, "ssid:RVtQjuLq", "post");
//		 System.out.print("test:" + test);
	}
}
