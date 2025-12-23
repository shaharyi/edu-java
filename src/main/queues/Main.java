public class Main {
	public static void main(String[] args) {
		Integer[] a = { 2, 2, 5, 6, 5, 5, 8, 8 };
		Queue<Integer> q1 = buildQueue(a);
		System.out.println(q1);
		System.out.print(ex5(q1, 6));
	}

	public static <T> Queue<T> buildQueue(T[] a) {
		Queue<T> q = new Queue<T>();
		for (int i = 0; i < a.length; i++) {
			q.insert(a[i]);
		}
		return q;
	}

	public static <T> Queue<T> ex1(Queue<T> x) {
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

	public static double ex2(Queue<Integer> x) {
		int c = 0;
		int s = 0;
		Queue<Integer> copy = ex1(x);
		while (!copy.isEmpty()) {
			s = s + copy.remove();
			c = c + 1;
		}
		return (double) s / c;
	}

	public static Integer ex3(Queue<Integer> x, int a) {
		int c = 0;
		Queue<Integer> copy = ex1(x);
		while (!copy.isEmpty()) {
			if (a % x.remove()  == 0) {
				c = c + 1;
			}
		}
		return c;
	}

	public static boolean ex4(Queue<Integer> a, Queue<Integer> b) {
		Queue<Integer> copyA = ex1(a);
		Queue<Integer> copyB = ex1(b);
		while (!copyA.isEmpty()) {
			if (divides(copyB, copyA.remove()) == false) {
				return false;
			}
		}
		return true;
	}

	public static boolean divides(Queue<Integer> a, int x) {
		Queue<Integer> copy = ex1(a);
		while (!copy.isEmpty()) {
			if (copy.remove() % x == 0) {
				return true;
			}
		}
		return false;
	}

	public static <T> boolean ex5(Queue<T> a, T x) {
		Queue<T> copy = ex1(a);
		T last = null;
		while (!copy.isEmpty()) {
			T w = copy.remove();
			if (w.equals(last) && w.equals(x)) {
				return true;
			}
			last = w;
		}
		return false;
	}
}
