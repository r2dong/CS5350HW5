package main;

import java.util.ArrayList;
import java.util.HashMap;

public class TransformationSequence {
	
	int inLength; // input size
	HashMap<Triple, Step> memFlip; // memoization tables
	HashMap<Duple, Step> memNonFlip;
	String in;
	String t;
	final ArrayList<Step> emptySteps = new ArrayList<>();
	final Step emptyStep = new Step(new ArrayList<>(), emptySteps);
	
	// initialize algorithm
	public Step getTranSeqWrapper(String in, String t) {
		if (in.length() != t.length()) {
			System.err.println("source and target length does not match");
			return null;
		}
		this.in = in;
		this.t = t;
		inLength = in.length();
		memFlip = new HashMap<>();
		memNonFlip = new HashMap<>();
		return getSeq(0);
	}
	
	// the actual algorithm
	private Step getSeq(int start) {
		
		Duple key = new Duple(start);
		
		// return answer if subproblem already solved
		if (memNonFlip.containsKey(key))
			return memNonFlip.get(key);
		
		// base case 1
		if (start == inLength)
			return emptyStep;
		
		/* base case 2
		 * not using memorization matrix here, both constant time. This way
		 * uses less memory
		 */
		if (start == inLength - 1) {
			if (in.charAt(start) == t.charAt(start))
				return emptyStep;
			else {
				ArrayList<String> answer = new ArrayList<>();
				answer.add(makeSubStr(in.charAt(start), start, t.charAt(start)));
				return new Step(answer, emptySteps);
			}
		}
		
		// helper variables to sum results from recursive calls
		ArrayList<Step> sumArr;
		ArrayList<String> current;
		Step a1;
		Step a2;
		Step curStep;
		
		//recursive case 1: try all possible flips including first letter
		Step minFlip = null;
		for (int i = start + 2; i <= inLength; i++) {
				a1 = getNFSeq(in, t, start, i);
				a2 = getSeq(i);
				sumArr = new ArrayList<>();
				sumArr.add(a1);
				sumArr.add(a2);
				current = new ArrayList<>();
				current.add(makeFlipStr(start, i - 1));
				curStep = new Step(current, sumArr);
				if (minFlip == null)
					minFlip = curStep;
				else if (curStep.size < minFlip.size)
					minFlip = curStep;
		}
		
		// recursive case 2, first letter does not belong to flip
		a1 = getSeq(start + 1);
		current = new ArrayList<>();
		sumArr = new ArrayList<>();
		sumArr.add(a1);
		if (in.charAt(start) != t.charAt(start))
			current.add(makeSubStr(in.charAt(start), start, t.charAt(start)));
		Step minSub = new Step(current, sumArr);
		
		// update memorization table, than return
		Step answer = minFlip.size < minSub.size ? minFlip : minSub;
		memNonFlip.put(key, answer);
		return answer;
	}
	
	// make a string representing flip
	private String makeFlipStr(int start, int end) {
		return "flip(" + start + ", " + end + ")";
	}
	
	// make a string representing a substitution
	private String makeSubStr(char source, int index, char t) {
		return index + ": " +  source + " -> " + t;
	}
	
	// calculate transformation path when a subarray can no loger be flipped
	private Step getNFSeq(String in, String t, int start, int end) {
		
		Triple key = new Triple(start, end);
		
		// return answer if sub-problem already solved
		if (memFlip.containsKey(key))
			return memFlip.get(key);
		
		ArrayList<String> seq = new ArrayList<>();
		int revInd;
		// check in reverse order due to the flip
		for (int i = start; i < end; i++) {
			revInd = end - (i - start) - 1;
			if (in.charAt(i) != t.charAt(revInd))
				seq.add(makeSubStr(in.charAt(i), revInd, 
						t.charAt(revInd)));
		}
		memFlip.put(key, new Step(seq, new ArrayList<>()));
		return memFlip.get(key);
	}
}
