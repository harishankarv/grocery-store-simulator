package grocery;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * The Simulator class uses the {@link Grocery} object and performs the
 * simulation using the interfaces it provides.
 * 
 * @author harishankarv
 *
 */
public class Simulator {

	Grocery groceryStore;

	/* Stores the time taken for the simulation */
	private int time;

	/**
	 * Returns the time taken by the simulation
	 * 
	 * @return time
	 */
	public int getTime() {
		return time;
	}

	/**
	 * Initializes the internal Grocery store object using the file provided.
	 * 
	 * @param filename
	 */
	public Simulator(String filename) {
		groceryStore = initialize(filename);
		this.time = simulate(groceryStore);
	}

	private Grocery initialize(String filename) {

		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String line = null;

			line = br.readLine();
			int numRegisters = Integer.parseInt(line);
			
			if(numRegisters <= 0){
				System.out.println("Cannot simulate without registers\n");
				System.exit(-1);
			}

			Grocery groceryStore = new Grocery(numRegisters);

			while ((line = br.readLine()) != null) {

				String[] tokens = line.split(" ");
				if (tokens.length != 3) {
					System.out.println("Error in input file\n");
					System.exit(-1);
				}
				int time = Integer.parseInt(tokens[1]);
				Customer customer = new Customer(tokens[0], time, Integer.parseInt(tokens[2]));

				Map<Integer, List<Customer>> customerMap = groceryStore.getCustomers();

				List<Customer> list = customerMap.get(time);
				if (list == null) {
					list = new ArrayList<Customer>();
					list.add(customer);
					customerMap.put(time, list);
				} else {
					list.add(customer);
				}
			}

			br.close();

			return groceryStore;

		} catch (FileNotFoundException e) {
			System.out.println("Input file not found:\n" + e.getMessage());
			System.exit(-1);
		} catch (NumberFormatException e) {
			System.out.println("Error in parsing the input file:\n" + e.getMessage());
			System.exit(-1);
		} catch (IOException e) {
			System.out.println("IOException while reading the input file:\n" + e.getMessage());
			System.exit(-1);
		}
		return null;
	}

	/**
	 * Simulation starts at time = 1. A loop counter maintains the time unit. At
	 * every iteration (which simulates a second) {@link Customer}(s) that
	 * arrive are first sorted according to the number of items they have using
	 * {@link Customer#compareTo(Customer)}} Then they are allowed to choose
	 * their preferred Register by calling {@link Customer#choose(List)} where
	 * they insert themselves into the register queue. Then
	 * {@link Grocery#processAllRegisters()} is called to check out one item
	 * from each customer (depending in the type of Register).
	 * 
	 * @param groceryStore
	 * @return time
	 */
	private int simulate(Grocery groceryStore) {
		int time = 1;
		while (time == 1 || !groceryStore.isEmpty()) {
			List<Customer> currentArrivals = groceryStore.getCustomers().get(time);
			if (currentArrivals != null) {
				Collections.sort(currentArrivals);
				for (Customer c : currentArrivals) {
					c.choose(groceryStore.getRegistersList());
				}
			}
			groceryStore.processAllRegisters();
			time++;
		}

		return time;
	}
}
