package main;

import java.util.ArrayList;
import java.util.HashMap;

public class TransformationSequence {
	
	HashMap<Triple, ArrayList<String>> mem;
	
	// handles initializations for the algorithm
	public ArrayList<String> getTranSeqWrapper(String in, String t) {
		mem = new HashMap<>();
		return getSeq(in, t, 0, in.length());
	}
	
	// the actual algorithm
	private ArrayList<String> getSeq(String in, String t, int start, int end) {
		
		Triple key = new Triple(start, end, false);
		
		// return answer if subproblem already solved
		if (mem.containsKey(key))
			return mem.get(key);
		
		int inLength = in.length();
		
		// base case 1
		if (start == end)
			return new ArrayList<String>();
		
		// base case 2
		/* not using memorization matrix here, both constant time, not sure
		 * which has a larger overhead. In either case should not be a big
		 * deal
		 */
		if (start == end - 1) {
			if (in.charAt(start) == t.charAt(start))
				return new ArrayList<String>();
			else {
				ArrayList<String> answer = new ArrayList<>();
				answer.add(makeSubStr(in.charAt(start), start, t.charAt(start)));
				return answer;
			}
		}
		
		ArrayList<String> sumArr;
		
		/* recursive case 1: try all possible flips including first letter */
		ArrayList<String> curMinFlipArr = null;
		ArrayList<String> a2;
		ArrayList<String> a3;
		for (int i = start + 2; i <= inLength; i++) {
				a2 = getNFSeq(in, t, start, i);
				a3 = getSeq(in, t, i, inLength);
				sumArr = new ArrayList<>();
				sumArr.addAll(a2);
				sumArr.addAll(a3);
				sumArr.add(makeFlipStr(start, i - 1));
				if (curMinFlipArr == null)
					curMinFlipArr = sumArr;
				else
					curMinFlipArr = sumArr.size() < curMinFlipArr.size() ? 
							sumArr : curMinFlipArr;
		}
		// recursive case 2, first letter does not belong to flip
		ArrayList<String> curMinSubArr = null;
		ArrayList<String> arr6;
		arr6 = getSeq(in, t, start + 1, inLength);
		sumArr = new ArrayList<>();
		if (in.charAt(start) != t.charAt(start)) {
			sumArr.add(makeSubStr(in.charAt(start), start, t.charAt(start)));
		}
		sumArr.addAll(arr6);
		curMinSubArr = sumArr;
		
		// update memorization table, than return
		ArrayList<String> answer = curMinFlipArr.size() < curMinSubArr.size() ?
				curMinFlipArr : curMinSubArr;
		mem.put(key, answer);
		return answer;
	}
	
	private String makeFlipStr(int start, int end) {
		return "flip(" + start + ", " + end + ")";
	}
	
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
		int inLength = in.length();
		int revInd;
		// check in reverse order due to the flip
		for (int i = start; i < end; i++) {
			revInd = inLength - (i - start) - 1;
			if (in.charAt(i) != t.charAt(revInd))
				seq.add(makeSubStr(in.charAt(i), revInd, 
						t.charAt(revInd)));
		}
		mem.put(key, seq);
		return seq;
	}
	
	public void reverselyPrintArray(ArrayList<String> arr) {
		for (int i = arr.size() - 1; i > -1; i--)
			System.out.println(arr.get(i));
	}
	
	public static void main(String[] args) {
		TransformationSequence t = new TransformationSequence();
		String source;
		String target;
		//source = "timeflieslikeanarrow";
		//target = "tfemiliilzejeworrbna";
		source = "abcd";
		target = "bcba";
		
		String longSource = RandomStringGenerator.generate(500);
		String longTarget = RandomStringGenerator.mutate(longSource);
		System.err.println(longTarget);
		System.err.println(longSource);
		
		System.err.println("Finished generating random strings.");
		long startTime = System.nanoTime();
		//ArrayList<String> result = t.getTranSeqWrapper(longSource, longTarget);
		ArrayList<String> result = t.getTranSeqWrapper(source, target);
		long endTime = System.nanoTime();
		System.err.println("total time = " + (endTime - startTime) / 1000000000 + " seconds.");
		t.reverselyPrintArray(result);
	}
}
