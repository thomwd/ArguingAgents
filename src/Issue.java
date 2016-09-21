
public class Issue {
	private  int issueId;
	private  String text;
	private  String textWt;


	public  void addIssue(String argId,String origin,String text,String textWt,String hypothType/*,int[] supportArgId,int[] rebuttalArgId, int[] undercutArgId*/, double credibility){
		setArgId(argId);
		setHypothType(hypothType);
		setOrigin(origin);
		setText(text);
		setTextWt(textWt);
		setCredibility(credibility);
//		setSupportArgId(supportArgId);
//		setRebuttalArgId(rebuttalArgId);
//		setUndercutArgId(undercutArgId);
	}

	public String getArgId() {
		return argId;
	}




	public void setArgId(String argId) {
		this.argId = argId;
	}



}
