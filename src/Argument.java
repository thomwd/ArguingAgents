
public class Argument {
	private  String argId;
	private  String origin;
	private  String text;
	private  String textWt;
	private  String hypothType;
	private  int[] undercutArgId;
	private  int[] supportArgId;
	private  int[] rebuttalArgId;
	
	
	public  void addArgumentElement(String argId,String origin,String text,String textWt,String hypothType/*,int[] supportArgId,int[] rebuttalArgId, int[] undercutArgId*/){
		setArgId(argId);
		setHypothType(hypothType);
		setOrigin(origin);
		setText(text);
		setTextWt(textWt);
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
