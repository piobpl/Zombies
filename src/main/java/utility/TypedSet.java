package utility;

import java.util.HashSet;
import java.util.Iterator;

/**
 * Typowany set, mozna dodawac elementy z typem i sprawdzac czy istnieje element
 * o okreslonym typie
 *
 * @author michal, piob
 *
 */

public class TypedSet<T extends Typed<S>, S> implements Iterable<T> {

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
		for(S needed : types)
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

	@Override
	public Iterator<T> iterator() {
		return new Iterator<T>() {

			Iterator<T> iterator = set.iterator();

			@Override
			public boolean hasNext() {
				return iterator.hasNext();
			}

			@Override
			public T next() {
				return iterator.next();
			}

			@Override
			public void remove() {
				iterator.remove();
			}

		};
	}

	public boolean isEmpty() {
		return set.isEmpty();
	}

	public void clear() {
		set.clear();
	}

}
