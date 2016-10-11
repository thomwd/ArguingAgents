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
		Element framework = root.element("framework");
		subject = framework.elementText("subject");
		return subject;
	}
	
	public static String getSummarySubject(Document document){
		String subject;
		Element root = document.getRootElement();
		Element framework = root.element("framework");
		subject = framework.elementText("summary");
		return subject;
	}
	

	
	public static ArrayList<Argument> getArgument(Document document){
		int argIdIndicator = 1;
		ArrayList<Argument> argArray= new ArrayList<Argument>();
	    XPath xpathSelectorForArgument = DocumentHelper.createXPath("//argument[@argId]");
	    List resultsForArgument = xpathSelectorForArgument.selectNodes(document);
	    for ( Iterator iter = resultsForArgument.iterator(); iter.hasNext(); ) {
	    	Element element = (Element) iter.next();
	    	int argId = Integer.parseInt(element.attributeValue("argId"));
	    	int agentId = Integer.parseInt(element.element("agentId").getText());
	    	double activition = Double.parseDouble(element.element("activation").getText());
	    	String text = element.element("text").getText();
	    	String summary = element.element("summary").getText();
	    	Argument argument = new Argument(argId, agentId, text, summary,activition);
	    	System.out.println("argId:"+argId+",agentId:"+agentId+",text:"+text+",summary:"+summary+",activation:"+activition);
	    	argArray.add(argument);    	
	    }
		return argArray;
	}		
	
	
	public static ArrayList<Relation> getRelation(Document document){
		ArrayList<Relation> relArray = new ArrayList<Relation>();
	    XPath xpathSelectorForArgument = DocumentHelper.createXPath("//argument[@argId]");
	    List resultsForArgument = xpathSelectorForArgument.selectNodes(document);
	    for ( Iterator iter = resultsForArgument.iterator(); iter.hasNext(); ) {    	
	    	Element element = (Element) iter.next();
	    	int argId = Integer.parseInt(element.attributeValue("argId"));
	    	XPath xpathSelectorTargetArg = DocumentHelper.createXPath("//argument[@argId = " +element.attributeValue("argId")+ "]//targetArgId[@weight]");
		    List resultsTargetArg = xpathSelectorTargetArg.selectNodes(document);
	    	for(Iterator iterTargetArg = resultsTargetArg.iterator(); iterTargetArg.hasNext(); ){
	    		Element elementTargetArg = (Element) iterTargetArg.next();
	    		int relId = Integer.parseInt(elementTargetArg.attributeValue("relId"));
	    		int originId = argId;
	    		int targetArgId = Integer.parseInt(elementTargetArg.getText().trim());
	    		double weight = Double.parseDouble(elementTargetArg.attributeValue("weight"));
	    		Relation relation = new Relation(relId, originId, targetArgId, 0, weight);
	    		System.out.println("redId:"+relId+",originId:"+originId+",targetArgId:"+targetArgId+",weight:"+weight);
	    		relArray.add(relation);
	    	}
	    	
	    	XPath xpathSelectorRelArg = DocumentHelper.createXPath("//argument[@argId= "+element.attributeValue("argId")+"]//targetRelId[@weight]");
		    List resultsRelArg = xpathSelectorRelArg.selectNodes(document);
	    	for(Iterator iterRelArg = resultsRelArg.iterator(); iterRelArg.hasNext(); ){
	    		Element elementRelArg = (Element) iterRelArg.next();
	    		int relId = Integer.parseInt(elementRelArg.attributeValue("relId"));
	    		int originId = argId;
	    		int targetRelId = Integer.parseInt(elementRelArg.getText().trim());
	    		double weight = Double.parseDouble(elementRelArg.attributeValue("weight"));
	    		Relation relation = new Relation(relId, originId, 0, targetRelId, weight);
	    		System.out.println("relId:"+relId+",originId:"+originId+",targetRelId:"+targetRelId+",weight:"+weight);
	    		relArray.add(relation);
	    	}	    	
	    }
		return relArray;
	}	
	
}
