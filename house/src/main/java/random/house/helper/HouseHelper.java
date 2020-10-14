package random.house.helper;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import random.house.http.HttpUrlClient;
import random.house.parser.XmlParser;

public class HouseHelper {
	
	public void getAuthorDetails(String[] param) {
		
		try {
			
			String xmlResponse = new HttpUrlClient().getResponse(param);
			new XmlParser().readXml(xmlResponse);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
