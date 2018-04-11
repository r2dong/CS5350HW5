package main;

public class Triple {
	
	final int left;
	final int right;
	
	public Triple(int left, int right) {
		this.left = left;
		this.right = right;
	}
	
	public boolean equals(Object other) {
		if (!(other instanceof Triple))
			return false;
		else {
			Triple p = (Triple)other;
			return p.left == left && p.right == right;
		}
	}
	
	public int hashCode() {
		// divide by 2 in case of overflow
		return left + right;
	}
}
