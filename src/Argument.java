public class Argument {
	private  int argId;	
	private  int agentId;	
	private  String text;
	private  String summary;
	private  double activity;	
	//private  ArrayList<Relation> relations;
	
	
	public Argument(int argId, int agentId, String text, String summary) {
		this.argId = argId;
		this.agentId = agentId;
		this.text = text;
		this.summary = summary;
	}
	
	
	
	public int getArgId() {
		return argId;
	}
	
	public void setArgId(int argId) {
		this.argId = argId;
	}
	public int getAgentId() {
		return agentId;
	}
	public void setAgentId(int agentId) {
		this.agentId = agentId;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public double getActivity() {
		return activity;
	}
	public void setActivity(double activity) {
		this.activity = activity;
	}
	
	
	// TODO: generate constructors/getters&setters
}
