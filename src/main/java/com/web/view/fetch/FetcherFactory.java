package com.web.view.fetch;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

public class FetcherFactory {
	
	private static PoolingHttpClientConnectionManager poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager();
	
	static{
		poolingHttpClientConnectionManager.setMaxTotal(50);
	}
	
	public static HttpClient getHttpClient() {
		CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(poolingHttpClientConnectionManager)
				.build();
		
		return httpClient;
	}

}
