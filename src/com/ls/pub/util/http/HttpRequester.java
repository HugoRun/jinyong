package com.ls.pub.util.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.exolab.jms.net.http.HTTPRequestInfo;


public class HttpRequester {
	
	  
    private String defaultContentEncoding;   
    
    public HttpRequester() {   
        this.defaultContentEncoding = Charset.defaultCharset().name();
        
    }   
    
    /**  
     * ����GET����  
     *   
     * @param urlString  
     *            URL��ַ  
     * @return ��Ӧ����  
     * @throws IOException  
     */  
    public HttpRespons sendGet(String urlString) throws IOException {   
        return this.send(urlString, "GET", null, null);   
    }   
    
    /**  
     * ����GET����  
     *   
     * @param urlString  
     *            URL��ַ  
     * @param params  
     *            ��������  
     * @return ��Ӧ����  
     * @throws IOException  
     */  
    public HttpRespons sendGet(String urlString, Map<String, String> params)   
            throws IOException {   
        return this.send(urlString, "GET", params, null);   
    }   
    
    /**  
     * ����GET����  
     *   
     * @param urlString  
     *            URL��ַ  
     * @param params  
     *            ��������  
     * @param propertys  
     *            ��������  
     * @return ��Ӧ����  
     * @throws IOException  
     */  
    public HttpRespons sendGet(String urlString, Map<String, String> params,   
            Map<String, String> propertys) throws IOException {   
        return this.send(urlString, "GET", params, propertys);   
    }   
    
    /**  
     * ����POST����  
     *   
     * @param urlString  
     *            URL��ַ  
     * @return ��Ӧ����  
     * @throws IOException  
     */  
    public HttpRespons sendPost(String urlString) throws IOException {   
        return this.send(urlString, "POST", null, null);   
    }   
    
    /**  
     * ����POST����  
     *   
     * @param urlString  
     *            URL��ַ  
     * @param params  
     *            ��������  
     * @return ��Ӧ����  
     * @throws IOException  
     */  
    public HttpRespons sendPost(String urlString, Map<String, String> params)   
            throws IOException {   
        return this.send(urlString, "POST", params, null);   
    }   
    /**  
     * ����POST����  ����ר��Ƶ��  
     *   
     * @param urlString  
     *            URL��ַ  
     * @param params  
     *            ��������  
     * @return ��Ӧ����  
     * @throws IOException  
     */  
    public HttpRespons sendPostTele(String urlString, Map<String, String> params)   
            throws IOException {   
        return this.sendPost(urlString, "POST", params);   
    } 
    /**  
     * ����HTTP����  ר��Ϊ����д��
     *   
     * @param urlString  
     * @return ��ӳ����  
     * @throws IOException  
     */  
    private HttpRespons sendPost(String urlString, String method,   
            Map<String, String> parameters)   
            throws IOException {   
        HttpURLConnection urlConnection = null;   
        URL url = new URL(urlString);   
        urlConnection = (HttpURLConnection) url.openConnection();   
        
        urlConnection.setRequestMethod(method);   
        urlConnection.setDoOutput(true);   
        urlConnection.setDoInput(true);   
        urlConnection.setUseCaches(false);   
        if (method.equalsIgnoreCase("POST") && parameters != null) {   
            StringBuffer param = new StringBuffer(); 
            param.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?><root>");
            for (String key : parameters.keySet()) { 
            	param.append("<"+key+">");
                param.append(parameters.get(key));
                param.append("</"+key+">");
            } 
            param.append("</root>");
            System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"+param.toString());
            urlConnection.getOutputStream().write(param.toString().getBytes()); 
            urlConnection.getOutputStream().flush();   
            urlConnection.getOutputStream().close();   
        }   
    
        return this.makeContent(urlString, urlConnection);   
    }   
    /**  
     * ����POST����  
     *   
     * @param urlString  
     *            URL��ַ  
     * @param params  
     *            ��������  
     * @param propertys  
     *            ��������  
     * @return ��Ӧ����  
     * @throws IOException  
     */  
    public HttpRespons sendPost(String urlString, Map<String, String> params,   
            Map<String, String> propertys) throws IOException {   
        return this.send(urlString, "POST", params, propertys);   
    }   
    
    /**  
     * ����HTTP����  
     *   
     * @param urlString  
     * @return ��ӳ����  
     * @throws IOException  
     */  
    private HttpRespons send(String urlString, String method,   
            Map<String, String> parameters, Map<String, String> propertys)   
            throws IOException {   
        HttpURLConnection urlConnection = null;   
        
        if (method.equalsIgnoreCase("GET") && parameters != null) {   
            StringBuffer param = new StringBuffer();   
            int i = 0;   
            for (String key : parameters.keySet()) {   
                if (i == 0)   
                    param.append("?");   
                else  
                    param.append("&");   
                param.append(key).append("=").append(parameters.get(key));   
                i++;   
            }   
            urlString += param;   
        }   
        URL url = new URL(urlString);   
        urlConnection = (HttpURLConnection) url.openConnection();   
        
        urlConnection.setRequestMethod(method);   
        urlConnection.setDoOutput(true);   
        urlConnection.setDoInput(true);   
        urlConnection.setUseCaches(false);   
    
        if (propertys != null)   
            for (String key : propertys.keySet()) {   
                urlConnection.addRequestProperty(key, propertys.get(key));   
            }   
    
        if (method.equalsIgnoreCase("POST") && parameters != null) {   
            StringBuffer param = new StringBuffer();   
            for (String key : parameters.keySet()) {   
                param.append("&");   
                param.append(key).append("=").append(parameters.get(key));   
            }   
            urlConnection.getOutputStream().write(param.toString().getBytes());   
            urlConnection.getOutputStream().flush();   
            urlConnection.getOutputStream().close();   
        }   
    
        return this.makeContent(urlString, urlConnection);   
    }   
    
