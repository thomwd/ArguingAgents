/**
 * Represents a complete argumentation framework, (a directed graph)
 * If we want to load a new framework, we create a new framework object and discard the existing one
 */
public class Framework {
	private  String topicDescription;		// what the argumentation is about
	private  String topicSummary;			// short summary of description
	private ArrayList<Argument> positions	// competing (final) positions in the argumentation, e.g. 'is guilty'
	private ArrayList<Argument> arguments;	// all arguments
	private ArrayList<Relation> relations;	// all relations between arguments and other arguments
											// and between arguments and relations

	// TODO
	// constructors/getters/setters
	// methods:
	// evaluate(mode, threshold)	evaluates the whole Argumentation (i.e. argument activity is calculated).
									// where mode specifies which proof standard we use etc, threshold specifies the activity level below which we will call an argument 'inactive'
									//
	public void evaluate (String mode, double threshold){
		//find leaf nodes (arguments where both the argument and their relations are not the target of another relation)
				//if there are no more leaf nodes, return the activations of the positions?
		//if a leaf node has no relations, find the position ID of these nodes and add their activation to that position.
		//propagate leaf node activation and their relation weights to their targets and update those target weights or activation
		//remove current leaf nodes as possible leaf nodes (how?)
		//find new "leaf" nodes and repeat previous step (call evaluate again)
	}
	// reset()						reset the arguments that we calculated so we can start from scratch/use a different mode for calculate
				//This might not be necessary if evaluate just returns the activation of the positions. Then you could just call it with a new mode
}
