package main;

import java.util.ArrayList;
import java.util.HashMap;

public class TransformationSequence {
	
	int inLength; // input size
	HashMap<Triple, ArrayList<String>> mem; // memoization table
	final ArrayList<String> empty = new ArrayList<>();
	
	// initialize algorithm
	public ArrayList<String> getTranSeqWrapper(String in, String t) {
		if (in.length() != t.length()) {
			System.err.println("source and target length does not match");
			return null;
		}
		inLength = in.length();
		mem = new HashMap<>();
		return getSeq(in, t, 0, in.length());
	}
	
	// the actual algorithm
	private ArrayList<String> getSeq(String in, String t, int start, int end) {
		
		Triple key = new Triple(start, end, false);
		
		// return answer if subproblem already solved
		if (mem.containsKey(key))
			return mem.get(key);
		
		// base case 1
		if (start == end)
			return new ArrayList<String>();
		
		/* base case 2
		 * not using memorization matrix here, both constant time, not sure
		 * which has a larger overhead. In either case should not be a big
		 * deal
		 */
		if (start == end - 1) {
			if (in.charAt(start) == t.charAt(start))
				return empty;
			else {
				ArrayList<String> answer = new ArrayList<>();
				answer.add(makeSubStr(in.charAt(start), start, t.charAt(start)));
				return answer;
			}
		}
		
		// helper variables to sum results from recursive calls
		ArrayList<String> sumArr;
		ArrayList<String> a1;
		ArrayList<String> a2;
		
		//recursive case 1: try all possible flips including first letter
		ArrayList<String> curMinFlipArr = null;
		for (int i = start + 2; i <= inLength; i++) {
				a1 = getNFSeq(in, t, start, i);
				a2 = getSeq(in, t, i, inLength);
				sumArr = new ArrayList<>();
				sumArr.addAll(a1);
				sumArr.addAll(a2);
				sumArr.add(makeFlipStr(start, i - 1));
				if (curMinFlipArr == null)
					curMinFlipArr = sumArr;
				else if (sumArr.size() < curMinFlipArr.size())
					curMinFlipArr = sumArr;
		}
		
		// recursive case 2, first letter does not belong to flip
		ArrayList<String> curMinSubArr = null;
		a1 = getSeq(in, t, start + 1, inLength);
		sumArr = new ArrayList<>();
		if (in.charAt(start) != t.charAt(start)) {
			sumArr.add(makeSubStr(in.charAt(start), start, t.charAt(start)));
		}
		sumArr.addAll(a1);
		curMinSubArr = sumArr;
		
		// update memorization table, than return
		ArrayList<String> answer = curMinFlipArr.size() < curMinSubArr.size() ?
				curMinFlipArr : curMinSubArr;
		mem.put(key, answer);
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
	private ArrayList<String> getNFSeq(String in, String t, int start, int end) {
		
		Triple key = new Triple(start, end, true);
		
		// return answer if sub-problem already solved
		if (mem.containsKey(key))
			return mem.get(key);
		
		ArrayList<String> seq = new ArrayList<String>();
		int revInd;
		// check in reverse order due to the flip
		for (int i = start; i < end; i++) {
			revInd = end - (i - start) - 1;
			if (in.charAt(i) != t.charAt(revInd))
				seq.add(makeSubStr(in.charAt(i), revInd, 
						t.charAt(revInd)));
		}
		mem.put(key, seq);
		return seq;
	}
	
	/* due to recursion, order of elementary moves are in reverse oder, so we
	 * print results in reverse to get the correct order
	 */
	public void reverselyPrintArray(ArrayList<String> arr) {
		for (int i = arr.size() - 1; i > -1; i--)
			System.err.println(arr.get(i));
	}
	
	public static void main(String[] args) {
		
		String source = null;
		String target = null;
		TransformationSequence t = new TransformationSequence();
			
		if (!args[0].equals("random")) {
			source = args[0];
			target = args[1];
		}
		else {
			int length = Integer.parseInt(args[1]);
			source = RandomStringGenerator.generate(length);
			target = RandomStringGenerator.mutate(source);
		}
		long startTime = System.nanoTime();
		ArrayList<String> result = t.getTranSeqWrapper(source, target);
		long endTime = System.nanoTime();
		double totalTime = (endTime - startTime) / 1000000000;
		System.err.println("total time = " + totalTime + " seconds.\n");
		t.reverselyPrintArray(result);
	}
}
