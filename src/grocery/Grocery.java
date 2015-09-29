package grocery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import grocery.Register.RegisterType;

/**
 * The grocery class contains: a list of {@link Register}, and and a map of
 * {@link Customer}, which holds the {@link Customer} object against their time
 * of arrival.
 * 
 * The constructor accepts the number of registers as argument and initializes
 * them, with one register as the training register. The
 * {@link Grocery#processAllRegisters()} allows the simulation of 1 second of
 * time, and is used by the simulator. {@link Grocery#getRegistersList()} is
 * used by the {@link Customer} to choose a {@link Register} according to
 * preference. {@link Grocery#getCustomers())} returns the internal customer
 * map, and is used by the simulator for simulation.
 * 
 * @author harishankarv
 */
public class Grocery {

	/* list of registers. */
	private ArrayList<Register> registersList;

	/* map of customers against their time of arrival */
	private Map<Integer, List<Customer>> customers;

	/**
	 * Constructs a Grocery object given the number of registers.
	 * 
	 * @param numRegisters
	 */
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
		for (Register r : registersList) {
			r.processOneItem();
		}
	}

	/**
	 * Returns the internal list of registers. Used by the customer to choose
	 * the register.
	 */
	public List<Register> getRegistersList() {
		return this.registersList;
	}

	/**
	 * Returns the internal map of customers. Used by the {@link Simulator}
	 */
	public Map<Integer, List<Customer>> getCustomers() {
		return this.customers;
	}

	/**
	 * Returns true if all the registers in the Grocery store are empty.
	 * Iterates through all the registers to find if every one of them is empty.
	 */
	public boolean isEmpty() {
		for (Register r : registersList) {
			if (!r.isQueueEmpty())
				return false;
		}
		return true;
	}

}
