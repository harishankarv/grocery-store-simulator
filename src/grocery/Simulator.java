package grocery;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Simulator {
	
	public Simulator(String filename){
		Grocery groceryStore = initialize(filename);
		startSimulation(groceryStore); 
	}
	
	private Grocery initialize(String filename) {

		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String line = null;

			line = br.readLine();
			int numRegisters = Integer.parseInt(line);

			Grocery groceryStore = new Grocery(numRegisters);

			while ((line = br.readLine()) != null) {

				String[] tokens = line.split(" ");
				int time = Integer.parseInt(tokens[1]);
				Customer customer = new Customer(CustomerType.valueOf(tokens[0]), time, Integer.parseInt(tokens[2]));

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

	private void startSimulation(Grocery groceryStore) {
		int time = 1;
		while (time == 1 || !groceryStore.isEmpty()) {
			List<Customer> currentArrivals = groceryStore.getCustomers().get(time);
			if(currentArrivals != null) {
				Collections.sort(currentArrivals);
				for (Customer c : currentArrivals) {
					c.choose(groceryStore.getRegistersList());
				}
			}
			groceryStore.processAllRegisters();
			time++;
		}

		System.out.println("Finished at: t=" + time + " minutes");
	}
	
	public static void main(String[] args) {
		new Simulator(args[0]);
	}

}
