
public class Argument {
	private  String argId;		// change to int
	private  String origin;		// TODO: rename to 'agent' (source would also be fine)
	private  String text;
	private  String textWt;		//TODO rename to 'summary'
	private  String hypothType; //TODO can be removed (we don't assign arguments to positions, arguments can favor one or more positions in the end)
	//private  int issueId;
	private  boolean activity;	//TODO change to double
	private  double credibility; //TODO delete this
	
	//TODO replace these three with a single collection (ArrayList) of relation objects, called 'relations'
	// private ArrayList<relation> relations = new ArrayList<>();
	private  int[] undercutArgId;
	private  int[] supportArgId; 
	private  int[] rebuttalArgId;


	public  void addArgumentElement(String argId,String origin,String text,String textWt,String hypothType){
		this.argId = argId;
		setHypothType(hypothType);
		setOrigin(origin);
		setText(text);
		setTextWt(textWt);
		//setCredibility(credibility);
		this.activity = true;
//  setIssueId(issueId);
//		setSupportArgId(supportArgId);
//		setRebuttalArgId(rebuttalArgId);
//		setUndercutArgId(undercutArgId);
	}

	/*public int getIssueId() {
		return issueId;
	}

	public void setIssueId(int issueId) {
		this.issueId = issueId;
	}

  public boolean getActivity(){
		return activity
  }

	public void setActivity(boolean activity){
		this.activity = activity;
	}
	*/

	public String getArgId() {
		return argId;
	}


	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}




	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}




	public String getTextWt() {
		return textWt;
	}




	public void setTextWt(String textWt2) {
		this.textWt = textWt2;
	}




	public String getHypothType() {
		return hypothType;
	}




	public void setHypothType(String hypothType) {
		this.hypothType = hypothType;
	}

	public double getCredibility() {
		return credibility;
	}

	public void setCredibility(double credibility) {
		this.credibility = credibility;
	}


	public int[] getUndercutArgId() {
		return undercutArgId;
	}

	public void setUndercutArgId(int[] undercutArgId) {
		this.undercutArgId = undercutArgId;
	}


	public int[] getSupportArgId() {
		return supportArgId;
	}

	public void setSupportArgId(int[] supportArgId) {
		this.supportArgId = supportArgId;
	}




	public int[] getRebuttalArgId() {
		return rebuttalArgId;
	}

	public void setRebuttalArgId(int[] rebuttalArgId) {
		this.rebuttalArgId = rebuttalArgId;
	}

}
