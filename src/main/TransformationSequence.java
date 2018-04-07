package main;

import java.util.ArrayList;
import java.util.HashMap;

public class TransformationSequence {
	
	HashMap<Triple, ArrayList<String>> mem;
	
	// handles initializations for the algorithm
	public ArrayList<String> getTranSeqWrapper(String in, final String target, int start) {
		mem = new HashMap<>();
		return getTranSeq(in, target, start, false);
	}
	
	public ArrayList<String> getTranSeq(String in, final String target, int start, boolean flipped) {
		
		Triple key = new Triple(in, target, flipped);
		
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
			if (in.charAt(0) == target.charAt(0))
				return new ArrayList<String>();
			else {
				ArrayList<String> toReturn = new ArrayList<>();
				toReturn.add("substitute letter \"" + in.charAt(0) + "\" at index " + start + " with \"" + target.charAt(0) + "\"");
				return toReturn;
			}
		}
		
		// if not flipped
		if (!flipped) {
			// recursive case 1
			ArrayList<String> curMinFlipArr = null;
			ArrayList<String> arr1;
			ArrayList<String> arr2;
			ArrayList<String> arr3;
			for (int i = 0; i <= inLength; i++)
				for (int j = i + 2; j <= inLength; j++) {
					arr1 = getTranSeq(new String(in.substring(0, i)), target.substring(0, i), start, false);
					// arr2 = getNoFlipSeq(reverse(in.substring(i, j)), target.substring(i, j), start + i, true);
					arr2 = getTranSeq(reverse(in.substring(i, j)), target.substring(i, j), start + i, true);
					arr3 = getTranSeq(new String(in.substring(j, inLength)), target.substring(j, inLength), start + j, false);
					arr1.addAll(arr2); // consider re-implement with self-made linked list
					arr1.addAll(arr3);
					arr1.add("flip(" + (i + start) + ", " + (j + start - 1) + ")");
					if (curMinFlipArr == null)
						curMinFlipArr = arr1;
					else
						curMinFlipArr = arr1.size() < curMinFlipArr.size() ? arr1 : curMinFlipArr;
				}
			// recursive case 2
			ArrayList<String> curMinSubArr = null;
			ArrayList<String> arr5;
			ArrayList<String> arr6;
			for (int i = 0; i < inLength; i++) {
				if (in.charAt(i) != target.charAt(i)) { 
					arr5 = getTranSeq(new String(in.substring(0, i)), target.substring(0, i), start, false);
					arr6 = getTranSeq(new String(in.substring(i + 1, inLength)), target.substring(i + 1, inLength), start + i + 1, false);
					arr5.addAll(arr6);
					arr5.add("substitute letter \"" + in.charAt(i) + "\" at index " + (i + start) + " with \"" + target.charAt(i) + "\"");
					if (curMinSubArr == null)
						curMinSubArr = arr5;
					else
						curMinSubArr = arr5.size() < curMinSubArr.size() ? arr5 : curMinSubArr;
				}
			}
			// handle edge case: curMinSubArr not initialized due to already correct array
			if (curMinSubArr == null)
				curMinSubArr = new ArrayList<String>();
			
			// update memorization table, than return
			ArrayList<String> answer = curMinFlipArr.size() < curMinSubArr.size() ? curMinFlipArr : curMinSubArr;
			mem.put(key, answer);
			return answer;
		}
		// if flipped
		else {
			
			ArrayList<String> seq = new ArrayList<String>();
			// int reversedIndex;
			// check in reverse order due to the flip
			for (int i = 0; i < inLength; i++) {
				if (in.charAt(i) != target.charAt(i))
					seq.add("substitute letter \"" + in.charAt(i) + "\" at index " + (i + start) + " with \"" + target.charAt(i) + "\"");
			}
			mem.put(key, seq);
			return seq;
		}
	}
	
	private ArrayList<String> getNoFlipSeq(String in, final String target, int start) {
		
		Triple key = new Triple(in, target, true);
		
		// return answer if sub-problem already solved
		if (mem.containsKey(key))
			return mem.get(key);
		
		ArrayList<String> seq = new ArrayList<String>();
		int inLength = in.length();
		// int reversedIndex;
		// check in reverse order due to the flip
		for (int i = 0; i < inLength; i++) {
			if (in.charAt(i) != target.charAt(i))
				seq.add("substitute letter \"" + in.charAt(i) + "\" at index " + (i + start) + " with \"" + target.charAt(i) + "\"");
		}
		mem.put(key, seq);
		return seq;
	}
	
	private String reverse(String in) {
		String toReturn = "";
		int inLength = in.length();
		for (int i = 0; i < inLength; i++)
			toReturn += in.charAt(inLength - 1 - i);
		return toReturn;
	}
	
	public void reverselyPrintArray(ArrayList<String> arr) {
		for (int i = arr.size() - 1; i > -1; i--)
			System.out.println(arr.get(i));
	}
	
	public static void main(String[] args) {
		TransformationSequence t = new TransformationSequence();
		String source;
		String target;
		source = "timeflieslikeanarrow";
		target = "tfemiliilzejeworrbna";
		//source = "abc";
		//target = "def";
		// source = "ab";
		// target = "de";
		//source = "abcf";
		//target = "baec";
		
		t.reverselyPrintArray(t.getTranSeqWrapper(source, target, 0));
	}
}
