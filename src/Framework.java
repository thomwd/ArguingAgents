/**
 * Represents a complete argumentation framework, (a directed graph)
 * If we want to load a new framework, we create a new framework object and discard the existing one
 */


 	//IMPORT MATHS
public class Framework {
	private  String topicDescription;		// what the argumentation is about
	private  String topicSummary;			// short summary of description
	private ArrayList<Argument> positions;	// competing (final) positions in the argumentation, e.g. 'is guilty'
	private ArrayList<Argument> arguments;	// all arguments
	private ArrayList<Relation> relations;	// all relations between arguments and other arguments
											// and between arguments and relations

	// TODO
	// constructors/getters/setters
	// methods:
	// evaluate(mode, threshold)	evaluates the whole Argumentation (i.e. argument activity is calculated).
									// where mode specifies which proof standard we use etc, threshold specifies the activity level below which we will call an argument 'inactive'
		//find leaf nodes (arguments where both the argument and their relations are not the target of another relation)
				//if there are no more leaf nodes, return the activations of the positions?
		//if a leaf node has no relations, find the position ID of these nodes and add their activation to that position.
		//propagate leaf node activation and their relation weights to their targets and update those target weights or activation
		//remove current leaf nodes as possible leaf nodes (how?)
		//find new "leaf" nodes and repeat previous step (call evaluate again)

	public ArrayList<Argument> evaluate (String mode, double threshold, Arraylist<Argument> argumentList, ArrayList<Relation> relationlist){
		ArrayList<Argument> tempArg = argumentList;
		ArrayList<Relation> tempRel = relationList;
		ArrayList<Argument> solution = new Arraylist<Argument>();
		tempArg = argumentList;
		tempRel = relationList;
		for(int i = 0; i < tempArg.size(); i++){
			Argument argument = tempArg.get(i);
			if(isLeaf(argument,tempRel)==true){
				for(int j = 0; j < tempRel.size(); j++){
					if(tempRel.get(j).originId == argument.argId){
						Relation relation = tempRel.get(j);
						if(relation.getTargetArgId!=null){
							int id = relation.getTargetArgId;
							tempArg.get(id).activity = tempArg.get(id).activity + (argument.acivity * relation.weight)
							//solution[target.argId].activity = solution[target.argId].activity + (argument.acivity * relation.weight)
						}
						else if(relation.getTargetRelId!=null){
							//tempRel[relation.getTargetRelId]
							//if (tempRel[relation.getTargetRelId].weight < 0)
								//tempRel[relation.getTargetRelId].weight = tempRel[relation.getTargetRelId].weight - (argument.acivity * relation.weight)
							//else
								//tempRel[relation.getTargetRelId].weight = tempRel[relation.getTargetRelId].weight + (argument.acivity * relation.weight)
							}
						else{
							//current argument is position, mark it as such
						}
						tempRel.remove(j);
					}
				}
				tempArg.remove(i);
			}
		}
		//update the lists accordingly
		if(solution is nog niet de positions){
			solution = solve (mode, threshold, argumentList, relationList)
		}else{
				return solution;
		}
	}

	public boolean isLeaf(Argument argument, Arraylist<Relation> relations){
		for (int i = 0; i < relations.size(); i++) {
			if(relations.get(i).targetArgId = argument.argId){
				return false;
			}
		}
		return true;
	}
	// reset()						reset the arguments that we calculated so we can start from scratch/use a different mode for calculate
				//This might not be necessary if evaluate just returns the activation of the positions. Then you could just call it with a new mode
}
