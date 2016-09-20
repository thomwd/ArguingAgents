
public class Conclusion {
	String type;
	String conclusionText;
	
	
	
	public void addConclusionElement(String type,String conclusionText) {
		this.type = type;
		this.conclusionText = conclusionText;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getConclusionText() {
		return conclusionText;
	}
	public void setConclusionText(String conclusionText) {
		this.conclusionText = conclusionText;
	}
	

}
