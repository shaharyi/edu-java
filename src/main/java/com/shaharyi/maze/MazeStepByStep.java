import java.util.Random;
import java.util.Scanner;

public class MazeStep {
	// Odd sizes give single-cell corridors without “double-print” tricks
	static final int W = 11, H = 7;
	static final char WALL = 'B', PATH = ' ';
	static final char[][] M = new char[H][W];
	static final Random rnd = new Random(); // use new Random(0) for deterministic demo
	static final Scanner scan = new Scanner(System.in);	
	static int step = 0;

	public static void main(String[] args) throws Exception {
		for (int y = 0; y < H; y++)
			for (int x = 0; x < W; x++)
				M[y][x] = WALL;
		carve(1, 1); // start inside the border

		// Punch entrance (top) and exit (bottom), with a couple of final steps
		M[0][1] = PATH;
		show(-1, -1, "Open entrance");
		M[H - 1][W - 2] = PATH;
		show(-1, -1, "Open exit");
		show(-1, -1, "Done");
	}

	// Recursive backtracking: visit cell, knock a wall, recurse, backtrack
	static void carve(int x, int y) throws Exception {
		M[y][x] = PATH;
		show(x, y, "Visit (" + x + "," + y + ")");

		int[][] dirs = { { 2, 0 }, { -2, 0 }, { 0, 2 }, { 0, -2 } }; // 2-step moves to keep 1-cell corridors
		shuffle(dirs);
		for (int[] d : dirs) {
			int nx = x + d[0], ny = y + d[1];
			if (0 < nx && nx < W - 1 && 0 < ny && ny < H - 1 && M[ny][nx] == WALL) {
				M[y + d[1] / 2][x + d[0] / 2] = PATH; // knock down the wall between
				show(x + d[0] / 2, y + d[1] / 2, "Knock wall");

				carve(nx, ny); // recurse into next cell
				show(x, y, "Backtrack to (" + x + "," + y + ")");
			}
		}
	}

	// Redraw maze and wait for Enter
	static void show(int cx, int cy, String note) throws Exception {
		System.out.print("\033[H\033[2J"); // clear screen (ANSI)
		System.out.flush();
		System.out.println("Recursive Maze — step " + (++step) + (note != null ? " : " + note : ""));
		System.out.println("Press Enter for next step (Ctrl+C to quit)\n");

		for (int y = 0; y < H; y++) {
			for (int x = 0; x < W; x++) {
				if (x == cx && y == cy)
					System.out.print('@'); // highlight current cell / wall
				else
					System.out.print(M[y][x]);
			}
			System.out.println();
		}
		scan.nextLine(); // wait for Enter
	}

	// Fisher–Yates shuffle
	static void shuffle(int[][] a) {
		for (int i = a.length - 1; i > 0; i--) {
			int j = rnd.nextInt(i + 1);
			int[] tmp = a[i];
			a[i] = a[j];
			a[j] = tmp;
		}
	}
}
