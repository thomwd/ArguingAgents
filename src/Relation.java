
public class Relation {

	private  int relId = 0; //The identifier for this relation
	private  int originId = 0; //The id of the argument where this relation originates from
	private  int targetArgId = 0; //The target of this relation. If the target is a relation, this value is null
	/*private int frontArgId = 0;//frontArgId and endArgId are to specify which relation it undercuts.
	private int endArgId = 0;//the reason why it shouldn't be targetRelationId is that the targetRelationId is not the input of the agent.
	//The agent only knows which relation between two arguments it undercuts instead of the id of that relation.
*/	private  double weight = 0; //The weight of this relation. TODO: think of sensible values
	private int targetRelId = 0;
	private boolean support = true;

	public Relation(int relId, int originId, int targetArgId, int targetRelId, double weight, boolean flag) {
		if(relId!= 0){
				this.relId = relId;
		}else{
			//error
		}
		if(originId != 0){
			this.originId = originId;
		}
		this.targetArgId = targetArgId;
		this.targetRelId = targetRelId;
		if(flag == false){
			this.weight = weight;
		}
		else if(weight < 0.0){
			this.support = false;
			this.weight = (weight*-1);
		}else{
			this.weight = weight;
		}
	}

	public int getTargetArgId() {
		if(targetArgId != 0){
	      return targetArgId;
	    }else{
	      return 0;
	    }
	}

	public void setFlag(boolean flag){ //set this flag to false if the relation is an attack relation and to true if it is a support relation 
		support = flag;
	}
	
	public boolean getFlag(){ //returns false if it is an attack relation and true if it is a support relation
		return support;
	}


	public int getTargetRelId() {
		if(targetRelId != 0){
	      return targetRelId;
	    }else{
	      return 0;
	    }

	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public double getWeight(){
		return weight;
	}

	
	
	
	public int getRelId() {
		return relId;
	}


	public int getOriginId() {
		return originId;
	}
	

}
