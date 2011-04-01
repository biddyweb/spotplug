package com.plugtree.spotplug.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class UsersList implements List {

	private ArrayList<User> list;
	
	
	public UsersList(){
		this.setList(new ArrayList <User>());		
	}
	
	@Override
	public int size() {
		return this.getList().size();
	}

	@Override
	public boolean isEmpty() {
		if (this.getList().isEmpty())
		{
			return true;
		}
		
		return false;
	}

	@Override
	public boolean contains(Object o) {
		return this.getList().contains(o);
	}

	@Override
	public Iterator<User> iterator() {
		return this.getList().iterator();
	}

	@Override
	public Object[] toArray() {
		return this.getList().toArray();
	}

	@Override
	public Object[] toArray(Object[] a) {
		return this.getList().toArray(a);
	}

	@Override
	public boolean add(Object e) {
		return this.getList().add((User) e);
	}

	@Override
	public boolean remove(Object o) {
		return this.getList().remove(o);
	}

	@Override
	public boolean containsAll(Collection c) {
		return this.getList().containsAll(c);
	}

	@Override
	public boolean addAll(Collection c) {
		return this.getList().addAll(c);
	}

	@Override
	public boolean addAll(int index, Collection c) {
		return this.getList().addAll(index, c);
	}

	@Override
	public boolean removeAll(Collection c) {
		return this.getList().removeAll(c);
	}

	@Override
	public boolean retainAll(Collection c) {
		return this.getList().retainAll(c);
	}

	@Override
	public void clear() {
		this.getList().clear();
	}

	@Override
	public Object get(int index) {
		return this.getList().get(index);
	}

	@Override
	public Object set(int index, Object element) {
		return this.getList().set(index, (User) element);
	}

	@Override
	public void add(int index, Object element) {
		this.getList().add(index, (User) element);
	}

	@Override
	public Object remove(int index) {
		return this.getList().remove(index);
	}

	@Override
	public int indexOf(Object o) {
		return this.getList().indexOf(o);
	}

	@Override
	public int lastIndexOf(Object o) {
		return this.getList().lastIndexOf(o);
	}

	@Override
	public ListIterator <User>listIterator() {
		return this.getList().listIterator();
	}

	@Override
	public ListIterator<User> listIterator(int index) {
		return this.getList().listIterator(index);
	}

	@Override
	public List<User> subList(int fromIndex, int toIndex) {
		return this.getList().subList(fromIndex, toIndex);
	}

	public void setList(ArrayList<User> list) {
		this.list = list;
	}

	public ArrayList<User> getList() {
		return list;
	}

	
}
