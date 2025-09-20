package com.shaharyi.recursion;

//20 digits: 3.1415926535 8979323846

class Pi {
	public static void main(String[] args) {
		System.out.println("page 26 q.11: " + sum(5));
		System.out.println("start");
		System.out.println(Math.PI);
		double r = 4 * leibniz2(0, 0, 0.0001);
		System.out.println(r);
		r = 4 * leibniz(0, 0.0001);
		System.out.println(r);
		r = 3 + contFracPI(1, 5000);
		System.out.println(r);
		r = 4 / (1 + euler(1, 120));
		System.out.println(r);
	}

	public static double contFracPI(int n, int m) {
		if (n == m)
			return 0;
		else {
			int a = (2 * n) - 1;
			double x = (a * a) / (6 + contFracPI(n + 1, m));
			return x;
		}
	}

	// linear convergence
	public static double euler(int n, int m) {
		if (n == m)
			return 0;
		else {
			double x = (n * n) / ((2 * n + 1) + euler(n + 1, m));
			return x;
		}
	}

	public static double leibniz2(int n, double c, double precision) {
		double e = 1.0 / (2 * n + 1);
		if (e < precision)
			return c;
		int sign = -2 * (n % 2) + 1;
		return leibniz2(n + 1, c + sign * e, precision);
	}

	// Slow convergence: for 3E+n elements, we get n digits of PI
	public static double leibniz(int n, double precision) {
		int sign = -2 * (n % 2) + 1;
		double e = (double) sign / (2 * n + 1);
		// System.out.println(e);
		if (Math.abs(e) < precision)
			return 0;
		return e + leibniz(n + 1, precision);
	}

	// page 26 q.11
	public static double sum(int n) {
		if (n == 1)
			return 1;
		if (n % 2 == 0)
			return -Math.sqrt(2 * n - 1) + sum(n - 1);
		return n * 2 - 1 + sum(n - 1);
	}
}
