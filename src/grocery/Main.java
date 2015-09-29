package grocery;

public class Main {

	public static void main(String[] args) {
		Simulator simulator = new Simulator(args[0]);
		int time = simulator.getTime();
		System.out.println("Finished at: t=" + time + " minutes");
	}
}
