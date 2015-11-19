package simpledatabase;
import java.util.ArrayList;

public class Sort extends Operator{
	
	private ArrayList<Attribute> newAttributeList;
	private String orderPredicate;
	ArrayList<Tuple> tuplesResult;
	Boolean returned = false;

	
	public Sort(Operator child, String orderPredicate){
		this.child = child;
		this.orderPredicate = orderPredicate;
		newAttributeList = new ArrayList<Attribute>();
		tuplesResult = new ArrayList<Tuple>();
		
	}
	
	
	/**
     * The function is used to return the sorted tuple
     * @return tuple
     */
	@Override
	public Tuple next(){
		//check if sorted tuple to the result array
		if(!returned){
			Tuple tuple = child.next();
			ArrayList<Tuple> temp = new ArrayList<Tuple>();
			//add tuples to temp array
			while(tuple != null){
				temp.add(tuple);
				tuple = child.next();
			}
			//check if no tuple in array
			if(temp.isEmpty()){
				return null;
			}else{
				//use i to find the index of attribute which to be sorted
				tuple = temp.get(0);
				int i = 0;
				for(i = 0; i < tuple.getAttributeList().size(); i++){
					if(tuple.getAttributeName(i).equals(orderPredicate)){
						break;
					}
				}
				//loop till sort all tuples from temp to result array
				while(!temp.isEmpty()){
					//use countMin as counter to count the minimum tuple
					int countMin = 0;
					//loop to find the minimum in temp
					for(int j = 0; j < temp.size(); j++){
						if(temp.get(j).getAttributeValue(i).toString().compareTo(temp.get(countMin).getAttributeValue(i).toString()) < 0){
							countMin = j;
						}
					}
					//put minimum tuple to result array and remove from temp array
					tuplesResult.add(temp.get(countMin));
					temp.remove(countMin);
				}
			}
		}
		if(tuplesResult.isEmpty()){
			return null;
		}else{
			//set the boolean is returned
			returned = true;
			//remove the top of the array to returnTuple and return it
			Tuple returnTuple = tuplesResult.remove(0);
			return returnTuple;
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