
public class Source {
	private  int sourceId;
	private  String name;
	private  ArrayList arguments;
	private double credibility;

	public  void addSource(int sourceId,String name, String[] arguments){
		setName(name)
    this.sourceId = sourceId;
		this.credibility = 1.0;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getCredibility(){
		return credibility;
	}

	public void setCredibility(double credibility){
		if(credibility>1||credibility<0){
			print("Credibility has to be between 0 and 1")
		}else{
			this.credibility = credibility;
		}
	}

	public ArrayList getArguments(){
		return arguments;
	}

	public void addArgument(int argumentId){
		this.arguments.add(argumentId);
	}

}
