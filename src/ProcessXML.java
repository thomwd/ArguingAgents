import java.util.List;
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
	
	public static ArrayList<Conclusion> getConclusion(Document document) {
		ArrayList<Conclusion> conArray= new ArrayList<Conclusion>();   
		XPath xpathSelectorForCon = DocumentHelper.createXPath("//conclusion[@id]");
	    List resultsForCon = xpathSelectorForCon.selectNodes(document);
	    for ( Iterator iter = resultsForCon.iterator(); iter.hasNext(); ) {
	    	Element element = (Element) iter.next();
	    	String type = element.attributeValue("hypothType");
	    	String conclusion = element.getText();
	    	Conclusion con = new Conclusion();
	    	con.addConclusionElement(type, conclusion);
	    	conArray.add(con);
	    }	
	    return conArray;
	}
	
	
	public static ArrayList<Argument> getArgument(Document document){
		int argIdIndicator = 1;
		ArrayList<Argument> argArray= new ArrayList<Argument>();
	    XPath xpathSelectorForArgument = DocumentHelper.createXPath("//argument[@argId]");
	    List resultsForArgument = xpathSelectorForArgument.selectNodes(document);
	    for ( Iterator iter = resultsForArgument.iterator(); iter.hasNext(); ) {
	    	Element element = (Element) iter.next();
	    	int argId = argIdIndicator;
	    	int agentId = Integer.parseInt(element.attributeValue("agentId"));
	    	String text = element.element("text").getText();
	    	String summary = element.element("summary").getText();
	    	Argument argument = new Argument(argId, agentId, text, summary);
	    	argArray.add(argument);    	
	    	argIdIndicator++;
	    }
		return argArray;
	}		
	
	
	public static ArrayList<Relation> getRelation(Document document){
		int relationIdIndicator = 1;
		int argIdIndicator = 1;
		ArrayList<Relation> relArray = new ArrayList<Relation>();
	    XPath xpathSelectorForArgument = DocumentHelper.createXPath("//argument[@argId]");
	    List resultsForArgument = xpathSelectorForArgument.selectNodes(document);
	    for ( Iterator iter = resultsForArgument.iterator(); iter.hasNext(); ) {    	
	    	
	    	XPath xpathSelectorTargetArg = DocumentHelper.createXPath("//argId[@weight]");
		    List resultsTargetArg = xpathSelectorTargetArg.selectNodes(document);
	    	for(Iterator iterTargetArg = resultsTargetArg.iterator(); iterTargetArg.hasNext(); ){
	    		Element elementTargetArg = (Element) iterTargetArg.next();
	    		int relationId = relationIdIndicator;
	    		int originId = argIdIndicator;
	    		int targetArgId = Integer.parseInt(elementTargetArg.getText());
	    		double weight = Double.parseDouble(elementTargetArg.attributeValue("weight"));
	    		Relation relation = new Relation(relationId, originId, targetArgId, 0, 0, weight);
	    		relArray.add(relation);
	    		relationIdIndicator++;
	    	}
	    	
	    	XPath xpathSelectorRelArg = DocumentHelper.createXPath("//pair[@id]");
		    List resultsRelArg = xpathSelectorRelArg.selectNodes(document);
	    	for(Iterator iterRelArg = resultsRelArg.iterator(); iterRelArg.hasNext(); ){
	    		Element elementRelArg = (Element) iterRelArg.next();
	    		int relationId = relationIdIndicator;
	    		int originId = argIdIndicator;
	    		int endArgId = Integer.parseInt(elementRelArg.element("endArgId").getText());
	    		int frontArgId = Integer.parseInt(elementRelArg.element("frontArgId").getText());
	    		double weight = Double.parseDouble(elementRelArg.attributeValue("weight"));
	    		Relation relation = new Relation(relationId, originId, 0, frontArgId, endArgId, weight);
	    		relArray.add(relation);
	    		relationIdIndicator++;
	    	}
	    	
	    	argIdIndicator++;
	    }
		return relArray;
	}	
	
	
	
	
	
	
	
	
	
	
	
//	public static ArrayList<Argument> getArgument(Document document){
//		ArrayList<Argument> argArray= new ArrayList<Argument>();   
//	    XPath xpathSelectorForArgument = DocumentHelper.createXPath("//argument[@argId]");
//	    List resultsForArgument = xpathSelectorForArgument.selectNodes(document);
//	    for ( Iterator iter = resultsForArgument.iterator(); iter.hasNext(); ) {
//	    	Element element = (Element) iter.next();
//	    	String argId = element.attributeValue("argId");
//	    	String origin = element.element("origin").getText();
//	    	String text = element.element("text").getText();
//	    	String textWt = element.element("text").attributeValue("weight");
//	    	String hypothType = element.element("attribution").element("hypothType").getText();
//	    	Argument argument = new Argument();
//	    	argument.addArgumentElement(argId, origin, text, textWt, hypothType);
//	    	argArray.add(argument);
//	    }	    
//		String argNumber = String.valueOf(argArray.size());
//		return argArray;
//	}
	
}
