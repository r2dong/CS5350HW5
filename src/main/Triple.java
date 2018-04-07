package main;

public class Triple {
	
	final String left;
	final String right;
	final boolean flipped;
	
	public Triple(String left, String right, boolean flipped) {
		this.left = left;
		this.right = right;
		this.flipped = flipped;
	}
	
	public boolean equals(Object other) {
		if (!(other instanceof Triple))
			return false;
		else {
			Triple p = (Triple)other;
			return p.left.equals(left) && p.right.equals(right) && p.flipped == flipped;
		}
	}
	
	public int hashCode() {
		// divide by 2 in case of overflow
		return left.hashCode() / 2 + right.hashCode() / 2;
	}
}
