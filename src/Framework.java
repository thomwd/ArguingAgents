/**
 * Represents a complete argumentation framework
 */
public class Framework {
	private  String topicDescription;		// what the argumentation is about 
	private  String topicSummary;			// short summary of description
	private ArrayList<Argument> positions	// competing (final) positions in the argumentation, e.g. 'is guilty'
	private ArrayList<Argument> arguments;	// all arguments
	private ArrayList<Relation> relations;	// all relations between arguments and other arguments
											// and between arguments and relations
										
	// TODO
	// methods:
	// solve
}
