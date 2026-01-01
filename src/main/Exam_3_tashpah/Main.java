public class Main {

	public static void main(String[] args) {
		testDel();
  }
  
  public static boolean del(Queue<Integer> q, int x) {
		Queue<Integer> d = new Queue<Integer>();
		q.insert(null);
	  	boolean found = false;
		while (q.head() != null) {
			int b = 0;
			int f = 1;
			while (q.head() != -9) {
				int a = q.remove();
				d.insert(a);
				b += f * a;
				f = f * 10;
			}
			System.out.println(b);
			d.insert(q.remove());
			if (b == x) {
				found = true;
				while (!d.isEmpty())
					d.remove();
			} else {
				while (!d.isEmpty())
					q.insert(d.remove());				
			}
		}
		q.remove(); // remove null
		return found;
	}

	public static void testDel() {
		Queue<Integer> q = new Queue<Integer>();
		int[] a = { 8, 7, -9, 5, 4, 1, -9, 4, 3, -9 };
		for (int i = 0; i < a.length; i++) {
			q.insert(a[i]);
		}
		System.out.println(q);
		System.out.println(del(q, 145));
		System.out.println(q);
	}
}
