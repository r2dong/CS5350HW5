package main;

import java.util.HashMap;
import java.util.Iterator;

public class TransformationSequence {
	
	HashMap<Triple, MyLinkedList> mem;
	
	// handles initializations for the algorithm
	public MyLinkedList getTranSeqWrapper(String in, String t) {
		mem = new HashMap<>();
		return getSeq(in, t, 0);
	}
	
	// the actual algorithm
	private MyLinkedList getSeq(String in, String t, int start) {
		
		Triple key = new Triple(in, t, false);
		
		// return answer if subproblem already solved
		if (mem.containsKey(key))
			return mem.get(key);
		
		int inLength = in.length();
		
		// base case 1
		if (inLength == 0)
			return new MyLinkedList();
		
		// base case 2
		/* not using memorization matrix here, both constant time, not sure
		 * which has a larger overhead. In either case should not be a big
		 * deal
		 */
		if (inLength == 1) {
			if (in.charAt(0) == t.charAt(0))
				return new MyLinkedList();
			else {
				MyLinkedList answer = new MyLinkedList();
				answer.add(makeSubStr(in.charAt(0), start, t.charAt(0)));
				return answer;
			}
		}
		
		
		MyLinkedList sumArr;
		
		// recursive case 1, try all possible ways of splitting
		MyLinkedList curMinFlipArr = null;
		MyLinkedList a1;
		MyLinkedList a2;
		MyLinkedList a3;
		for (int i = 0; i <= inLength; i++)
			for (int j = i + 2; j <= inLength; j++) {
				a1 = getSeq(in.substring(0, i), t.substring(0, i), start);
				a2 = getNFSeq(in.substring(i, j), t.substring(i, j), start + i);
				a3 = getSeq(in.substring(j, inLength), 
						t.substring(j, inLength), start + j);
				sumArr = new MyLinkedList();
				sumArr.concate(a1);
				sumArr.concate(a2);
				sumArr.concate(a3);
				sumArr.add(makeFlipStr(i + start, j + start - 1));
				if (curMinFlipArr == null)
					curMinFlipArr = sumArr;
				else
					curMinFlipArr = sumArr.size < curMinFlipArr.size ? 
							sumArr : curMinFlipArr;
			}
		// recursive case 2, try all possible ways of substitution
		MyLinkedList curMinSubArr = null;
		MyLinkedList arr5;
		MyLinkedList arr6;
		for (int i = 0; i < inLength; i++) {
			if (in.charAt(i) != t.charAt(i)) { 
				arr5 = getSeq(in.substring(0, i), t.substring(0, i), start);
				arr6 = getSeq(in.substring(i + 1, inLength), 
						t.substring(i + 1, inLength), start + i + 1);
				sumArr = new MyLinkedList();
				sumArr.concate(arr5);
				sumArr.concate(arr6);
				sumArr.add(makeSubStr(in.charAt(i), i + start, t.charAt(i)));
				if (curMinSubArr == null)
					curMinSubArr = sumArr;
				else
					curMinSubArr = sumArr.size < curMinSubArr.size ? 
							sumArr : curMinSubArr;
			}
		}
		// in case curMinSubArr not initialized for already correct array
		if (curMinSubArr == null)
			curMinSubArr = new MyLinkedList();
		
		// update memorization table, than return
		MyLinkedList answer = curMinFlipArr.size < curMinSubArr.size ?
				curMinFlipArr : curMinSubArr;
		mem.put(key, answer);
		return answer;
	}
	
	private String makeFlipStr(int start, int end) {
		return "flip(" + start + ", " + end + ")";
	}
	
	private String makeSubStr(char source, int index, char t) {
		return "substitute letter \"" + source + "\" at index " + index + 
				" with \"" + t + "\"";
	}
	
	// calculate transformation path when a subarray can no loger be flipped
	private MyLinkedList getNFSeq(String in, final String t, int start) {
		
		Triple key = new Triple(in, t, true);
		
		// return answer if sub-problem already solved
		if (mem.containsKey(key))
			return mem.get(key);
		
		MyLinkedList seq = new MyLinkedList();
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
	
	public void reverselyPrintArray(MyLinkedList arr) {
		Iterator<String> iterator = arr.getBackwardIterator();
		while (iterator.hasNext()) {
			System.out.println(iterator.next());
		}
	}
	
	public static void main(String[] args) {
		TransformationSequence t = new TransformationSequence();
		String source;
		String target;
		source = "lieslikeanarrow";
		target = "liilzejeworrbna";
		
		t.reverselyPrintArray(t.getTranSeqWrapper(source, target));
	}
}
