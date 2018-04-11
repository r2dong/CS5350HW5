package main;

public class Driver {
	public static void main(String[] args) {

		String source = null;
		String target = null;
		TransformationSequence t = new TransformationSequence();

		if (!args[0].equals("random")) {
			source = args[0];
			target = args[1];
		} else {
			int length = Integer.parseInt(args[1]);
			source = RandomStringGenerator.generate(length);
			target = RandomStringGenerator.mutate(source);
		}
		long startTime = System.nanoTime();
		Step result = t.getTranSeqWrapper(source, target);
		long endTime = System.nanoTime();
		double totalTime = (endTime - startTime) / 1000000000;
		System.out.println("total time = " + totalTime + " seconds.\n");
		result.print();
	}
}
