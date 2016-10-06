import java.util.ArrayList;

public class Agent {
	private int agentId;
	private String name;
	private ArrayList<Argument> arguments;
	private double credibility;

	// Yannik: I think this can be deleted - if we should never want to change an agent (except his credibility value maybe)
	// public  void addSource(int agentId,String name, String[] arguments){
	// 	setName(name);
	// this.agentId = agentId;
	// 	this.credibility = 1.0;
	// }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getCredibility() {
		return credibility;
	}

	public void setCredibility(double credibility) {
		if (credibility > 1 || credibility < 0) {        // TODO: Yannik: I would let credibility be between 0 and 2 or 3 maybe, where 1 is the default value. Or just impose cred >= 0
			System.out.println("Credibility has to be between 0 and 1");
		} else {
			this.credibility = credibility;
		}
	}

	public ArrayList<Argument> getArguments() {
		return arguments;
	}

	public void addArgument(Argument argument) {
		this.arguments.add(argument);
	}
}