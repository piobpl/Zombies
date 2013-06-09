package utility;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Typowany set, mozna dodawac elementy z typem i sprawdzac czy istnieje element
 * o okreslonym typie
 *
 * @author michal, piob
 *
 */

public class TypedSet<T extends Typed<S>, S> implements Serializable {

	private static final long serialVersionUID = -433195257299471873L;
	private HashSet<T> set = new HashSet<T>();

	public void add(T element) {
		set.add(element);
	}

	public boolean contains(S type) {
		for (T element : set)
			if (element.getType() == type)
				return true;
		return false;
	}

	@SuppressWarnings("unchecked")
	public boolean containsAny(S... types) {
		for (S needed : types)
			for (T element : set)
				if (element.getType() == needed)
					return true;
		return false;
	}

	public void remove(S type) {
		T toRemove = null;
		for (T element : set)
			if (element.getType() == type) {
				toRemove = element;
				break;
			}
		if (toRemove != null) {
			set.remove(toRemove);
		}
	}

	public void remove(T element) {
		set.remove(element);
	}

	public List<T> asList(){
		List<T> list = new ArrayList<>();
		for(T elem : set)
			list.add(elem);
		return list;
	}

	public boolean isEmpty() {
		return set.isEmpty();
	}

	public void clear() {
		set.clear();
	}

}
