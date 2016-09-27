
public class Relation {

	private  int relationId = 0; //The identifier for this relation
	private  int originId = 0; //The id of the argument where this relation originates from
	private  int targetArgId = 0; //The target of this relation. If the target is a relation, this value is null
	private int frontArgId = 0;//frontArgId and endArgId are to specify which relation it undercuts.
	private int endArgId = 0;//the reason why it shouldn't be targetRelationId is that the targetRelationId is not the input of the agent. 
	//The agent only knows which relation between two arguments it undercuts instead of the id of that relation.
	private  double weight = 0; //The weight of this relation. TODO: think of sensible values
	
	
//=======
//	private  int relationId; //The identifier for this relation
//	private  int origin; //The id of the argument where this relation originates from
//	private int targetArgument; //The target of this relation. If the target is a relation, this value is null
//  private int targetRelation; //The target of this relation. If the target is an argument, this value is null
//  private double weight; //The weight of this relation. TODO: think of sensible values
//
//	public  void addRelation(int relationId, int origin, int targetArgument, int targetRelation, double weight){
//		this.relationId = relationId;
//		this.origin = origin;
//    this.targetArgument = targetArgument;
//    this.targetRelation = targetRelation;
//		if(weight != null){
//			setWeight (weight);
//		}else{
//			// TODO: think of a sensible standard value
//			setWeight (0.0);
//		}
//		// TODO: add error messages when origin or relation are null
//	}
//
//  //Based on which one of the getTarget functions returns a non-null value it is possible to discern if the target is a relation or an argument
//	public int getTargetArgument() {
//		if(targetArgument != null){
//      return targetArgument;
//    }else{
//      return null;
//    }
//	}
//
//  public int getTargetRelation() {
//		if(targetRelation != null){
//      return targetRelation;
//    }else{
//      return null;
//    }
//	}
//>>>>>>> 295990688f22770f58dad13447c867bd1bcf4b17

	
	
	public Relation(int relationId, int originId, int targetArgId, int frontArgId, int endArgId, double weight) {
		this.relationId = relationId;
		this.originId = originId;
		this.targetArgId = targetArgId;
		this.frontArgId = frontArgId;
		this.endArgId = endArgId;
		this.weight = weight;
	}

	public int getRelationId() {
		return relationId;
	}


	public void setRelationId(int relationId) {
		this.relationId = relationId;
	}


	public int getOriginId() {
		return originId;
	}


	public void setOriginId(int originId) {
		this.originId = originId;
	}


	public int getTargetArgId() {
		return targetArgId;
	}


	public void setTargetArgId(int targetArgId) {
		this.targetArgId = targetArgId;
	}


	public int getFrontArgId() {
		return frontArgId;
	}


	public void setFrontArgId(int frontArgId) {
		this.frontArgId = frontArgId;
	}


	public int getEndArgId() {
		return endArgId;
	}


	public void setEndArgId(int endArgId) {
		this.endArgId = endArgId;
	}


	public double getWeight() {
		return weight;
	}


	public void setWeight(double weight) {
		this.weight = weight;
	}
	

}
