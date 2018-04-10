package main;

public class Triple {
	
	final int left;
	final int right;
	final boolean flipped;
	
	public Triple(int left, int right, boolean flipped) {
		this.left = left;
		this.right = right;
		this.flipped = flipped;
	}
	
	public boolean equals(Object other) {
		if (!(other instanceof Triple))
			return false;
		else {
			Triple p = (Triple)other;
			return p.left == left && p.right == right && p.flipped == flipped;
		}
	}
	
	public int hashCode() {
		// divide by 2 in case of overflow
		return left + right;
	}
}
