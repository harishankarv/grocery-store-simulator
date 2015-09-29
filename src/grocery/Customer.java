package grocery;

import java.util.List;

/**
 * This class represents a Customer. It implements the {@link Comparable}
 * interface. This allow comparisons between customers, which helps to decide
 * which customer is allowed to choose the {@link Register} first.
 * 
 * @author harishankarv
 */
public class Customer implements Comparable<Customer> {

	enum CustomerType {
		A, B
	}

	CustomerType customerType;
	private int numberOfItems;
	private int arrivalTime;

	public Customer(String customerType, int arrivalTime, int numberOfItems) { 
		if (!(customerType.equals("A") || customerType.equals("B"))) {
			System.out.println("Invalid Customer Type\n");
			System.exit(-1);
		}
		this.customerType = CustomerType.valueOf(customerType);
		this.numberOfItems = numberOfItems;
		this.arrivalTime = arrivalTime;
	}

	public int numberOfItems() {
		return numberOfItems;
	}

	public void checkoutOneItem() {
		numberOfItems--;
	}

	/**
	 * Allows a customer to choose a {@link Register} given a list, and join the
	 * queue for the register. The method follows these rules:
	 * 
	 * Customer Type A always chooses the register with the shortest line (least
	 * number of customers in line). If two or more registers have the shortest
	 * line, Customer Type A will choose the register with the lowest register
	 * number (e.g. register #1 would be chosen over register #3). Customer Type
	 * B looks at the last customer in each line, and always chooses to be
	 * behind the customer with the fewest number of items left to check out,
	 * regardless of how many other customers are in the line or how many items
	 * they have. Customer Type B will always choose an empty line before a line
	 * with any customers in it.
	 * 
	 * @param registersList
	 */
	public void choose(List<Register> registersList) {

		Register winner = registersList.get(0);

		if (this.customerType == CustomerType.A) {
			int minLine = winner.getQueueSize();
			for (Register r : registersList) {
				int line = r.getQueueSize();
				if (line < minLine) {
					winner = r;
					minLine = line;
				} else if (line == minLine && r.getID() < winner.getID()) {
					winner = r;
				}
			}
		} else {

			int min = winner.getLastCustomer().numberOfItems;
			for (Register r : registersList) {
				if (r.isQueueEmpty()) {
					winner = r;
					break;
				} else if (r.getLastCustomer().numberOfItems() < min) {
					min = r.getLastCustomer().numberOfItems();
					winner = r;
				}
			}
		}

		winner.queueCustomer(this);

	}

	@Override
	public String toString() {
		return this.customerType.toString() + " " + this.arrivalTime + " " + this.numberOfItems;
	}

	/**
	 * Compares customers based on the numberOfItems.
	 */
	@Override
	public int compareTo(Customer that) {
		if (this.numberOfItems < that.numberOfItems)
			return -1;
		else if (this.numberOfItems > that.numberOfItems)
			return 1;
		else if (this.customerType == CustomerType.A && that.customerType == CustomerType.B)
			return -1;
		else if (this.customerType == CustomerType.B && that.customerType == CustomerType.A)
			return 1;
		else
			return 0;
	}

}
