package grocery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import grocery.Register.RegisterType;


public class Grocery {

	private ArrayList<Register> registersList; //list of registers.
	private Map<Integer, List<Customer>> customers; //customers against their time of arrival
	
	public Grocery(int numRegisters) {
		this.customers = new HashMap<>();
		this.registersList = new ArrayList<>();
		for (int i = 1; i <= numRegisters; i++) {
			if (i == numRegisters) {
				this.registersList.add(new Register(RegisterType.TRAINING, i));
				break;
			}
			this.registersList.add(new Register(RegisterType.NORMAL, i));
		}
	}
	
	public void processAllRegisters() {
		for(Register r : registersList){
			r.processOneItem();
		}
	}
	
	/**
	 * @return {@link Register}
	 */
	public List<Register> getRegistersList(){
		return this.registersList;
	}
	
	public Map<Integer, List<Customer>> getCustomers(){
		return this.customers;
	}

	public boolean isEmpty() {
		for (Register r : registersList) {
			if (!r.isQueueEmpty())
				return false;
		}
		return true;
	}

}
