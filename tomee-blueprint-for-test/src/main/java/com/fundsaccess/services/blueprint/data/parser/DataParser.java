package com.fundsaccess.services.blueprint.data.parser;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.fundsaccess.services.blueprint.data.ExchangeCurrencies;
import com.google.gson.Gson;

public class DataParser {
	
	private static Map<String,List<ExchangeCurrencies>> mapExchangeCurrencies = new HashMap<String,List<ExchangeCurrencies>>();
	
	
	private static DataParser singletonParser = null;
	
	public static DataParser getParser() {
		
		if (null == singletonParser) {
			singletonParser = new DataParser();
		}
		return singletonParser;
	}
	
	private DataParser() {
		this.readXml("BBEX3.D.CZK.EUR.BB.AC.000.xml");
		this.readXml("BBEX3.D.AUD.EUR.BB.AC.000.xml");
		this.readXml("BBEX3.D.BGN.EUR.BB.AC.000.xml");
	}
	
	private void readXml(String filename) {
		List<ExchangeCurrencies> exchangeCurrencies = new ArrayList<ExchangeCurrencies>();
		try {

			File fXmlFile = new File(getClass().getClassLoader().getResource(filename).getFile());

			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			//optional, but recommended
			//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
			doc.getDocumentElement().normalize();

			System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

			NodeList nList = doc.getElementsByTagName("bbk:DataSet");

			System.out.println("----------------------------");
			//	    	System.out.println(nList);

			for (int temp = 0; temp < nList.getLength(); temp++) {


				Node nNode = nList.item(temp);
				System.out.println("\nCurrent Element :" + nNode.getNodeName());
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					for (int i = 0; i < nNode.getChildNodes().getLength(); i++) {
						if(nNode.getChildNodes().item(i).getNodeType() == Node.ELEMENT_NODE) {
							System.out.println("Child: "+(nNode.getChildNodes().item(i).getNodeName()));	  

							Element eSeries = (Element)nNode.getChildNodes().item(i);
							String unit = eSeries.getAttribute("UNIT");
							NodeList obsList = eSeries.getElementsByTagName("bbk:Obs");


							for(int j = 0; j < obsList.getLength(); j++) {
								Node obsNode = obsList.item(j);
								if(obsNode.getNodeType() == Node.ELEMENT_NODE) {
									Element obsElement = (Element)obsNode;
									System.out.println("TIME_PERIOD = "+obsElement.getAttribute("TIME_PERIOD")+" \t "+"OBS_VALUE = "+obsElement.getAttribute("OBS_VALUE"));
									ExchangeCurrencies exchangeCurrency = new ExchangeCurrencies();
									exchangeCurrency.setTimePeriod(obsElement.getAttribute("TIME_PERIOD"));
									exchangeCurrency.setObsValue(obsElement.getAttribute("OBS_VALUE"));
									exchangeCurrency.setUnit(unit);
									exchangeCurrencies.add(exchangeCurrency);
								}

							}
							
							if(mapExchangeCurrencies.get(unit) != null) {
								exchangeCurrencies.addAll(mapExchangeCurrencies.get(unit));
							}
							mapExchangeCurrencies.put(unit, exchangeCurrencies);
						}

					}
					
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Gson gson = new Gson();
		System.out.println(gson.toJson(exchangeCurrencies));
		
	}
	
	public List<ExchangeCurrencies> getExchangeCurrencies() {
		List<ExchangeCurrencies> exchangeCurrencies = new ArrayList<ExchangeCurrencies>();
		for(String key:mapExchangeCurrencies.keySet()) {
			exchangeCurrencies.addAll(mapExchangeCurrencies.get(key));
		}
		return exchangeCurrencies;
	}
	
	public List<Object> getExchangeCurrenciesMap() {
		return Arrays.asList(mapExchangeCurrencies.keySet().toArray());
	}
	
	
	
	  
}
