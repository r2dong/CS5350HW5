package main;

import java.util.ArrayList;

public class TransformationSequence {
	
	public ArrayList<String> getTranSeq(String in, final String target, boolean isFlipped) {
		
		// recursive cases 1 and 2
		if (!isFlipped) {
			// recursive case 1
			int inLength = in.length();
			for (int i = 0; i < inLength; i++)
				for (int j = i + 1; j < inLength; j++) {
					ArrayList<String> arr1 = getTranSeq(new String(in.substring(0, i)), target.substring(0, i), false);
					ArrayList<String> arr2 = getTranSeq(new String(in.substring(i, j + 1)), target.substring(i, j + 1), true);
					ArrayList<String> arr3 = getTranSeq(new String(in.substring(j + 1, inLength)), target.substring(j + 1, inLength), false);
					ArrayList<String> minArr = getMinArray(arr1, arr2, arr3);
					// we can directly compare references here
					if (minArr == arr1)
						minArr.add("flip(0, " + i + ")");
					else if (minArr == arr2)
						minArr.add("flip(" + i + ", " + j + 1 + ")");
					else
						minArr.add("flip(" + j + 1 + ", " + )
				}
		}
		
		

		return null;
	}
	
	private ArrayList<String> getMinArray(ArrayList<String> ... arrs) {
		return null;
	}
	
	
	private void flip(int i, int j, String toFlip) {
		//TODO implement this
	}
}
