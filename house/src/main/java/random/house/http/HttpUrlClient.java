package random.house.http;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import random.house.parser.XmlParser;

public class HttpUrlClient {
   
	private static final String GET_URL = "https://reststop.randomhouse.com/resources/authors?";
	private static final String REQUEST_TYPE_GET = "GET";
	private static final String FIRST_NAME = "firstName";
	private static final String LAST_NAME = "lastName";
	private static final String ENCODING_UTF8 = "UTF-8";
	
	public String getResponse(String[] parameters) throws IOException, ParserConfigurationException, SAXException {
		
	    Map<String, String> params = new HashMap<String,String>();
	    params.put(FIRST_NAME, parameters[0]);
	    params.put(LAST_NAME, parameters[1]);
	    
	    StringBuilder requestData = new StringBuilder();

	    for (Map.Entry<String, String> param : params.entrySet()) {
	        if (requestData.length() != 0) {
	            requestData.append('&');
	        }
	        
	        requestData.append(URLEncoder.encode(param.getKey(), ENCODING_UTF8));
	        requestData.append('=');
	        requestData.append(URLEncoder.encode(String.valueOf(param.getValue()), ENCODING_UTF8));
	    }
	    
	    URL url = new URL(GET_URL + requestData.toString());
	    HttpURLConnection connection = (HttpURLConnection) url.openConnection();

	    connection.setRequestMethod(REQUEST_TYPE_GET);
	    
	    byte[] requestDataByes = requestData.toString().getBytes(ENCODING_UTF8);
	    connection.setDoOutput(true);
	    
	    StringBuilder content;
	    try{
	         	
	        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));	
	        String line;
	        content = new StringBuilder();
	           while ((line = in.readLine()) != null) {
	                content.append(line);
	                content.append(System.lineSeparator());
	            }  

	    } finally {
	        connection.disconnect();
	    }
	    
		return content.toString();
	}
}
