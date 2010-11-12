package com.github.mithunder.worklist;

import java.util.List;
import java.util.ListIterator;

public class ReverseListIterator<E> implements ListIterator<E> {

	private ListIterator<E> iterator;

	public ReverseListIterator(List<E> e) {
		iterator = e.listIterator();
	}

	@Override
	public void add(E e) {
		iterator.add(e);
		iterator.next();
	}

	@Override
	public boolean hasNext() {
		return iterator.hasPrevious();
	}

	@Override
	public boolean hasPrevious() {
		return iterator.hasNext();
	}

	@Override
	public E next() {
		return iterator.previous();
	}

	@Override
	public int nextIndex() {
		return iterator.previousIndex();
	}

	@Override
	public E previous() {
		return iterator.next();
	}

	@Override
	public int previousIndex() {
		return iterator.nextIndex();
	}

	@Override
	public void remove() {
		iterator.remove();
	}

	@Override
	public void set(E e) {
		iterator.set(e);
	}

}
