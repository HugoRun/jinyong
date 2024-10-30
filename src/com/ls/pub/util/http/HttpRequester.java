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
     * 发送GET请求  
     *   
     * @param urlString  
     *            URL地址  
     * @return 响应对象  
     * @throws IOException  
     */  
    public HttpRespons sendGet(String urlString) throws IOException {   
        return this.send(urlString, "GET", null, null);   
    }   
    
    /**  
     * 发送GET请求  
     *   
     * @param urlString  
     *            URL地址  
     * @param params  
     *            参数集合  
     * @return 响应对象  
     * @throws IOException  
     */  
    public HttpRespons sendGet(String urlString, Map<String, String> params)   
            throws IOException {   
        return this.send(urlString, "GET", params, null);   
    }   
    
    /**  
     * 发送GET请求  
     *   
     * @param urlString  
     *            URL地址  
     * @param params  
     *            参数集合  
     * @param propertys  
     *            请求属性  
     * @return 响应对象  
     * @throws IOException  
     */  
    public HttpRespons sendGet(String urlString, Map<String, String> params,   
            Map<String, String> propertys) throws IOException {   
        return this.send(urlString, "GET", params, propertys);   
    }   
    
    /**  
     * 发送POST请求  
     *   
     * @param urlString  
     *            URL地址  
     * @return 响应对象  
     * @throws IOException  
     */  
    public HttpRespons sendPost(String urlString) throws IOException {   
        return this.send(urlString, "POST", null, null);   
    }   
    
    /**  
     * 发送POST请求  
     *   
     * @param urlString  
     *            URL地址  
     * @param params  
     *            参数集合  
     * @return 响应对象  
     * @throws IOException  
     */  
    public HttpRespons sendPost(String urlString, Map<String, String> params)   
            throws IOException {   
        return this.send(urlString, "POST", params, null);   
    }   
    /**  
     * 发送POST请求  电信专门频道  
     *   
     * @param urlString  
     *            URL地址  
     * @param params  
     *            参数集合  
     * @return 响应对象  
     * @throws IOException  
     */  
    public HttpRespons sendPostTele(String urlString, Map<String, String> params)   
            throws IOException {   
        return this.sendPost(urlString, "POST", params);   
    } 
    /**  
     * 发送HTTP请求  专门为电信写的
     *   
     * @param urlString  
     * @return 响映对象  
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
     * 发送POST请求  
     *   
     * @param urlString  
     *            URL地址  
     * @param params  
     *            参数集合  
     * @param propertys  
     *            请求属性  
     * @return 响应对象  
     * @throws IOException  
     */  
    public HttpRespons sendPost(String urlString, Map<String, String> params,   
            Map<String, String> propertys) throws IOException {   
        return this.send(urlString, "POST", params, propertys);   
    }   
    
    /**  
     * 发送HTTP请求  
     *   
     * @param urlString  
     * @return 响映对象  
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
     * 得到响应对象  
     *   
     * @param urlConnection  
     * @return 响应对象  
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
     * 默认的响应字符集  
     */  
    public String getDefaultContentEncoding() {   
        return this.defaultContentEncoding;   
    }   
    
    /**  
     * 设置默认的响应字符集  
     */  
    public void setDefaultContentEncoding(String defaultContentEncoding) {   
        this.defaultContentEncoding = defaultContentEncoding;   
    }  
	
	
	
	public static void main( String[] agrs )
	{
		  try {   
			  /**
			   * 跳网申请游戏请求
			   */
	            HttpRequester request = new HttpRequester(); 
	            request.setDefaultContentEncoding("utf-8");
	            
	           /* 
	            GameName=游戏名称(not null)
	            ImgUrl=游戏图像(null)
	            Summary=游戏简介(not null)
	            HomePage=游戏主页(无主页可填为登录页)(not null)
	            LoginUrl=游戏登录页(not null)
	            LogoutUrl=游戏注销页(null)
	            PayKey=游戏密匙(not null)(为防不同语言和平台默认中文编码格式不同，不要用中文.)
	            MoneyRate=游戏币汇率(<=0将转为1)如1圆钱能换100个游戏币，此处应填写100
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
	           System.out.println("返回游戏标识串:"+result);

	        } catch (Exception e) {   
	            e.printStackTrace();   
	        }
	}
}
