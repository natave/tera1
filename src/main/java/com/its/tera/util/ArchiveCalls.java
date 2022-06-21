package com.its.tera.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.extensions.webscripts.WebScriptException;

public class ArchiveCalls {
	
	
	private static Log logger = LogFactory.getLog(ArchiveCalls.class);
	
	public static String callServiceGet(String url, AppParameters app, String auth) throws WebScriptException, ClientProtocolException, IOException {
		
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		
		HttpGet request = new HttpGet(app.getHostPortInfoArchive() + url);
		if(auth != null) {
			request.addHeader("Authorization", auth);
		}
		
		logger.debug("URI = " + request.getURI());
		//RequestConfig config = new RequestConfig;
		//request.setConfig(config );
		//HttpUriRequest request = new ;
		CloseableHttpResponse response = httpClient.execute(request );
		
		InputStream responseInputStream = response.getEntity().getContent();
		String result = IOUtils.toString(responseInputStream, StandardCharsets.UTF_8);
		
		
		return result;
	}
	
	public static String callServicePost(String url, String body, AppParameters app, String auth) throws ClientProtocolException, IOException {
		
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		
		HttpPost request = new HttpPost(app.getHostPortInfoArchive() + url);
		request.addHeader("Content-Type", "application/json; charset=utf-8");
		if(auth != null) {
			request.addHeader("Authorization", auth);
		}
		/*SerializableEntity input = new SerializableEntity(coeff);
		input.setContentEncoding("UTF-8" );
		input.setContentType("application/json");
		post.setEntity(input );*/
		request.setEntity(new StringEntity(body, "UTF-8"));
		logger.debug("URI = " + request.getURI());
		
//		
//		
//		
		
		CloseableHttpResponse response = httpClient.execute(request );
		
		
		
		InputStream responseInputStream = response.getEntity().getContent();
		String result = IOUtils.toString(responseInputStream, StandardCharsets.UTF_8);
		
		
		return result;
	}
	
	public static String callServicePut(String url, String body, AppParameters app, String auth) throws ClientProtocolException, IOException {
		
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		
		HttpPut request = new HttpPut(app.getHostPortInfoArchive() + url);
		request.addHeader("Content-Type", "application/json; charset=utf-8");
		if(auth != null) {
			request.addHeader("Authorization", auth);
		}
		/*SerializableEntity input = new SerializableEntity(coeff);
		input.setContentEncoding("UTF-8" );
		input.setContentType("application/json");
		post.setEntity(input );*/
		request.setEntity(new StringEntity(body, "UTF-8"));
		
		logger.debug("URI = " + request.getURI());
		
//		ConnectionConfig cc = new ConnectionConfig();
//		RequestConfig config = new RequestConfig();
//		request.setConfig(config );
		//HttpUriRequest request = new ;
		CloseableHttpResponse response = httpClient.execute(request );
		
		
		
		InputStream responseInputStream = response.getEntity().getContent();
		String result = IOUtils.toString(responseInputStream, StandardCharsets.UTF_8);
		
		
		return result;
	}
	
	
	public static String callGet(String processId, AppParameters app) throws ClientProtocolException, IOException{
		
		//HttpResponse<String> res = 
		
		//RequestBuilder rb = new
		/*HttpUriRequest b = RequestBuilder.get().setUri("uri").addHeader("name", "value").addParameter("name", "value").build();
		Client
		Request req = .;*/
		
		
		HttpClient client = new DefaultHttpClient();
		//org.apache.commons.httpclient.HttpClient client = new org.apache.commons.httpclient.HttpClient();
		HttpGet request = new HttpGet(app.getHostPortInfoArchive() + "app.getLoanCoeffGetPath()" + "?processId=" + processId);
		request.addHeader("name", "value");
		
		//HttpMethod method = 
		HttpResponse response = client.execute(request);
		//HttpResponse response = client.executeMethod(method );
		BufferedReader rd = new BufferedReader (new InputStreamReader(response.getEntity().getContent()));
		String line = "";
		String resp = "";
		while ((line = rd.readLine()) != null) {
			logger.debug("callGet = " + line);
			resp = resp + line;
		}
		
		return resp;
	}
	
	
	
	
	public static void callPost(AppParameters app, String coeff) throws ClientProtocolException, IOException, JSONException{
		
		HttpClient httpClient = new DefaultHttpClient();//HttpClientBuilder.create().build();
		HttpPost post = new HttpPost(app.getHostPortInfoArchive() + "app.getLoanCoeffPostPath()");
		
		//StringEntity input = new StringEntity(coeff);//charset utf-8  header('content-type: text/html; charset=utf-8');
		
		logger.debug(" -- coeff = " + coeff);
		
		post.addHeader("Content-Type", "application/json; charset=utf-8");
		/*SerializableEntity input = new SerializableEntity(coeff);
		input.setContentEncoding("UTF-8" );
		input.setContentType("application/json");
		post.setEntity(input );*/
		post.setEntity(new StringEntity(coeff, "UTF-8"));
		/*List<? extends NameValuePair> input  = new ArrayList<NameValuePair>();
		new URLEncoder().
		post.setEntity(new UrlEncodedFormEntity(input,"UTF-8"));*/
		//logger.debug(" // 			 input.getContent() = " +  input.getContent().toString());
		logger.debug(" // post.getEntity().getContent() = " +  post.getEntity().getContent().toString());
		
		HttpResponse response = httpClient.execute(post);
		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		
		logger.debug("response");
		String line = "";
		String resp = "";
		while ((line = rd.readLine()) != null) {
			logger.debug(line);
			resp = resp + line;
		}
		JSONObject jsonObj = new JSONObject(resp);
		logger.debug("response jsonObj = " + jsonObj);
		logger.debug("response END");
		//return jsonObj;
	}
	
	
	
	
	

}
