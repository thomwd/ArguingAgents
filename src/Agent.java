
public class Agent {
	private  int agentId;
	private  String name;
	private  ArrayList arguments;
	private double credibility;

	public  void addSource(int agentId,String name, String[] arguments){
		setName(name);
    this.agentId = agentId;
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
			System.out.println("Credibility has to be between 0 and 1");
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
