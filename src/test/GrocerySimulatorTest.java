package test;

import static org.junit.Assert.*;

import org.junit.Test;

import grocery.Simulator;

public class GrocerySimulatorTest {

	@Test
	public void test() {
		String[] inputs = {"1.txt", "2.txt", "3.txt", "4.txt", "5.txt"};
		int[] answers = {7, 13, 6, 9, 11};
		for (int i =0; i<answers.length; i++){
			Simulator simulator = new Simulator(inputs[i]);
			int time = simulator.getTime();
			assertEquals(answers[i], time);
		}
	}

}
