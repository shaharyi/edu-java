package com.shaharyi.maze;

import java.util.Scanner;

public class SimpleMaze {

	static final private int HEIGHT = 21, WIDTH = 21;

	static final private char START = 'X';
	static final private char END = 'X';
	static final private char WALL = '@';
	static final private char CRUMB = '.';
	static final private char PATH = '+';
	static final private char CLEAR = ' ';

	static final private int[][] OFFSETS = {
			{ -1, 0 }, // NORTH
			{ 1, 0 }, // SOUTH
			{ 0, 1 }, // EAST
			{ 0, -1 } // WEST
	};

	public static void main(String[] args) {
		char[][] mat = new char[HEIGHT][WIDTH];
		fill(mat, WALL);
		genRecurse(mat, 1, 1);
		mat[1][1] = START;
		mat[HEIGHT - 2][WIDTH - 2] = END;
		print(mat);
		solve(mat, 1, 1);
		print(mat);
	}

	public static void fill(char[][] mat, char c) {
		for (int i = 0; i < mat.length; i++) {
			for (int j = 0; j < mat[0].length; j++) {
				mat[i][j] = c;
			}
		}
	}

	public static void print(char[][] mat) {
		System.out.println();
		for (int i = 0; i < mat.length; i++) {
			for (int j = 0; j < mat[0].length; j++) {
				System.out.print(mat[i][j] + " ");
			}
			System.out.println();
		}
	}

	/*
	 * fill array of the 4 neighbors coordinates of (x, y) in distance of "step"
	 * looking like: [[y1,x1], [y2,x2], ... ]
	 */
	public static int getNeighbors(int[][] n, int y, int x, int step) {
		int num = 0;
		for (int i = 0; i < 4; i++) {
			int ny = y + step * OFFSETS[i][0];
			int nx = x + step * OFFSETS[i][1];
			if (ny >= 0 && ny < HEIGHT && nx >= 0 && nx < WIDTH) {
				n[num][0] = ny;
				n[num][1] = nx;
				num++;
			}
		}
		return num;
	}

	static void swap(Object[] arr, int i, int j) {
		Object temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;
	}

	static void shuffle(Object[] arr, int num) {
		for (int i = num; i > 0; i--) {
			int r = (int) (Math.random() * i);
			swap(arr, r, i - 1);
		}
	}

	public static void genRecurse(char[][] mat, int y, int x) {
		print(mat);
		mat[y][x] = CLEAR;
		int[][] n = new int[4][2];
		int num = getNeighbors(n, y, x, 2);
		shuffle(n, num);
		for (int i = 0; i < num; i++) {
			int ny = n[i][0];
			int nx = n[i][1];
			if (mat[ny][nx] == WALL) {
				int midy = (y + ny) / 2;
				int midx = (x + nx) / 2;
				mat[midy][midx] = CLEAR;
				genRecurse(mat, n[i][0], n[i][1]);
			}
		}
	}

	public static boolean solve(char[][] m, int y, int x) {
		if (x == WIDTH - 2 && y == HEIGHT - 2)
			return true;
		if (m[y][x] == WALL || m[y][x] == CRUMB)
			return false;
		m[y][x] = CRUMB;

		for (int i = 0; i < 4; i++) {
			int ny = y + OFFSETS[i][0];
			int nx = x + OFFSETS[i][1];
			if (ny >= 0 && ny < HEIGHT && nx >= 0 && nx < WIDTH) {			
				if (solve(m, ny, nx)) {
					m[y][x] = PATH;
					return true;
				}
			}
		}
		return false;
	}

}
