
public class Relation {
	private  int relationId; //The identifier for this relation
	private  int origin; //The id of the argument where this relation originates from
	private int targetArgument; //The target of this relation. If the target is a relation, this value is null
  private int targetRelation; //The target of this relation. If the target is an argument, this value is null
  private double weight; //The weight of this relation. TODO: think of sensible values

	public  void addRelation(int relationId, int origin, int targetArgument, int targetRelation, double weight){
		this.relationId = relationId;
		this.origin = origin;
    this.targetArgument = targetArgument;
    this.targetRelation = targetRelation;
		if(weight != null){
			setWeight (weight);
		}else{
			// TODO: think of a sensible standard value
			setWeight (0.0);
		}
		// TODO: add error messages when origin or relation are null
	}

  //Based on which one of the getTarget functions returns a non-null value it is possible to discern if the target is a relation or an argument
	public int getTargetArgument() {
		if(targetArgument != null){
      return targetArgument;
    }else{
      return null;
    }
	}

  public int getTargetRelation() {
		if(targetRelation != null){
      return targetRelation;
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
