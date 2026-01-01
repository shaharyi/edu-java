public class Main {
  //////
  // QA Program
  //////
	public static void main(String[] args) {
		Integer[] b = { 50, 25, 75, 10, 90, 88, 12, 50 };
		Queue<Integer> q2 = buildQueue(b);
		System.out.println(to100(q2));
	}
  
	public static <T> Queue<T> buildQueue(T[] a) {
		Queue<T> q = new Queue<T>();
		for (int i = 0; i < a.length; i++) {
			q.insert(a[i]);
		}
		return q;
	}

  
  ////////
  // Q1 - does every item has a matching item that adds up to 100
  ////////
  public static boolean to100(Queue<Integer> q) {
    Queue<Integer> copy = copyQ(q);
    while (!copy.isEmpty()) {
      int a = copy.remove();
      int need = 1;
      if (a == 50)
        need = 2;
      if (countX(q, 100 - a) < need)
        return false;
    }
    return true;
  }
  
  public static <T> Queue<T> copyQ(Queue<T> x) {
		Queue<T> copy = new Queue<T>();
		Queue<T> temp = new Queue<T>();
		while (!x.isEmpty()) {
			T item = x.remove();
			copy.insert(item);
			temp.insert(item);
		}
		while (!temp.isEmpty()) {
			x.insert(temp.remove());
		}
		return copy;
  }

  public static int countX(Queue<Integer> q, int x) {
		Queue<Integer> copy = copyQ(q);
		int c = 0;
		while (!copy.isEmpty()) {
			if (copy.remove() == x)
				c++;
		}
		return c;
  }
  
  //////
  // Q2 - Move repetitions to another list and return it
  //////
  public static Node<Integer> moveRep(Node<Integer> p) {
		Node<Integer> h = new Node<>(-1);
		Node<Integer> a = h;
		while (p.hasNext()) {
			if (p.getNext().getValue() == p.getValue()) {
				a.setNext(p.getNext());
				a = a.getNext();
				p.setNext(p.getNext().getNext());
			} else
				p = p.getNext();
		}
		return h.getNext();
  }
	
  //////
  // Q3
  //////
  a) break to 4 languages, write each language, draw automata for each, show how they combine.
  b)
	L1 - non regular due to unbound counting dependancy.
	L2 - regular, since it is (ab)^2n
	L3 - regular, since it is (ab)^2n+1
	L4 - regular, since it is finite language (4 words actually).
}
