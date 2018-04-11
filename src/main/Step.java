package main;

import java.util.ArrayList;

public class Step {
	
	final ArrayList<String> current;
	final ArrayList<Step> steps;
	final int size;
	
	Step(ArrayList<String> current, ArrayList<Step> steps) {
		this.current = current;
		this.steps = steps;
		int childTotalSize = 0;
		for (Step step : steps) {
			childTotalSize += step.size;
		}
		size = current.size() + childTotalSize;
	}
	
	// print results of this step
	void print() {
		for (String str : current)
			System.out.println(str);
		for (Step step : steps)
			step.print();
	}
}
