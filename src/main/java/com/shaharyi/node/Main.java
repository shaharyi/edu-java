package com.shaharyi.node;

public class Main {	
	
	public static void main(String[] args) {
		testIntNode();
		testStack();
	}
	
	public static Node<Integer> buildList(int[] a) {
		Node<Integer> h = new Node<Integer>(-1);
		Node<Integer> p = h;
		Node<Integer> x;
		for (int i = 0; i < a.length; i++) {
			x = new Node<Integer>(a[i]);
			p.setNext(x);
			p = p.getNext();
		}
		return h.getNext();
	}
	
	public static Node<Integer> delSingle(Node<Integer> h, int target) {
		h = new Node<Integer>(-1, h);
		Node<Integer> p = h;
		boolean done = false;
		while (!done && p.hasNext()) {
			if (p.getNext().getValue() == target) {
				p.setNext(p.getNext().getNext());
				done = true;
			} else {
				p = p.getNext();
			}
		}
		return h.getNext();
	}
	
	public static <T> boolean isIn(Queue<T> q, T x) {
		boolean r = false;
		Queue<T> save = new Queue<T>();
		while (!q.isEmpty()) {
			T a = q.remove();
			save.insert(a);
			if (a.equals(x))
				r = true;
		}
		while (!save.isEmpty()) {
			q.insert(save.remove());
		}
		return r;
	}
	
	public static <T> boolean isExist(Queue<T> q, T x) {
		boolean r = false;
		q.insert(null);
		while (q.head() != null) {
			T a = q.remove();
			q.insert(a);
			if (a.equals(x))
				r = true;
		}
		q.remove();
		return r;
	}
		
	public static void testQueue() {
		Queue<Integer> q = new Queue<Integer>();
		q.insert(7);
		q.insert(5);
		q.insert(5);
		q.insert(9);
		q.insert(7);
		q.insert(3);
		System.out.println(q);
		System.out.println("isIn(q, 9): " + isIn(q, 9));
	}

	/*
	 * c dd eee 0
	 * true
	 * 
	 * Assume at least one letter before '0'
	 */
	public static boolean growingLetter(Queue<Character> q) {
		int limit = 1, count = 1;
		char a = q.remove();
		int b = a;
		while (a == b) {
			b++;
			limit++;
			count = 1;
			a = q.remove();
			while (count < limit && a == b) {
				count++;
				a = q.remove();
			}
		}
		return (count == 1 && a == '0');
	}	
	
	public static void testStack() {
		Stack<Integer> c = new Stack<>();
		c.push(3);
		c.push(5);
		c.push(5);
		c.push(7);
		Stack<Integer> d = new Stack<>();
		d.push(1);
		d.push(3);
		d.push(6);
		d.push(9);
		d.push(10);
		Stack<Integer> r = merge2(c, d);
		System.out.println(r);
	}		
	
	public static Stack<Integer> merge(Stack<Integer> a, Stack<Integer> b) {
		Stack<Integer> c = new Stack<>();

		while (!a.isEmpty() && !b.isEmpty()) {
			if (a.top() >= b.top())
				c.push(a.pop());
			else
				c.push(b.pop());
		}
		while (!a.isEmpty()) {
			c.push(a.pop());
		}
		while (!b.isEmpty()) {
			c.push(b.pop());
		}
		return c;
	}

	public static Stack<Integer> merge2(Stack<Integer> a, Stack<Integer> b) {
		Stack<Integer> c = new Stack<>();

		while (!a.isEmpty() || !b.isEmpty()) {
			if (a.isEmpty())
				c.push(b.pop());
			else if (b.isEmpty())
				c.push(a.pop());
			else if (a.top() > b.top())
				c.push(a.pop());
			else
				c.push(b.pop());
		}
		return c;
	}

	public static IntNode build(int[] a) {
		IntNode p = new IntNode(-1);    // dummy node
		IntNode first = p;		
		IntNode x;
		for (int i = 0; i < a.length; i++) {
			x = new IntNode(a[i]);
			p.setNext(x);
			p = x;
		}
		return first.getNext();    // skip dummy node
	}

	public static boolean q8_contained(IntNode L1, IntNode L2) {
		return false;
	}
	
	public static boolean isIn(IntNode p, int x) {
		if (p == null)
			return false;
		if (p.getValue() == x)
			return true;
		return isIn(p.getNext(), x);
	}
	
	public static void testIntNode() {
		int[] b = { 1, 1, 5, 1, 2, 4, 8 };
		IntNode p = build(b);
		System.out.println(p);
	}
}
