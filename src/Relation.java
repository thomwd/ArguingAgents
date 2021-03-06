
public class Relation {

	private  int relId = 0; //The identifier for this relation
	private  int originId = 0; //The id of the argument where this relation originates from
	private  int targetArgId = 0; //The target of this relation. If the target is a relation, this value is null
	private  double weight = 0; //The weight of this relation. 
	private int targetRelId = 0;//The targeting relation id (for undercut case)
	private boolean support = true;// The boolean flag to show whether it is attack relation or support relation

	public Relation(int relId, int originId, int targetArgId, int targetRelId, double weight, boolean flag) {
		if(relId!= 0){
				this.relId = relId;
		}
		if(originId != 0){
			this.originId = originId;
		}
		this.targetArgId = targetArgId;
		this.targetRelId = targetRelId;
		if(flag == false){ //If we create a relation and we already know it is an attack relation, we store it as such
			this.weight = weight;
			this.support = false;
		}
		else if(weight < 0.0){ //If the weight of a relation is < 0 and it was a support relation, it now becomes an attack relation
			this.support = false;
			this.weight = (weight*-1);
		}else{
			this.weight = weight;
		}
	}

	public void setOriginId(int id) {
		this.originId = id;
	}

	public int getTargetArgId() {
      return targetArgId;
	}

	public int getTargetRelId() {
	  return targetRelId;
	}


	public void setFlag(boolean flag){ //set this flag to false if the relation is an attack relation and to true if it is a support relation 
		support = flag;
	}
	
	public boolean getFlag(){ //returns false if it is an attack relation and true if it is a support relation
		return support;
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
