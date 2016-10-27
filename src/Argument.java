public class Argument {
	private  int argId;	
	private  int agentId;	
	private  String text;// the full text of the argument
	private  String summary;// the summary of the full text
	private  double activity;	
	
	
	public Argument(int argId, int agentId, String text, String summary, double activity) {
		this.argId = argId;
		this.agentId = agentId;
		this.text = text;
		this.summary = summary;
		this.activity = activity;
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
		
}
