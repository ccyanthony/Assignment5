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
		//put all leftChild tuple to tuples1 array
		while(leftTuple != null){
			tuples1.add(leftTuple);
			leftTuple = leftChild.next();
		}
		//loop while rightChild return tuple and haven't joint
		Tuple rightTuple = rightChild.next();
		while(rightTuple != null && !joint){
			joint = false;
			newAttributeList = new ArrayList<Attribute>();
			//loop for the size of rightchild's tuple's attribute size
			for(int i = 0; i < rightTuple.getAttributeList().size(); i++){
				//check if the i's attributeName is the common key - "id"
				if(rightTuple.getAttributeList().get(i).getAttributeName().equals("id")){
					//loop for the tuples from the leftChild
					for(Tuple temp: tuples1){
						//loop for the leftChild's tuple's attribute size
						for(int j = 0; j < temp.getAttributeList().size(); j++){
							//check if the attribute name and value of leftchild is equal to rightchild's 
							if(temp.getAttributeName(j).equals(rightTuple.getAttributeList().get(i).getAttributeName()) && temp.getAttributeValue(j).equals(rightTuple.getAttributeList().get(i).getAttributeValue())){
								//add them all to the array list
								newAttributeList.addAll(temp.getAttributeList());
								joint = true;
							}
						}
					}
				}else{
					//add the non common attribute to the array list
					newAttributeList.add(rightTuple.getAttributeList().get(i));
				}
			}
			//check if joint
			if(joint){
				//create a new tuple with joint arrtibute list and return
				tuple = new Tuple(newAttributeList);
				return tuple;
			}else{
				//if not joint, go to the next tuple of rightChild
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