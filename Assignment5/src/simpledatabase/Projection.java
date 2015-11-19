package simpledatabase;
import java.util.ArrayList;

public class Projection extends Operator{
	
	ArrayList<Attribute> newAttributeList;
	private String attributePredicate;


	public Projection(Operator child, String attributePredicate){
		
		this.attributePredicate = attributePredicate;
		this.child = child;
		newAttributeList = new ArrayList<Attribute>();
		
	}
	
	
	/**
     * Return the data of the selected attribute as tuple format
     * @return tuple
     */
	@Override
	public Tuple next(){
		Tuple tuple = child.next();
		//check if child return tuple
		if (tuple == null){
			return null;
		}else{
			newAttributeList = new ArrayList<Attribute>();
			Tuple newTuple;
			//loop for the attributes of child tuple
			for(Attribute attribute: tuple.getAttributeList()){
				//check if the attribute name is equal to the attributePredicate
				if(attribute.getAttributeName().equals(attributePredicate)){
					//add the attribute to the new attribute array list
					newAttributeList.add(attribute);
				}
			}
			//create a new tuple with new attribute array list and return
			newTuple = new Tuple(newAttributeList);
			return newTuple;
		}
	}
		

	
	/**
     * The function is used to get the attribute list of the tuple
     * @return attribute list
     */
	public ArrayList<Attribute> getAttributeList(){
		return child.getAttributeList();
	}

}