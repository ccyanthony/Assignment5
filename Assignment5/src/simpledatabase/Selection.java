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
		if(child.from.equals(whereTablePredicate)){
			while(tuple != null && !returned){
				for (Attribute attribute: tuple.getAttributeList()){
					if(attribute.getAttributeName().equals(whereAttributePredicate) && attribute.getAttributeValue().equals(whereValuePredicate)){
						returned = true;
						return tuple;
					}
				}
				tuple = child.next();
			}
		}else if(tuple != null){
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