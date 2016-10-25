/**
 * Represents a complete argumentation framework, (a directed graph)
 * If we want to load a new framework, we create a new framework object and discard the existing one
 */


 	//IMPORT MATHS
import java.util.*;

import javax.swing.Action;
public class Framework {
	private static final Framework Framework = null;
	private  String topicDescription;		// what the argumentation is about
	private  String topicSummary;			// short summary of description
	private ArrayList<Argument> positions;	// competing (final) positions in the argumentation, e.g. 'is guilty'
	private ArrayList<Argument> arguments;	// all arguments
	private ArrayList<Relation> relations;	// all relations between arguments and other arguments
											// and between arguments and relations


	public Framework(String topic, String summary, ArrayList<Argument> argumentList, ArrayList<Relation> relationList) {
		this.topicDescription = topic;
		this.topicSummary = summary;
		this.arguments = argumentList;
		this.relations = relationList;
		this.positions = new ArrayList<Argument>();
		positions.add(argumentList.get(0));
		positions.add(argumentList.get(1));
	}

	public String getTopicDescription() {
		return topicDescription;
	}
	public String getTopicSummary() {
		return topicSummary;
	}
	public ArrayList<Argument> getPositions() {
		return positions;
	}
	public void setPositions(ArrayList<Argument> positions) {
		this.positions = positions;
	}
	public ArrayList<Argument> getArguments() {
		return arguments;
	}
	public ArrayList<Relation> getRelations() {
		return relations;
	}


	
	//Function to calculate the activity of arguments, based on their mode
	public static double solveArgument (String mode, ArrayList<Argument> argumentList, Relation relation){
		int targetId = relation.getTargetArgId();
		int originId = relation.getOriginId();
		
		Argument origin = getArg(originId, argumentList);
		Argument target = getArg(targetId, argumentList);
		
		//System.out.println("relId: "+relation.getRelId()+" origin: "+origin.getSummary()+" target: "+target.getSummary());
		if(mode == "POE"){ //Preponderance of Evidence
			//System.out.println("original activity for target: "+ target.getActivity());
			double activity = target.getActivity() + (origin.getActivity() * relation.getWeight());
			//System.out.println("new activity for target: " + activity);
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

	public static double solveRelation(String mode, ArrayList<Relation> relationList, ArrayList<Argument> argumentList, Relation relation){
		int targetRelId = relation.getTargetRelId();
		int originId = relation.getOriginId();
		
		Argument origin = getArg(originId, argumentList);
		Relation target = getRel(targetRelId, relationList);
		
		double weight = 0;

		// TODO this update is complex, let's discuss this in person
		//System.out.println("relId: "+relation.getRelId()+" targetRel: "+relation.getTargetRelId());
		
		weight = target.getWeight() + (origin.getActivity() * relation.getWeight());
		//System.out.println("original weight for target: "+target.getWeight());
		//System.out.println("new weight for target: "+weight);
		return weight;
	}

	public static double applyThreshold(double activity, String threshold){
		double k = 12;
		double x0 = 0.5;
		if(threshold == "sigmoid"){
			return 1/(1+Math.exp(-k*(activity-x0)));
		}else if (threshold == "binary"){
			//System.out.println("we are in binary mode");
			if(activity>0.5){
				return 1;
			}
			return 0;
		}
		return activity;
	}
	
	
	/**
	 * @param arguments ArrayList<Argument> a copy of the field 'arguments'. (Not just a copy of the reference)
	 * @param relations ArrayList<Relations> a copy of the field 'relations'. (Not just a copy of the reference)
	 * @param solution ArrayList<Argument> a copy of the field 'arguments'. (Not just a copy of the reference)
	 * @return ArrayList<Argument> a copy of the field 'arguments' for which all activations have been computed.
	 */
	//TODO: if threshold is changed to a function (lambda expression), this also needs to be changed in the argContribution method.
	public static ArrayList<Argument> evaluate(String mode, String threshold, ArrayList<Argument> arguments,
										ArrayList<Relation> relations, ArrayList<Argument> solution){
		boolean solved = true;                              // if false, function will call itself recursively.
		ArrayList<Integer> removeArgs = new ArrayList<Integer>();
		for(int i = 0; i < arguments.size(); i++){ 			// Iterate over all arguments
			Argument argument = arguments.get(i);
			if(isLeaf(argument,relations)){ 			    // Check if the argument is an argument from the current layer
                // enforce 0 <= activation <= 1
				if(argument.getActivity()<0){argument.setActivity(0);}
				if(argument.getActivity()>1){argument.setActivity(1);}
				//System.out.println("thresholding argument "+argument.getArgId());
				argument.setActivity(applyThreshold(argument.getActivity(),threshold));	
				if (argument.getArgId()>100) {
					getArg((argument.getArgId())/100, solution).setActivity(argument.getActivity());
				}else	getArg(argument.getArgId(), solution).setActivity(argument.getActivity());
				//System.out.println("activity is now  "+argument.getActivity());				
				//TODO: work with a threshold
				removeArgs.add(argument.getArgId());        // Add this argument to the list of arguments that need to be removed
				ArrayList<Integer> removeRels = new ArrayList<Integer>();
				for(int j = 0; j < relations.size(); j++){  // Iterate over all the relations
					Relation relation = relations.get(j);
					                                        // Check if the origin of the relation is the current argument
					if(relation.getOriginId() == argument.getArgId()){
						if(relation.getWeight()<0){                      // if weight has become negative due to undercuts
							relation.setFlag(!relation.getFlag());       // flip the attack/support flag
							relation.setWeight(relation.getWeight()*-1); // and make it positive again
						}
						if(!relation.getFlag()){                         // for attack, we add a negative value
							relation.setWeight(relation.getWeight()*-1);
						}
						
						if(relation.getTargetArgId()!=0){ 	// Check if the target of the relation is an argument
							solved = false; 				// There are still arguments to analyze
                                                            // Calculate the new activity for the argument
							double activity = solveArgument(mode, arguments, relation);
							int targetId = relation.getTargetArgId();
							getArg(targetId, arguments).setActivity(activity);
							
							//Make sure that activation is not below zero or above 1
							if(activity<0){activity=0;}
							if(activity>1){activity=1;}	
							getArg(targetId, solution).setActivity(activity);
						}
						else if(relation.getTargetRelId()!=0){  // Check if the target of the relation is a relation
							solved = false; 				    // There are still arguments to analyze
                                                                // Calculate the new weight for the relation
							double weight = solveRelation(mode, relations, arguments, relation);
							int targetId = relation.getTargetRelId();
							getRel(targetId, relations).setWeight(weight);
						}
						removeRels.add(relation.getRelId()); //Add this relation to the list of relations that need to be removed
					} // end if(isLeaf(argument,relations))
				} // end for(int j = 0; j < relations.size(); j++)
				
				// Remove all the relations that we do not need to analyze anymore
				for(int k=0; k < removeRels.size();k++){
                    //Do not remove a relation if it is the target of another relation
					if(isNotTargetRel(getRel(removeRels.get(k),relations),relations)==true){
						//System.out.println("Removed relation "+getRel(removeRels.get(k),relations).getRelId());
						relations.remove(getRel(removeRels.get(k),relations));
					}
				}
			} // end if(isLeaf(argument,relations))
		} // end for(int i = 0; i < arguments.size(); i++)

		//Remove all arguments that do not need to be analyzed anymore
		for(int k=0; k < removeArgs.size();k++){
			//System.out.println("Removed argument "+getArg(removeArgs.get(k),arguments).getArgId());
			arguments.remove(getArg(removeArgs.get(k),arguments));
		}
		// If there are still arguments to be calculated, recursively solve the remaining arguments and relations
		if(!solved){
			//System.out.println("It is not solved yet");
			solution = evaluate (mode, threshold, arguments, relations, solution);
		}
		return solution; 	// If there were no more arguments to solve, return the current solution
	} // end function evaluate

    /**
     * Calculates the contribution of this argument (and the subtree of arguments that attack/support it)
     * by creating a dummy argument with permanent activation 0 and no incoming relations. All outgoing
     * relations are transferred to the dummy argument such that they will not have any effect on other
     * arguments/relations.
     * @param  argId The argument id for which we contribute the contribution
     * @param  mode The mode that we use in the call to evaluate
     * @param  threshold The threshold that we use in the call to evaluate
     * @return updatedPos: The updated copies of the positions. We can then compare their activation to
     *                     our the activation of our real positions.
     */
    public ArrayList<Argument> argContribution(int argId, String mode, String threshold, ArrayList<Argument> arguments,ArrayList<Relation>relations) {
        ArrayList<Argument> copyArgs = Actions.copyArgArrayList(arguments);
        ArrayList<Relation> copyRels = Actions.copyRelArrayList(relations);
        Argument original = getArg(argId, arguments);
        int oldId = original.getArgId();            // dummy has and keeps 0 activation
       // int newId = 99999;                          // needs to be unique TODO: which id should be specified
        int newId = oldId*100;                          // needs to be unique TODO: which id should be specified
        Argument dummy = new Argument(newId, original.getAgentId(), "dummy", "dummy", 0);
        copyArgs.add(dummy);
        for(int j = 0; j < copyRels.size(); j++) {  // Transfer all outgoing relations
            Relation rel = copyRels.get(j);         // from original to dummy
            if(rel.getOriginId() == oldId) {        // so these will not have any effect
                rel.setOriginId(newId);             // since the activation of dummy is always 0
            }                                       // because there are no incoming relations
        }
        // Recompute activation after effectively removing all outgoing relations
        ArrayList<Argument> updatedArgs = new ArrayList<>();
        ArrayList<Argument> solistForContri = Actions.copyArgArrayList(arguments);
        updatedArgs = evaluate(mode, threshold, copyArgs, copyRels, solistForContri);
        ArrayList<Argument> updatedPos = new ArrayList<>();
        for (int j = 0; j < positions.size(); j++) {    // return updated Positions
            int posId = positions.get(j).getArgId();    // get old position id
            updatedPos.add(getArg(posId, updatedArgs)); // add matching argument (== updated position)
        }
        return updatedPos;                              // return updated positions
    }


	// See if there are any relations with this argument as a target
	public static boolean isLeaf(Argument argument, ArrayList<Relation> relations){
		for (int i = 0; i < relations.size(); i++) {
			if(relations.get(i).getTargetArgId() == argument.getArgId()){
				return false; //If there is a relation with this argument as a target, the argument is not a leaf
			}else if(relations.get(i).getOriginId() == argument.getArgId()){
				if(isNotTargetRel(relations.get(i),relations)==false){
					return false; //If this argument is the origin of a relation that still needs to be analyzed, this argument is not a leaf either
				}
			}			
		}
		//System.out.println("Argument "+argument.getArgId()+" is a leaf.");
		return true; //Otherwise it is a leaf
	}


	public static boolean isNotTargetRel(Relation relation, ArrayList<Relation> relations){
	//Check if a given relation is the target of another relation (and thus still needs to be analyzed)
		for (int i = 0; i < relations.size(); i++) {
			if(relations.get(i).getTargetRelId() == relation.getRelId()){				
				return false; //If there is a relation with this relation as a target, return false
			}
		}
		return true; //Otherwise return true
	}
	
	//A function to determine which arguments and relations are in which layer (right Michael?)
	public void showLeaf(ArrayList<Argument> arguments,ArrayList<Relation> relations){
		boolean isLeaf = true;
		int size = arguments.size();
		List<Integer> leafToRemove = new ArrayList<Integer>();
		for(int i = 0;i<size;i++){
			isLeaf = true;
			List<Integer> relToRemove = new ArrayList<Integer>();
			Argument argument = arguments.get(i);
			int argId = argument.getArgId();
			for(int j = 0;j<relations.size();j++){
				Relation relation = relations.get(j);
				if (relation.getTargetArgId() == argId) {
					isLeaf = false;
				}
			}
			if (isLeaf == true) {
				//System.out.println("Arg"+argId+" is leaf");
				for(int j = 0; j<relations.size();j++){
					if(relations.get(j).getOriginId() == argId){
						relToRemove.add(relations.get(j).getRelId());
					}
				}
				leafToRemove.add(argId);
				for(int q = 0;q<relToRemove.size();q++){
					//System.out.println("removed relation:"+relToRemove.get(q));
					relations.remove(getRel(relToRemove.get(q),relations));
				}
			}
		}
		for(int i = 0;i<leafToRemove.size();i++){
			arguments.remove(getArg(leafToRemove.get(i),arguments));
		}
		//System.out.println("go to next layer, size of remaining arguments: "+ arguments.size()+" size of remaining relations: "+relations.size());
		if (arguments.size() != 0 ) {
			showLeaf(arguments, relations);
		}else return;
		
	}
	
	//Get an argument from the argument list with a certain ID
	public static Argument getArg(int argId, ArrayList<Argument> arguments){
		Argument argument = null;
		for(int i = 0; i<arguments.size();i++){
			if (arguments.get(i).getArgId() == argId) {
				argument = arguments.get(i);
				break;
			}
		}
		return argument;
	}
	
	//Get a relation from the relation list with a certain ID
	public static Relation getRel(int relId, ArrayList<Relation> relations){
		Relation relation = null;
		for(int i = 0; i<relations.size();i++){
			if (relations.get(i).getRelId() == relId) {
				relation = relations.get(i);
				break;
			}
		}
		return relation;
	}
	
}
