package utility;

import java.io.Serializable;

public class Pair<FirstType, SecondType> implements Serializable {

	private static final long serialVersionUID = -7174546082463196205L;
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
		if (getClass() != obj.getClass())
			return false;
		Pair<?, ?> p = (Pair<?, ?>) obj;
		return first.equals(p.first) && second.equals(p.second);
	}

	@Override
	public String toString() {
		return "(" + first + ", " + second + ")";
	}
}
