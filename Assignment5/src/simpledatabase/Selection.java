package simpledatabase;
import java.util.ArrayList;

public class Selection extends Operator{
	
	ArrayList<Attribute> attributeList;
	String whereTablePredicate;
	String whereAttributePredicate;
	String whereValuePredicate;

	
	public Selection(Operator child, String whereTablePredicate, String whereAttributePredicate, String whereValuePredicate) {
		this.child = child;
		this.whereTablePredicate = whereTablePredicate;
		this.whereAttributePredicate = whereAttributePredicate;
		this.whereValuePredicate = whereValuePredicate;
		attributeList = new ArrayList<Attribute>();

	}
	
	
	/**
     * Get the tuple which match to the where condition
     * @return the tuple
     */
	@Override
	public Tuple next(){
		Tuple tuple = child.next();
		Boolean returned = false;
		//check if the child's table is equal to whereTablePredicate
		if(child.from.equals(whereTablePredicate)){
			//loop while child return tuple and haven't returned
			while(tuple != null && !returned){
				//loop for the attribute of child tuple's attribute
				for (Attribute attribute: tuple.getAttributeList()){
					//check if the attribute name and value equal to the whereAttributePredicate and whereValuePredicate
					if(attribute.getAttributeName().equals(whereAttributePredicate) && attribute.getAttributeValue().equals(whereValuePredicate)){
						//set it is returned and return the tuple
						returned = true;
						return tuple;
					}
				}
				tuple = child.next();
			}
		}else if(tuple != null){
			//if the child's table is not equal to whereTablePredicate, just return the tuple
			return new Tuple(tuple.getAttributeList());
		}
		return null;
	}
	
	/**
     * The function is used to get the attribute list of the tuple
     * @return the attribute list
     */
	public ArrayList<Attribute> getAttributeList(){
		
		return(child.getAttributeList());
	}

	
}