    /**  
     * �õ���Ӧ����  
     *   
     * @param urlConnection  
     * @return ��Ӧ����  
     * @throws IOException  
     */  
    private HttpRespons makeContent(String urlString,   
            HttpURLConnection urlConnection) throws IOException {   
        HttpRespons httpResponser = new HttpRespons();   
        try {   
            InputStream in = urlConnection.getInputStream();   
            BufferedReader bufferedReader = new BufferedReader(   
                    new InputStreamReader(in));   
            httpResponser.contentCollection = new Vector<String>();   
            StringBuffer temp = new StringBuffer();   
            String line = bufferedReader.readLine();   
            while (line != null) {   
                httpResponser.contentCollection.add(line);   
                temp.append(line).append("\r\n");   
                line = bufferedReader.readLine();   
            }   
            bufferedReader.close();   
    
            String ecod = urlConnection.getContentEncoding();   
            if (ecod == null)   
                ecod = this.defaultContentEncoding;   
    
            httpResponser.urlString = urlString;   
    
            httpResponser.defaultPort = urlConnection.getURL().getDefaultPort();   
            httpResponser.file = urlConnection.getURL().getFile();   
            httpResponser.host = urlConnection.getURL().getHost();   
            httpResponser.path = urlConnection.getURL().getPath();   
            httpResponser.port = urlConnection.getURL().getPort();   
            httpResponser.protocol = urlConnection.getURL().getProtocol();   
            httpResponser.query = urlConnection.getURL().getQuery();   
            httpResponser.ref = urlConnection.getURL().getRef();   
            httpResponser.userInfo = urlConnection.getURL().getUserInfo();   
    
            httpResponser.content = new String(temp.toString().getBytes(), ecod);   
            
            httpResponser.contentEncoding = ecod;   
            httpResponser.code = urlConnection.getResponseCode();   
            httpResponser.message = urlConnection.getResponseMessage();   
            httpResponser.contentType = urlConnection.getContentType();   
            httpResponser.method = urlConnection.getRequestMethod();   
            httpResponser.connectTimeout = urlConnection.getConnectTimeout();   
            httpResponser.readTimeout = urlConnection.getReadTimeout();   
    
            return httpResponser;   
        } catch (IOException e) {   
            throw e;   
        } finally {   
            if (urlConnection != null)   
                urlConnection.disconnect();   
        }   
    }   
    
    /**  
     * Ĭ�ϵ���Ӧ�ַ���  
     */  
    public String getDefaultContentEncoding() {   
        return this.defaultContentEncoding;   
    }   
    
    /**  
     * ����Ĭ�ϵ���Ӧ�ַ���  
     */  
    public void setDefaultContentEncoding(String defaultContentEncoding) {   
        this.defaultContentEncoding = defaultContentEncoding;   
    }  
	
	
	
	public static void main( String[] agrs )
	{
		  try {   
			  /**
			   * ����������Ϸ����
			   */
	            HttpRequester request = new HttpRequester(); 
	            request.setDefaultContentEncoding("utf-8");
	            
	           /* 
	            GameName=��Ϸ����(not null)
	            ImgUrl=��Ϸͼ��(null)
	            Summary=��Ϸ���(not null)
	            HomePage=��Ϸ��ҳ(����ҳ����Ϊ��¼ҳ)(not null)
	            LoginUrl=��Ϸ��¼ҳ(not null)
	            LogoutUrl=��Ϸע��ҳ(null)
	            PayKey=��Ϸ�ܳ�(not null)(Ϊ����ͬ���Ժ�ƽ̨Ĭ�����ı����ʽ��ͬ����Ҫ������.)
	            MoneyRate=��Ϸ�һ���(<=0��תΪ1)��1ԲǮ�ܻ�100����Ϸ�ң��˴�Ӧ��д100
	            */
	            Map<String,String> params = new HashMap<String,String>();
	            params.put("GameName", "jinyong");
	            //params.put("ImgUrl", "");
	            params.put("Summary", "");
	            params.put("HomePage", "");
	            params.put("LoginUrl", "http://localhost:8080");
	            //params.put("LogoutUrl", "");
	            params.put("PayKey", "oneshow123");
	            params.put("MoneyRate", "100");
	            
	            HttpRespons hr = request.sendPost("http://192.168.1.206:8080/backdoor.jsp", params);   
	               
	            System.out.println(hr.getContent());
	    
	            String result = hr.getContent();
	           System.out.println("������Ϸ��ʶ��:"+result);

	        } catch (Exception e) {   
	            e.printStackTrace();   
	        }
	}
}
