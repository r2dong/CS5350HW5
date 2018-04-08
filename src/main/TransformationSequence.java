package main;

import java.util.ArrayList;
import java.util.HashMap;

public class TransformationSequence {
	
	HashMap<Triple, ArrayList<String>> mem;
	
	// handles initializations for the algorithm
	public ArrayList<String> getTranSeqWrapper(final String in, final String target, int start) {
		mem = new HashMap<>();
		return getTranSeq(in, target, start);
	}
	
	public ArrayList<String> getTranSeq(String in, final String target, int start) {
		
		Triple key = new Triple(in, target, false);
		
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
		
		
		ArrayList<String> sumArr;
		
		// recursive case 1
		ArrayList<String> curMinFlipArr = null;
		ArrayList<String> arr1;
		ArrayList<String> arr2;
		ArrayList<String> arr3;
		for (int i = 0; i <= inLength; i++)
			for (int j = i + 2; j <= inLength; j++) {				
				arr1 = getTranSeq(new String(in.substring(0, i)), target.substring(0, i), start);
				arr2 = getNoFlipSeq(new String(in.substring(i, j)), target.substring(i, j), start + i);
				arr3 = getTranSeq(new String(in.substring(j, inLength)), target.substring(j, inLength), start + j);
				sumArr = new ArrayList<>();
				sumArr.addAll(arr1);
				sumArr.addAll(arr2);
				sumArr.addAll(arr3);
				sumArr.add("flip(" + (i + start) + ", " + (j + start - 1) + ")");
				if (curMinFlipArr == null)
					curMinFlipArr = sumArr;
				else
					curMinFlipArr = sumArr.size() < curMinFlipArr.size() ? sumArr : curMinFlipArr;
			}
		// recursive case 2
		ArrayList<String> curMinSubArr = null;
		ArrayList<String> arr5;
		ArrayList<String> arr6;
		for (int i = 0; i < inLength; i++) {
			if (in.charAt(i) != target.charAt(i)) { 
				arr5 = getTranSeq(new String(in.substring(0, i)), target.substring(0, i), start);
				arr6 = getTranSeq(new String(in.substring(i + 1, inLength)), target.substring(i + 1, inLength), start + i + 1);
				sumArr = new ArrayList<>();
				sumArr.addAll(arr5);
				sumArr.addAll(arr6);
				sumArr.add("substitute letter \"" + in.charAt(i) + "\" at index " + (i + start) + " with \"" + target.charAt(i) + "\"");
				if (curMinSubArr == null)
					curMinSubArr = sumArr;
				else
					curMinSubArr = sumArr.size() < curMinSubArr.size() ? sumArr : curMinSubArr;
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
	
	private ArrayList<String> getNoFlipSeq(String in, final String target, int start) {
		
		Triple key = new Triple(in, target, true);
		
		// return answer if sub-problem already solved
		if (mem.containsKey(key))
			return mem.get(key);
		
		ArrayList<String> seq = new ArrayList<String>();
		int inLength = in.length();
		int reversedIndex;
		// check in reverse order due to the flip
		for (int i = 0; i < inLength; i++) {
			reversedIndex = inLength - 1 - i;
			if (in.charAt(i) != target.charAt(reversedIndex))
				seq.add("substitute letter \"" + in.charAt(i) + "\" at index " + (reversedIndex + start) + " with \"" + target.charAt(reversedIndex) + "\"");
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
		//source = "timefliezlijeanbrrow";
		//target = "timeflieslikeanarrow";
		//source = "lijb";
		//target = "lika";
		//source = "abc";
		//target = "def";
		// source = "ab";
		// target = "de";
		//source = "abcf";
		//target = "baec";
		//source = "abcdy";
		//target = "xbzdf";
		//source = "lijfdsafdsafsd";
		//target = "lijfdsafdsafsd";
		//source = "li";
		//target = "li";
		source = "timeflieslikeanarrow";
		target = "tfemiliilzejeworrbna";
		
		t.reverselyPrintArray(t.getTranSeqWrapper(source, target, 0));
	}
}
