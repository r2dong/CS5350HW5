package main;

// a type of key used during memoization
public class Duple {
	
	final int start;
	
	public Duple(int start) {
		this.start = start;
	}
	
	public boolean equals(Object other) {
		if (!(other instanceof Duple))
			return false;
		else {
			Duple p = (Duple)other;
			return start == p.start;
		}
	}
	
	public int hashCode() {
		// divide by 2 in case of overflow
		return start;
	}
}
