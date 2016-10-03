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


	//Function to calculate the activity of arguments, based on their mode
	public double solveArgument (String mode, ArrayList<Argument> argumentList, Relation relation){
		int targetId = relation.getTargetArgId();
		int originId = relation.getOriginId();
		Argument origin = argumentList.get(originId);
		Argument target = argumentList.get(targetId);
		if(mode == "POE"){ //Preponderance of Evidence
			double activity = target.getActivity() + (origin.getAcivity() * relation.getWeight());
			return activity;
		}else if(mode == "BRD"){ //Beyond Reasonable Doubt
			if((origin.getAcivity() * relation.getWeight())=<0){ //If the argument attacks the target argument, there is doubt, so set the activity of the target argument to 0
				return 0;
			}else{
				return target.getActivity();
			}
		}else if(mode == "SOE"){ //Scintilla of Evidence
			if((origin.getAcivity() * relation.getWeight())>0){ //If the argument supports the target argument, there is a scintilla of evidence, so set the activity of the target argument to 1
				return 1;
			}else{
				return target.getActivity();
			}
		}else{ //Error because it is not one of the predefined modes
			System.out.println("Error: mode is not recognized.");
			return null;
		}
	}

	public double solveRelation(String mode, ArrayList<Relation> relationList, ArrayList<Argument> argumentList, Relation relation){
		int targetId = relation.getTargetRelId();
		int originId = relation.originId();
		Argument origin = argumentList.get(originId);
		Relation target = relationList.get(targetId);
		//TODO: how do the modes work with the relations?
		if(target.getWeight()<0){
			double weight = target.getWeight() - (origin.getAcivity() * relation.getWeight());
		}else{
			double weight = target.getWeight() + (origin.getAcivity() * relation.getWeight());
		}
		return weight;
	}

	public ArrayList<Argument> evaluate (String mode, double threshold, Arraylist<Argument> argumentList, ArrayList<Relation> relationlist, ArrayList<Argument> solution){
		ArrayList<Argument> tempArg = argumentList;
		ArrayList<Relation> tempRel = relationList;
		boolean solved = true; //Flag to see if the framework is solved. Set to false if there are still arguments to be analyzed
		for(int i = 0; i < tempArg.size(); i++){ //Iterate over all arguments
			Argument argument = tempArg.get(i);
			if(isLeaf(argument,tempRel)==true){ //Check if the argument is an argument from the current layer
				//TODO: work with a threshold
				for(int j = 0; j < tempRel.size(); j++){ //Iterate over all the relations
					Relation relation = tempRel.get(j)
					if(relation.getOriginId() == argument.getArgId()){ //Check if the origin of the relation is the current argument
						if(relation.getTargetArgId()!=null){ //Check if the target of the relation is an argument
							solved = false; //There are still arguments to analyze
							double activity = solveArgument(mode, tempArg, relation); //Calculate the new activity for the argument
							int targetId = relation.getTargetArgId();
							tempArg.get(targetId).setActivity(activity); //Add it to the argument list
							solution.get(id).setActivity(activity);
							//If we want to do something with threshold it can be done here.
						}
						else if(relation.getTargetRelId()!=null){ //Check if the target of the relation is a relation
							solved = false; //There are still arguments to analyze
							double weight = solveRelation(mode, tempRel, relation); //Calculate the new weight for the relation
							int targetId = relation.getTargetRelId();
							tempRel.get(targetId).setWeight(weight); //Add it to the weight list
						}
						tempRel.remove(j); //remove this relation, because it is not necessary for the calculation anymore
					}
				}
				tempArg.remove(i); //Remove this argument, because it is not necessary for the calculation anymore
			}
		}
		if(solved == false){ //If there are still arguments to be calcuted, recursively solve the remaining arguments and relations
			solution = solve (mode, threshold, tempArg, tempRel, solution);
		}else{ //If there were no more arguments to solve, return the current solution
			return solution;
		}
	}

	//See if there are any relations with this argument as a target
	public boolean isLeaf(Argument argument, Arraylist<Relation> relations){
		for (int i = 0; i < relations.size(); i++) {
			if(relations.get(i).getTargetArgId = argument.argId){
				return false; //If there is a relation with this argument as a target, the argument is not a leaf
			}
		}
		return true; //Otherwise it is a leaf
	}

}
