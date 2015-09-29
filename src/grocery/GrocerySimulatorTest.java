package grocery;

import static org.junit.Assert.*;

import org.junit.Test;

public class GrocerySimulatorTest {

	@Test
	public void test() {
		String[] inputs = {"./tests/1.txt", "./tests/2.txt", "./tests/3.txt", "./tests/4.txt", "./tests/5.txt"};
		int[] answers = {7, 13, 6, 9, 11};
		for (int i =0; i<answers.length; i++){
			Simulator simulator = new Simulator(inputs[i]);
			int time = simulator.getTime();
			assertEquals(answers[i], time);
		}
	}

}
