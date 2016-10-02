
public class Relation {

	private  int relationId = 0; //The identifier for this relation
	private  int originId = 0; //The id of the argument where this relation originates from
	private  int targetArgId = 0; //The target of this relation. If the target is a relation, this value is null
	private int frontArgId = 0;//frontArgId and endArgId are to specify which relation it undercuts.
	private int endArgId = 0;//the reason why it shouldn't be targetRelationId is that the targetRelationId is not the input of the agent.
	//The agent only knows which relation between two arguments it undercuts instead of the id of that relation.
	private  double weight = 0; //The weight of this relation. TODO: think of sensible values
	private int targetRelId = 0;

	public void addRelation(int relationId, int originId, int targetArgId, int targetRelId, int frontArgId, int endArgId, double weight) {
		if(relationId != null){
				this.relationId = relationId;
		}else{
			//error
		}
		if(originId != null){
			this.originId = origin;
		}else{
			//error
		}
		this.targetArgId = targetArgId;
		this.targetRelId = targetRelId;
		this.frontArgId = frontArgId;
		this.endArgId = endArgId;
		if(weight != null){
			setWeight (weight);
		}else{
			// TODO: think of a sensible standard value
			setWeight (0.0);
		}
		// TODO: add error messages when origin or relation are null
	}

  //Based on which one of the getTarget functions returns a non-null value it is possible to discern if the target is a relation or an argument
	public int getTargetArgId() {
		if(targetArgId != null){
      return targetArgId;
    }else{
      return null;
    }
	}

	public int getFrontArgId(){
		return frontArgId;
	}

	public int getEndArgId(){
		return endArgId;
	}

  public int getTargetRelId() {
		if(targetRelId != null){
      return targetRelId;
    }else{
      return null;
    }
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public double getWeight(){
		return weight;
	}

}
