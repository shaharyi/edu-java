
public class Main {

	public static int findMax(Queue<Integer> q) {
		int max = q.head();
		q.insert(null);
		while (q.head() != null) {
			int a = q.remove();
			if (a > max)
				max = a;
		}
		q.remove();  //remove null
		return max;
	}
	
	public static int countDigits(int x) {
		int count = 0;
		while (x != 0) {
			x = x / 10;
			count++;
		}
		return count;
	}

  // get digit number j from right of x
	public static int getDigit(int x, int j) {
		for (int i = 0; i < j; i++) {
			x = x / 10;
		}
		return x % 10;
	}
	
	public static void radixSort(Queue<Integer> q){
		Queue<Integer>[] a = new Queue[10];
		for (int i = 0; i < a.length; i++) {
		    a[i] = new Queue<Integer>();
		}
		
		int max = findMax(q);
		int len = countDigits(max);
		
		for (int k = 0; k < len; k++) {
			while (!q.isEmpty()) {
				int x = q.remove();
				int d = getDigit(x, k);
				a[d].insert(x);
			}
			
			for (int i = 0; i < a.length; i++) {
				q.insert(a[i].remove());
			}
		}
	}
	
	public static void main(String[] args) {
		Queue<Integer> q = new Queue<Integer>();
		q.insert(803);
		q.insert(7);
		q.insert(21);
		q.insert(4);
		System.out.println(q);
		
		System.out.println("max = " + findMax(q));
		System.out.println("countDigits(12345) = " + countDigits(12345));
		System.out.println("digit number 0 from right of 84: " + getDigit(84, 0));
    sort(q);
		System.out.println(q);
	}

}
