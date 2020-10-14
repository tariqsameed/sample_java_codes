package random.house.parser;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XmlParser {
	
	private static final String URI = "uri";
	private static final String AUTHOR = "author";
	private static final String AUTHOR_LAST_FIRST = "authorlastfirst";
	private static final String TITLES = "titles";
			
	
	public void readXml(String xml) throws ParserConfigurationException, SAXException, IOException {
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		
		Document doc = builder.parse(new InputSource(new StringReader(xml)));
		
		doc.getDocumentElement().normalize();

		NodeList nList = doc.getElementsByTagName(AUTHOR);
		
		String id = null;
		String lastFirstName = null;
		String titleCounts = null;

		for (int temp = 0; temp < nList.getLength(); temp++) {
			Node nNode = nList.item(temp);
			
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				String[] segments = eElement.getAttribute(URI).split("/");
				id = segments[segments.length-1];
				
				lastFirstName = eElement.getElementsByTagName(AUTHOR_LAST_FIRST).item(0).getTextContent();
				
				NodeList titles = eElement.getElementsByTagName(TITLES);
				
				for(int j = 0; j < titles.getLength(); j++) {
					Node titNode = titles.item(j);
					titleCounts = Integer.toString(titNode.getChildNodes().getLength());
				}
							
			}
			
			System.out.println(id+";"+lastFirstName+";"+titleCounts);
			
			id = null;
			lastFirstName = null;
			titleCounts = null;
		}
	}
}
