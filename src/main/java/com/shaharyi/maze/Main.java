package com.shaharyi.maze;

class Main {
	public static void main(String[] args) {
		Maze maze = new Maze(21, 21);
		maze.mat.print();
		//maze.solve2(1, 1);
		maze.solve();
		maze.mat.print();
	}
}

