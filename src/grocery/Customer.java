package grocery;

import java.util.List;

enum CustomerType {
	A, B
}

public class Customer implements Comparable<Customer> {

	CustomerType customerType;
	private int numberOfItems;
	private int arrivalTime;

	public Customer(CustomerType customerType, int arrivalTime, int numberOfItems) {
		this.customerType = customerType;
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
	 * @param registersList
	 */
	public void choose(List<Register> registersList) {

		Register winner = registersList.get(0);

		if (this.customerType == CustomerType.A) {
			/*
			 * Customer Type A always chooses the register with the shortest
			 * line (least number of customers in line). If two or more
			 * registers have the shortest line, Customer Type A will choose the
			 * register with the lowest register number (e.g. register #1 would
			 * be chosen over register #3).
			 */
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
			/*
			 * Customer Type B looks at the last customer in each line, and
			 * always chooses to be behind the customer with the fewest number
			 * of items left to check out, regardless of how many other
			 * customers are in the line or how many items they have. Customer
			 * Type B will always choose an empty line before a line with any
			 * customers in it.
			 */

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
