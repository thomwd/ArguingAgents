/**
 * Represents a complete argumentation framework, (a directed graph)
 * If we want to load a new framework, we create a new framework object and discard the existing one
 */


 	//IMPORT MATHS
import java.lang.Math;
import java.util.*;
public class Framework {
	private  String topicDescription;		// what the argumentation is about
	private  String topicSummary;			// short summary of description
	private ArrayList<Argument> positions;	/* competing (final) positions in the argumentation, e.g. 'is guilty'*/
	private ArrayList<Argument> arguments;	// all arguments
	private ArrayList<Relation> relations;	// all relations between arguments and other arguments
											// and between arguments and relations

	// TODO
	// constructors/getters/setters


	//Function to calculate the activity of arguments, based on their mode
	public double solveArgument (String mode, ArrayList<Argument> argumentList, Relation relation){
		int targetId = relation.getTargetArgId();
		int originId = relation.getOriginId();
		Argument origin = argumentList.get(originId);
		Argument target = argumentList.get(targetId);
		if(mode == "POE"){ //Preponderance of Evidence
			double activity = target.getActivity() + (origin.getActivity() * relation.getWeight());
			return activity;
		}else if(mode == "BRD"){ //Beyond Reasonable Doubt
			if((origin.getActivity() * relation.getWeight()) < 0){ //If the argument attacks the target argument, there is doubt, so set the activity of the target argument to 0
				return 0;
			}else{
				return target.getActivity();
			}
		}else if(mode == "SOE"){ //Scintilla of Evidence
			if((origin.getActivity() * relation.getWeight()) > 0){ //If the argument supports the target argument, there is a scintilla of evidence, so set the activity of the target argument to 1
				return 1;
			}else{
				return target.getActivity();
			}
		}else{ //Error because it is not one of the predefined modes
			System.err.println("Error: mode is not recognized.");
			return 0;
		}
	}

	public double solveRelation(String mode, ArrayList<Relation> relationList, ArrayList<Argument> argumentList, Relation relation){
		int targetId = relation.getTargetRelId();
		int originId = relation.getOriginId();
		Argument origin = argumentList.get(originId);
		Relation target = relationList.get(targetId);
		double weight = 0; //TODO fix this
		//TODO: how do the modes work with the relations?
		// (Yannik:) I would skip modes for relations for now, think they are easier to understand for arguments

		// TODO this update is complex, let's discuss this in person
		if(target.getWeight()<0){
			weight = target.getWeight() - (origin.getActivity() * relation.getWeight());
		}else{
			weight = target.getWeight() + (origin.getActivity() * relation.getWeight());
		}
		return weight;
	}

	/**
	 * @param argumentList ArrayList<Argument> a copy of the field 'arguments'. (Not just a copy of the reference)
	 * @param relationList ArrayList<Relations> a copy of the field 'relations'. (Not just a copy of the reference)
	 * @param solution ArrayList<Argument> a copy of the field 'arguments'. (Not just a copy of the reference)
	 * @return ArrayList<Argument> a copy of the field 'arguments' for which all activations have been computed.
	 */
	public ArrayList<Argument> evaluate(String mode, double threshold, ArrayList<Argument> argumentList,
										ArrayList<Relation> relationList, ArrayList<Argument> solution){
		ArrayList<Argument> tempArg = argumentList; //TODO: why is this step necessary? I think we can just pass a copy
													// of the arraylist in the very first function call.
													// If we do decide to copy inside, this statement only copies the reference of the ArrayList.
		ArrayList<Relation> tempRel = relationList;
		boolean solved = true; // Flag to see if the framework is solved. Set to false if there are still arguments to be analyzed
		for(int i = 0; i < tempArg.size(); i++){ //Iterate over all arguments
			Argument argument = tempArg.get(i);
			if(isLeaf(argument,tempRel)==true){ //Check if the argument is an argument from the current layer
				//TODO: work with a threshold
				for(int j = 0; j < tempRel.size(); j++){ //Iterate over all the relations
					Relation relation = tempRel.get(j);
					if(relation.getOriginId() == argument.getArgId()){ //Check if the origin of the relation is the current argument
						if(relation.getTargetArgId()!=0){ //Check if the target of the relation is an argument
							solved = false; //There are still arguments to analyze
							double activity = solveArgument(mode, tempArg, relation); //Calculate the new activity for the argument
							int targetId = relation.getTargetArgId();
							tempArg.get(targetId).setActivity(activity); //Add it to the argument list
							solution.get(targetId).setActivity(activity);
							//If we want to do something with threshold it can be done here.
						}
						else if(relation.getTargetRelId()!=0){ //Check if the target of the relation is a relation
							solved = false; //There are still arguments to analyze
							double weight = solveRelation(mode, tempRel, tempArg, relation); //Calculate the new weight for the relation
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
			solution = evaluate (mode, threshold, tempArg, tempRel, solution);
		}
		return solution; //If there were no more arguments to solve, return the current solution
	}

	// See if there are any relations with this argument as a target
	public boolean isLeaf(Argument argument, ArrayList<Relation> relations){
		for (int i = 0; i < relations.size(); i++) {
			if(relations.get(i).getTargetArgId() == argument.getArgId()){
				return false; //If there is a relation with this argument as a target, the argument is not a leaf
			}
		}
		return true; //Otherwise it is a leaf
	}
}