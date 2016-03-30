package com.web.view.fetch;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class Fetcher {
	
	
	public String fetch(String url, Map<String, String> params){
		HttpClient httpClient=FetcherFactory.getHttpClient();
		HttpPost httpPost = new HttpPost(url);
		RequestConfig config=RequestConfig.custom().setConnectTimeout(5000).setSocketTimeout(8000).build();
		httpPost.setConfig(config);
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		if (params != null) {
			for (String key : params.keySet()) {
				nvps.add(new BasicNameValuePair(key, params.get(key)));
			}
			try {
				httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		
		HttpResponse response;
			try {
				 response= httpClient.execute(httpPost);
				int statusCode = response.getStatusLine().getStatusCode();
				if (statusCode == HttpStatus.SC_OK) {
					HttpEntity httpEntity = response.getEntity();
					String result= EntityUtils.toString(httpEntity);
					return result;
				}else{
					httpPost.abort();
				}
				
				
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		return null;
	}
	
	
	/*public static void main(String[] args) {
		Fetcher fetcher=new Fetcher();
		
		Map<String,String> map=new HashMap<String,String>();
		map.put("crawl_id", "23656928");
		map.put("categoryId", "20");
		
		String s=fetcher.fetch("http://10.0.2.152:8080/SearchPlatform/rest/risk/multiConditionSearch/searchByIds", map);
		System.out.println(s);
	}*/
}
