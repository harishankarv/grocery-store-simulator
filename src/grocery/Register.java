package grocery;

import java.util.LinkedList;
import java.util.Queue;

/**
 * This class represents a register. It internally contains a queue of
 * customers. The queueCustomer method allows to queue a {@link Customer} object
 * into this registers queue.
 * 
 * @author harishankarv
 *
 */
public class Register {

	/**
	 * This represents the type of the register. It is used by {@link Register}
	 * to decide how to process customers.
	 * 
	 * @author harishankarv
	 *
	 */
	enum RegisterType {
		TRAINING, NORMAL;
	}

	RegisterType registerType;
	private int id;

	/* Customers currently in line */
	private Queue<Customer> customerQueue = new LinkedList<>();

	/* Reference to the last customer for helping type B customers decide */
	private Customer lastCustomer;

	/* Used internally for mimicking 2x time for Training Registers */
	private boolean x2Time = false;

	/**
	 * Adds a Customer to the internal queue. and updates the lastCustomer.
	 * 
	 * @param c
	 *            the Customer
	 */
	public void queueCustomer(Customer c) {
		lastCustomer = c;
		customerQueue.add(c);
	}

	public Customer getLastCustomer() {
		return lastCustomer;
	}

	/**
	 * Return the size of the queue.
	 * 
	 * @return size of queue
	 */
	public int getQueueSize() {
		return this.customerQueue.size(); 
	}

	public int getID() {
		return id;
	}

	public boolean isQueueEmpty() {
		return this.customerQueue.isEmpty();
	}

	/**
	 * Depending on the {@link RegisterType} this register, this method checks
	 * out one item from the customers queue by calling {@link Customer#checkoutOneItem()}.
	 * To simulate slower time on training registers, a boolean variable is used.
	 */
	public void processOneItem() {
		if (this.isQueueEmpty())
			return;

		Customer frontCustomer = customerQueue.peek();
		if (this.registerType == RegisterType.TRAINING && x2Time == false) {
			x2Time = true;
		} else {
			frontCustomer.checkoutOneItem();
			x2Time = false;
			if (frontCustomer.numberOfItems() == 0) {
				customerQueue.remove();
				if(isQueueEmpty()){
					lastCustomer = null;
				}
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
