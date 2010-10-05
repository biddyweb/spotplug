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
		if (this.getList().size()==0)
		{
			return true;
		}else{
			return false;
		}
		
	}

	@Override
	public boolean contains(Object o) {
		// TODO Auto-generated method stub
		return this.getList().contains(o);
	}

	@Override
	public Iterator iterator() {
		// TODO Auto-generated method stub
		return this.getList().iterator();
	}

	@Override
	public Object[] toArray() {
		// TODO Auto-generated method stub
		return this.getList().toArray();
	}

	@Override
	public Object[] toArray(Object[] a) {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		return this.getList().containsAll(c);
	}

	@Override
	public boolean addAll(Collection c) {
		// TODO Auto-generated method stub
		return this.getList().addAll(c);
	}

	@Override
	public boolean addAll(int index, Collection c) {
		// TODO Auto-generated method stub
		return this.getList().addAll(index, c);
	}

	@Override
	public boolean removeAll(Collection c) {
		// TODO Auto-generated method stub
		return this.getList().removeAll(c);
	}

	@Override
	public boolean retainAll(Collection c) {
		// TODO Auto-generated method stub
		return this.getList().retainAll(c);
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		this.getList().clear();
	}

	@Override
	public Object get(int index) {
		// TODO Auto-generated method stub
		return this.getList().get(index);
	}

	@Override
	public Object set(int index, Object element) {
		// TODO Auto-generated method stub
		return this.getList().set(index, (User) element);
	}

	@Override
	public void add(int index, Object element) {
		// TODO Auto-generated method stub
		this.getList().add(index, (User) element);
	}

	@Override
	public Object remove(int index) {
		// TODO Auto-generated method stub
		return this.getList().remove(index);
	}

	@Override
	public int indexOf(Object o) {
		// TODO Auto-generated method stub
		return this.getList().indexOf(o);
	}

	@Override
	public int lastIndexOf(Object o) {
		// TODO Auto-generated method stub
		return this.getList().lastIndexOf(o);
	}

	@Override
	public ListIterator listIterator() {
		// TODO Auto-generated method stub
		return this.getList().listIterator();
	}

	@Override
	public ListIterator listIterator(int index) {
		// TODO Auto-generated method stub
		return this.getList().listIterator(index);
	}

	@Override
	public List subList(int fromIndex, int toIndex) {
		// TODO Auto-generated method stub
		return this.getList().subList(fromIndex, toIndex);
	}

	public void setList(ArrayList<User> list) {
		this.list = list;
	}

	public ArrayList<User> getList() {
		return list;
	}

	
}
