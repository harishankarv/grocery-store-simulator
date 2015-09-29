package grocery;

import java.util.LinkedList;
import java.util.Queue;

public class Register {

	enum RegisterType {
		TRAINING, NORMAL;
	}

	RegisterType registerType;
	private int id;
	private Queue<Customer> customerQueue = new LinkedList<>(); // customers in
																// line
	private Customer lastCustomer; // for helping B decide
	boolean x2Time = false; // for mimicking 2x time

	public boolean isTraining() {
		return this.registerType == RegisterType.TRAINING;
	}

	public void queueCustomer(Customer c) {
		lastCustomer = c;
		customerQueue.add(c);
	}

	public Customer getLastCustomer() {
		return lastCustomer;
	}

	public int getQueueSize() {
		return this.customerQueue.size() - 1; //current customer doesn't count in queue.
	}

	public int getID() {
		return id;
	}

	public boolean isQueueEmpty() {
		return this.customerQueue.isEmpty();
	}

	public void processOneItem() {
		if (this.isQueueEmpty())
			return;

		Customer front = customerQueue.peek();
		if (this.registerType == RegisterType.TRAINING && x2Time == false) {
			x2Time = true;
		} else {
			front.checkoutOneItem();
			x2Time = false;
			if (front.numberOfItems() == 0) {
				customerQueue.remove();
			}
		}
	}

	@Override
	public String toString() {
		return registerType + ", id=" + id;
	}

	public Register(RegisterType registerType, int id) {
		this.registerType = registerType;
		this.id = id;
	}

}
