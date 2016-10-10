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
	 * @param arguments ArrayList<Argument> a copy of the field 'arguments'. (Not just a copy of the reference)
	 * @param relations ArrayList<Relations> a copy of the field 'relations'. (Not just a copy of the reference)
	 * @param solution ArrayList<Argument> a copy of the field 'arguments'. (Not just a copy of the reference)
	 * @return ArrayList<Argument> a copy of the field 'arguments' for which all activations have been computed.
	 */
	public ArrayList<Argument> evaluate(String mode, double threshold, ArrayList<Argument> arguments,
										ArrayList<Relation> relations, ArrayList<Argument> solution){
		boolean solved = true; // Flag to see if the framework is solved. Set to false if there are still arguments to be analyzed
		for(int i = 0; i < arguments.size(); i++){ 			//Iterate over all arguments
			Argument argument = arguments.get(i);
			if(isLeaf(argument,relations)==true){ 			//Check if the argument is an argument from the current layer
				//TODO: work with a threshold
				for(int j = 0; j < relations.size(); j++){  //Iterate over all the relations
					Relation relation = relations.get(j);
															//Check if the origin of the relation is the current argument
					if(relation.getOriginId() == argument.getArgId()){
						if(relation.getTargetArgId()!=0){ 	//Check if the target of the relation is an argument
							solved = false; 				//There are still arguments to analyze
															//Calculate the new activity for the argument
							double activity = solveArgument(mode, arguments, relation);
							int targetId = relation.getTargetArgId();
															//Add it to the argument list
							arguments.get(targetId).setActivity(activity);
							solution.get(targetId).setActivity(activity);
							//If we want to do something with threshold it can be done here.
						}
						else if(relation.getTargetRelId()!=0){ //Check if the target of the relation is a relation
							solved = false; 				   //There are still arguments to analyze
							double weight = solveRelation(mode, relations, arguments, relation); //Calculate the new weight for the relation
							int targetId = relation.getTargetRelId();
																//Add it to the weight list
							relations.get(targetId).setWeight(weight);
						}
						relations.remove(j); 					//remove this relation, because it is not necessary for the calculation anymore
					}
				}
				arguments.remove(i); 							//Remove this argument, because it is not necessary for the calculation anymore
			}
		}
		if(solved == false){ // If there are still arguments to be calcuted, recursively solve the remaining arguments and relations
			solution = evaluate (mode, threshold, arguments, relations, solution);
		}
		return solution; 	// If there were no more arguments to solve, return the current solution
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