package main;

import java.util.ArrayList;
import java.util.HashMap;

public class TransformationSequence {
	
	HashMap<Triple, ArrayList<String>> mem;
	
	// handles initializations for the algorithm
	public ArrayList<String> getTranSeqWrapper(String in, String t) {
		mem = new HashMap<>();
		return getSeq(in, t, 0);
	}
	
	// the actual algorithm
	private ArrayList<String> getSeq(String in, String t, int start) {
		
		
		
		Triple key = new Triple(in, t, false);
		//System.err.println(in);
		//System.err.println(t);
		//System.err.println(start);
		
		// return answer if subproblem already solved
		if (mem.containsKey(key))
			return mem.get(key);
		
		int inLength = in.length();
		
		// base case 1
		if (inLength == 0)
			return new ArrayList<String>();
		
		// base case 2
		/* not using memorization matrix here, both constant time, not sure
		 * which has a larger overhead. In either case should not be a big
		 * deal
		 */
		if (inLength == 1) {
			if (in.charAt(0) == t.charAt(0))
				return new ArrayList<String>();
			else {
				ArrayList<String> answer = new ArrayList<>();
				answer.add(makeSubStr(in.charAt(0), start, t.charAt(0)));
				return answer;
			}
		}
		
		
		ArrayList<String> sumArr;
		
		/* recursive case 1: try all possible flips including first letter */
		ArrayList<String> curMinFlipArr = null;
		ArrayList<String> a2;
		ArrayList<String> a3;
		for (int i = 2; i <= inLength; i++) {
				a2 = getNFSeq(in.substring(0, i), t.substring(0, i), start);
				a3 = getSeq(in.substring(i, inLength), 
						t.substring(i, inLength), start + i);
				sumArr = new ArrayList<>();
				sumArr.addAll(a2);
				sumArr.addAll(a3);
				sumArr.add(makeFlipStr(start, i + start - 1));
				if (curMinFlipArr == null)
					curMinFlipArr = sumArr;
				else
					curMinFlipArr = sumArr.size() < curMinFlipArr.size() ? 
							sumArr : curMinFlipArr;
		}
		// recursive case 2, first letter does not belong to flip
		ArrayList<String> curMinSubArr = null;
		ArrayList<String> arr6;
		arr6 = getSeq(in.substring(1, inLength), 
				t.substring(1, inLength), start + 1);
		sumArr = new ArrayList<>();
		if (in.charAt(0) != t.charAt(0)) {
			sumArr.add(makeSubStr(in.charAt(0), 0 + start, t.charAt(0)));
		}
		sumArr.addAll(arr6);
		curMinSubArr = sumArr;
		// in case curMinSubArr not initialized for already correct array
		if (curMinSubArr == null)
			curMinSubArr = new ArrayList<String>();
		
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
	private ArrayList<String> getNFSeq(String in, final String t, int start) {
		
		Triple key = new Triple(in, t, true);
		
		// return answer if sub-problem already solved
		if (mem.containsKey(key))
			return mem.get(key);
		
		ArrayList<String> seq = new ArrayList<String>();
		int inLength = in.length();
		int revInd;
		// check in reverse order due to the flip
		for (int i = 0; i < inLength; i++) {
			revInd = inLength - 1 - i;
			if (in.charAt(i) != t.charAt(revInd))
				seq.add(makeSubStr(in.charAt(i), revInd + start, 
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
		//source = "ab";
		//target = "ac";
		
		String longSource = RandomStringGenerator.generate(500);
		String longTarget = RandomStringGenerator.mutate(longSource);
		System.err.println(longTarget);
		System.err.println(longSource);
		
		System.err.println("Finished generating random strings.");
		long startTime = System.nanoTime();
		ArrayList<String> result = t.getTranSeqWrapper(longSource, longTarget);
		long endTime = System.nanoTime();
		System.err.println("total time = " + (endTime - startTime) / 1000000000 + " seconds.");
		t.reverselyPrintArray(result);
	}
}
