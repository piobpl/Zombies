package utility;

public class Pair<FirstType, SecondType> {
	// exception - writable public variables
	public FirstType first;
	public SecondType second;

	public Pair() {

	}

	public Pair(FirstType first, SecondType second) {
		this.first = first;
		this.second = second;
	}

	@Override
	public boolean equals(Object obj) {
		if(getClass() != obj.getClass())
			return false;
		Pair<?, ?> p = (Pair<?, ?>) obj;
		return first.equals(p.first) && second.equals(p.second);
	}
}
