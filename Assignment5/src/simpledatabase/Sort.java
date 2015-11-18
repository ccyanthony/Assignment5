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
		if(!returned){
			Tuple tuple = child.next();
			ArrayList<Tuple> temp = new ArrayList<Tuple>();
			while(tuple != null){
				temp.add(tuple);
				tuple = child.next();
			}
			if(temp.isEmpty()){
				System.out.print("isEmpty");
				return null;
			}else{
				tuple = temp.get(0);
				int i = 0;
				for(i = 0; i < tuple.getAttributeList().size(); i++){
					if(tuple.getAttributeName(i).equals(orderPredicate)){
						break;
					}
				}
				while(!temp.isEmpty()){
					int countMin = 0;
					for(int j = 0; j < temp.size(); j++){
						if(temp.get(j).getAttributeValue(i).toString().compareTo(temp.get(countMin).getAttributeValue(i).toString()) < 0){
							countMin = j;
						}
					}
					tuplesResult.add(temp.get(countMin));
					temp.remove(countMin);
				}
			}
		}
		if(tuplesResult.isEmpty()){
			return null;
		}else{
			returned = true;
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