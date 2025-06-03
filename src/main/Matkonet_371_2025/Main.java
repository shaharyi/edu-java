import java.util.Iterator;

public class Main {

	public static void main(String[] args) {

	}

	/*
	 * Q1a Does s1 contain all LETTERS from s2 Assume no repetitions in s1 nor in s2
	 */
	public static boolean isWrap(String s1, String s2) {
		for (int i = 0; i < s1.length(); i++) {
			char c = s1.charAt(i);
			if (c >= 'A' && c <= 'Z' || c >= 'a' && c <= 'z')
				if (s2.indexOf(c) == -1)
					return false;
		}
		return true;
	}

	/*
	 * Q1b
	 * 
	 * @return How many items of arrs1 wrap ALL items in arrs2
	 */

	public static int countWrap(String[] arrs1, String[] arrs2) {
		int count = 0;
		for (int i = 0; i < arrs1.length; i++) {
			boolean all = true;
			for (int j = 0; j < arrs2.length; j++) {
				all = all && isWrap(arrs1[i], arrs2[j]);
			}
			if (all)
				count++;
		}
		return count;
	}

	/*
	 * Q3 given method
	 */
	public static boolean exist(int[] arr, int num) {
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] == num)
				return true;
		}
		return false;
	}

	/*
	 * Q3 Add missing opposite numbers to given digit array
	 */
	public static int[] addOpposites(int[] a) {
		int[] b = new int[a.length];
		int pos = a.length;
		for (int i = 0; i < a.length; i++) {
			if (!exist(a, -a[i])) {
				b[pos] = -a[i];
				pos++;
			}
		}
		int[] r = new int[a.length + pos];
		for (int i = 0; i < a.length; i++) {
			r[i] = a[i];
		}
		for (int i = a.length; i < pos; i++) {
			r[i] = b[i];
		}
		return r;
	}

	/*
	 * Q5d
	 */
	public int winner(Contestant[] a) {
		Contestant c = null;
		double best = 0;
		for (int i = 0; i < a.length; i++) {
			double s = a[i].calculateTotalScore();
			if (c == null || s > best) {
				best = s;
				c = a[i];
			}
		}
		return c.getId();
	}
}
