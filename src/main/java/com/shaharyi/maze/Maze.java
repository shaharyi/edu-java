package com.shaharyi.maze;

import java.util.Random;

import com.shaharyi.node.Stack;

public class Maze {
	public Mat mat;
	private Random rand = new Random();
	private Point start;
	private Point end;

	static final private char WALL = '@';
	static final private char CRUMB = '.';
	static final private char PATH = '+';
	static final private char CLEAR = ' ';

	public Maze(int w, int h) {
		start = new Point(1, 1);
		end = new Point(w - 2, h - 2);
		mat = new Mat(w, h);
		mat.fill(WALL);
		genRecurse(start);
		// genStack(start);
		mat.set(start, 'X');
		mat.set(end, 'X');
	}

	void swap(Object[] arr, int x, int y) {
		Object temp = arr[x];
		arr[x] = arr[y];
		arr[y] = temp;
	}

	void shuffle(Object[] arr) {
		for (int i = arr.length; i > 0; i--) {
			int r = rand.nextInt(i);
			swap(arr, r, i - 1);
		}
	}

	void shuffleRecurse(Object[] arr, int n) {
		if (n == 0)
			return;
		int r = rand.nextInt(n + 1);
		swap(arr, n, r);
		shuffleRecurse(arr, n - 1);
	}

	public Point[] neighbors(Point p, int step, boolean shuffle) {
		int x = p.x, y = p.y;
		// @formatter:off
		Point[] ret = { 
				new Point(x - step, y), 
				new Point(x + step, y), 
				new Point(x, y - step), 
				new Point(x, y + step) 
		}; 
		// @formatter:on
		if (shuffle)
			shuffleRecurse(ret, ret.length - 1);
		return ret;
	}

	public Point middle(Point p1, Point p2) {
		return new Point((p1.x + p2.x) / 2, (p1.y + p2.y) / 2);
	}

	public void genRecurse(Point pos) {
		mat.print();

		mat.set(pos, CLEAR);
		Point[] neibors = neighbors(pos, 2, true);
		Point mid;
		for (int i = 0; i < neibors.length; i++) {
			if (mat.get(neibors[i]) == WALL) {
				mid = middle(pos, neibors[i]);
				mat.set(mid, CLEAR);
				genRecurse(neibors[i]);
			}
		}
	}

	public void genRecurseRand(Point pos) {
		mat.set(pos, CLEAR);
		Point[] neibors = neighbors(pos, 2, false);
		Point next = null;
		for (int i = neibors.length; i > 0; i--) {
			int r = rand.nextInt(i);
			next = new Point(neibors[r]);
			neibors[r] = neibors[i - 1];
			if (mat.get(next) == WALL) {
				mat.set(middle(pos, next), CLEAR);
				genRecurseRand(next);
			}
		}
	}

	public void genStack(Point pos) {
		Stack<Point> stack = new Stack<>();
		mat.set(pos, CLEAR);
		stack.push(pos);
		Point[] neibors;
		Point mid;
		while (!stack.isEmpty()) {
			pos = stack.pop();
			neibors = neighbors(pos, 2, true);
			for (int i = 0; i < neibors.length; i++) {
				if (mat.get(neibors[i]) == WALL) {
					mat.set(neibors[i], CLEAR);
					mid = middle(pos, neibors[i]);
					mat.set(mid, CLEAR);
					stack.push(neibors[i]);
				}
			}
		}
	}

	public void solve() {
		solve(start);
	}

	/**
	 * Solve using Tremaux algorithm:
	 * Leave a mark behind you.
	 * On junction explore every non marked direction.
	 * 
	 * @param x, y = current position (start with 1,1)
	 * @return true if this is part of the solution path
	 */
	public boolean solve2(int x, int y) {
		final int[][] OFFSETS = {
		        {-1,  0}, // NORTH
		        { 1,  0}, // SOUTH
		        { 0,  1}, // EAST
		        { 0, -1}  // WEST
		    };
		if (x == end.x && y == end.y)
			return true;
		if (mat.get(x, y) == WALL || mat.get(x, y) == CRUMB)
			return false;
		mat.set(x, y, CRUMB);

		for (int i = 0; i < 4; i++) {
			int x1 = x + OFFSETS[i][0];
			int y1 = y + OFFSETS[i][1];
				if (solve2(x1, y1)) {
					mat.set(x, y, PATH);
					return true;
				}
		}
		return false;
	}

	public boolean solve(Point pos) {
		if (pos.equals(end))
			return true;
		if (mat.get(pos) == WALL || mat.get(pos) == CRUMB)
			return false;
		mat.set(pos, CRUMB);
		Point[] neibors = neighbors(pos, 1, false);
		for (int i = 0; i < neibors.length; i++) {
			if (solve(neibors[i])) {
				mat.set(pos, PATH);
				return true;
			}
		}
		return false;
	}

}
