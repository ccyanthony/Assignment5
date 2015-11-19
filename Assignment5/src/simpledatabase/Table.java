package simpledatabase;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Table extends Operator{
	private BufferedReader br = null;
	private boolean getAttribute=false;
	private Tuple tuple;
	private String attributeLine;
	private String dataTypeLine;

	
	public Table(String from){
		this.from = from;
		
		//Create buffer reader
		try{
			br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/datafile/"+from+".csv")));
		}
		catch (Exception e) {
			e.printStackTrace();
		} 
		
	}

	
	/**
     * Create a new tuple and return the tuple to its parent.
     * Set the attribute list if you have not prepare the attribute list
     * @return the tuple
     */
	@Override
	public Tuple next(){
		tuple = null;
		String tupleLine;
		try{
			//check if getAttribute before
			if(getAttribute == false){
				//read the attribute name and type line and set getAttribute
				attributeLine = br.readLine();
				dataTypeLine = br.readLine();
				getAttribute = true;
			}
			//read the value line
			tupleLine = br.readLine();
			//check if the value line is null
			if (tupleLine == null){
				return null;
			}
			//create new tuple for return with using the first constructor the attribute name, type and value
			tuple = new Tuple(attributeLine, dataTypeLine, tupleLine);
			//set the tuple's attributeArrayList
			tuple.setAttributeName();
			tuple.setAttributeType();
			tuple.setAttributeValue();
			//return the new created tuple
			return tuple;
		} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return null;
	}
	

	/**
     * The function is used to get the attribute list of the tuple
     * @return the attribute list
     */
	public ArrayList<Attribute> getAttributeList(){
		return tuple.getAttributeList();
	}
	
}