import java.util.List;

import javax.print.attribute.standard.RequestingUserName;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.Iterator;
import org.dom4j.XPath;
import org.dom4j.DocumentHelper;



public class ProcessXML {
	
	public static Document parse(String url) throws DocumentException {
        SAXReader reader = new SAXReader();
        Document document = reader.read(url);
        return document;
    }
	
	
	public static String getSubject(Document document){
		String subject;
		Element root = document.getRootElement();
		Element head = root.element("head");
		subject = head.elementText("subject");
		return subject;
	}
	
	public static String[] getConclusion(Document document) {
		
		int i=0;
		XPath xpathSelector = DocumentHelper.createXPath("//conclusion[@id]");
	    List results = xpathSelector.selectNodes(document);
	    for ( Iterator iter = results.iterator(); iter.hasNext(); ) {
	    	Element element = (Element) iter.next();
            i++;
	    }
	    String[] conclusion = new String[i];
	    int j = 0;
	    for ( Iterator iter = results.iterator(); iter.hasNext(); ) {
	    	Element element = (Element) iter.next();
	    	conclusion[j] = element.getText();
            j++;
	    }
		return conclusion;
	}
	
			
	public static ArrayList<Argument> getArgument(Document document){
		ArrayList<Argument> argArray= new ArrayList<Argument>();   
	    XPath xpathSelectorForArgument = DocumentHelper.createXPath("//argument[@argId]");
	    List resultsForArgument = xpathSelectorForArgument.selectNodes(document);
	    for ( Iterator iter = resultsForArgument.iterator(); iter.hasNext(); ) {
	    	Element element = (Element) iter.next();
	    	String argId = element.attributeValue("argId");
	    	String origin = element.element("origin").getText();
	    	String text = element.element("text").getText();
	    	String textWt = element.element("text").attributeValue("weight");
	    	String hypothType = element.element("attribution").element("hypothType").getText();
	    	Argument argument = new Argument();
	    	argument.addArgumentElement(argId, origin, text, textWt, hypothType);
	    	argArray.add(argument);
	    }	    
		String argNumber = String.valueOf(argArray.size());
		return argArray;
	}
	
}
