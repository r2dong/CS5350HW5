package main;

import java.util.Iterator;

public class MyLinkedList  {
	
	Node head;
	Node tail;
	int size;
	
	MyLinkedList() {
		size = 0;
	}
	
	void add(String data) {
		if (head == null) {
			head = new Node(data);
			tail = head;
			size = 1;
		}
		else {
			tail.next = new Node(data);
			tail.next.pre = tail;
			tail = tail.next;
			size++;
		}
	}
	
	void concate(final MyLinkedList other) {
		if (other.size == 0)
			return;
		else if (head == null) {
			head = new Node(other.head);
			tail = new Node(other.tail);
			size = other.size;
		}
		else {
			tail.next = new Node(other.head);
			tail.next.pre = tail;
			tail = new Node(other.tail);
			size = size + other.size;
		}
	}
	
	Iterator<String> getForwardIterator() {
		return new MyLinkedListIterator(this);
	}
	
	Iterator<String> getBackwardIterator() {
		return new MyLinkedListBackIterator(this);
	}
}

class Node {
	
	final String data;
	Node next;
	Node pre;
	
	Node(String data) {
		this.data = data;
	}
	
	// copy constructor
	Node(Node toCopy) {
		data = toCopy.data;
		next = toCopy.next;
		pre = toCopy.pre;
	}
}

class MyLinkedListIterator implements Iterator<String> {
	
	private Node current;
	
	MyLinkedListIterator(MyLinkedList list) {
		current = list.head;
	}
	
	@Override
	public boolean hasNext() {
		return current != null;
	}

	@Override
	public String next() {
		Node temp = current;
		current = current.next;
		return temp.data;
	}
	
}

class MyLinkedListBackIterator implements Iterator<String> {
	
	private Node current;
	
	MyLinkedListBackIterator(MyLinkedList list) {
		current = list.tail;
	}
	
	@Override
	public boolean hasNext() {
		return current != null;
	}

	@Override
	public String next() {
		Node temp = current;
		current = current.pre;
		return temp.data;
	}
	
}

