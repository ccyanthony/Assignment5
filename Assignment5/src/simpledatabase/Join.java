package simpledatabase;
import java.util.ArrayList;

public class Join extends Operator{

	private ArrayList<Attribute> newAttributeList;
	private String joinPredicate;
	ArrayList<Tuple> tuples1;

	
	//Join Constructor, join fill
	public Join(Operator leftChild, Operator rightChild, String joinPredicate){
		this.leftChild = leftChild;
		this.rightChild = rightChild;
		this.joinPredicate = joinPredicate;
		newAttributeList = new ArrayList<Attribute>();
		tuples1 = new ArrayList<Tuple>();
		
	}

	
	/**
     * It is used to return a new tuple which is already joined by the common attribute
     * @return the new joined tuple
     */
	//The record after join with two tables
	@Override
	public Tuple next(){
		Tuple tuple = null;
		Tuple leftTuple = leftChild.next();
		Boolean joint = false;
		while(leftTuple != null){
			tuples1.add(leftTuple);
			leftTuple = leftChild.next();
		}
		Tuple rightTuple = rightChild.next();
		while(rightTuple != null && !joint){
			joint = false;
			newAttributeList = new ArrayList<Attribute>();
			for(int i = 0; i < rightTuple.getAttributeList().size(); i++){
				if(rightTuple.getAttributeList().get(i).getAttributeName().equals("id")){
					for(Tuple temp: tuples1){
						for(int j = 0; j < temp.getAttributeList().size(); j++){
							if(temp.getAttributeName(j).equals(rightTuple.getAttributeList().get(i).getAttributeName()) && temp.getAttributeValue(j).equals(rightTuple.getAttributeList().get(i).getAttributeValue())){
								newAttributeList.addAll(temp.getAttributeList());
								joint = true;
							}
						}
					}
				}else{
					newAttributeList.add(rightTuple.getAttributeList().get(i));
				}
			}
			if(joint){
				tuple = new Tuple(newAttributeList);
				return tuple;
			}else{
				rightTuple = rightChild.next();
			}
		}
		return null;
	}
	
	
	/**
     * The function is used to get the attribute list of the tuple
     * @return attribute list
     */
	public ArrayList<Attribute> getAttributeList(){
		if(joinPredicate.isEmpty())
			return child.getAttributeList();
		else
			return(newAttributeList);
	}

}