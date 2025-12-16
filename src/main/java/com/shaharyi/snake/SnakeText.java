package com.shaharyi.snake;

import com.shaharyi.node.Node;

import java.io.IOException;
import java.util.Random;

public class SnakeText {

	static final int W = 30;
	static final int H = 15;
	static final char WALL = '#';
	static final char EMPTY = ' ';
	static final char SNAKE_HEAD = 'O';
	static final char SNAKE_BODY = 'o';
	static final char APPLE = '@';

	static final Random rng = new Random();

	public static void main(String[] args) throws Exception {
		// Helpful for many terminals to make "no echo" / "instant keys":
		// On Linux, run: stty -icanon -echo (then start the game), and restore after:
		// stty sane
		// The game still works without it, but keys may require Enter depending on
		// terminal.

		Snake snake = new Snake(new Point(W / 2, H / 2));
		int dx = 1, dy = 0;
		Point apple = randomApple(snake);
		int apples = 0;

		boolean gameOver = false;

		// Basic tick loop
		while (true) {
			long frameStart = System.currentTimeMillis();

			// ---- Input (non-blocking if terminal is in raw mode) ----
			int key = readKeyNonBlocking();
			if (gameOver) {
				if (key == 'r' || key == 'R') {
					snake = new Snake(new Point(W / 2, H / 2));
					dx = 1;
					dy = 0;
					apple = randomApple(snake);
					apples = 0;
					gameOver = false;
				} else if (key == 27 /* ESC */ || key == 'q' || key == 'Q') {
					clearScreen();
					return;
				}
			} else {
				// WASD directions, block 180° reversal
				if ((key == 'w' || key == 'W') && dy != 1) {
					dx = 0;
					dy = -1;
				} else if ((key == 's' || key == 'S') && dy != -1) {
					dx = 0;
					dy = 1;
				} else if ((key == 'a' || key == 'A') && dx != 1) {
					dx = -1;
					dy = 0;
				} else if ((key == 'd' || key == 'D') && dx != -1) {
					dx = 1;
					dy = 0;
				} else if (key == 27 /* ESC */) {
					clearScreen();
					return;
				}
			}

			// ---- Update ----
			if (!gameOver) {
				Point h = snake.getHeadPos();
				Point nh = new Point(h.x + dx, h.y + dy);

				// wall collision (inside border)
				if (nh.x < 0 || nh.x >= W || nh.y < 0 || nh.y >= H) {
					gameOver = true;
				} else if (snake.contains(nh)) {
					gameOver = true;
				} else {
					boolean ate = nh.equals(apple);
					snake.moveTo(nh, ate);
					if (ate) {
						apples++;
						apple = randomApple(snake);
					}
				}
			}

			// ---- Draw ----
			draw(snake, apple, apples, gameOver);

			// ---- Tick (aim ~10 fps) ----
			sleepToMaintainFps(frameStart, dy != 0 ? 6 : 10);
		}
	}

	static Point randomApple(Snake snake) {
		Point p;
		do {
			p = new Point(rng.nextInt(W), rng.nextInt(H));
		} while (snake.contains(p));
		return p;
	}

	static void draw(Snake snake, Point apple, int apples, boolean gameOver) {
		char[][] grid = new char[H][W];
		for (int y = 0; y < H; y++) {
			for (int x = 0; x < W; x++)
				grid[y][x] = EMPTY;
		}

		// apple
		grid[apple.y][apple.x] = APPLE;

		// snake (head first)
		Node<Point> cur = snake.getHead();
		boolean first = true;
		while (cur != null) {
			Point p = cur.getValue();
			grid[p.y][p.x] = first ? SNAKE_HEAD : SNAKE_BODY;
			first = false;
			cur = cur.getNext();
		}

		StringBuilder sb = new StringBuilder();
		clearScreenInto(sb);

		// --- top border (scaled) ---
		sb.append(WALL);
		for (int i = 0; i < W; i++)
			sb.append(WALL);
		sb.append(WALL).append('\n');

		// --- rows ---
		for (int y = 0; y < H; y++) {

			sb.append(WALL);

			for (int x = 0; x < W; x++) {
				char c = grid[y][x];

				if (c == EMPTY)
					sb.append(" ");
				else if (c == APPLE)
					sb.append("@");
				else if (c == SNAKE_HEAD)
					sb.append("O");
				else if (c == SNAKE_BODY)
					sb.append("o");
			}

			sb.append(WALL).append('\n');
		}

		// --- bottom border (scaled) ---
		sb.append(WALL);
		for (int i = 0; i < W; i++)
			sb.append(WALL);
		sb.append(WALL).append('\n');

		sb.append("Apples: ").append(apples).append("   Controls: WASD   ESC quit");
		if (gameOver)
			sb.append("   GAME OVER (R=retry, ESC/Q=quit)");
		sb.append('\n');

		System.out.print(sb);
		System.out.flush();
	}

	// Non-blocking read of one byte if available; returns -1 if none.
	static int readKeyNonBlocking() throws IOException {
		if (System.in.available() > 0)
			return System.in.read();
		return -1;
	}

	static void sleepToMaintainFps(long frameStartMillis, int fps) {
		long frameTime = 1000L / fps;
		long elapsed = System.currentTimeMillis() - frameStartMillis;
		long remaining = frameTime - elapsed;
		if (remaining > 0) {
			try {
				Thread.sleep(remaining);
			} catch (InterruptedException ignored) {
			}
		}
	}

	// ANSI clear screen
	static void clearScreen() {
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}

	static void clearScreenInto(StringBuilder sb) {
		sb.append("\033[H\033[2J");
	}
}
