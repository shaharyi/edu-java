package com.shaharyi.recursion;

import java.util.Scanner;

public class SmallNim {

	// Helper record for returning both move decision and state score
	record Result(int move, int score, int calls) {
	}

	public static Result negamax(int items, int calls) {
		// If 0 items remain, the opponent took the last item and LOST.
		// Therefore, the current perspective WINS (+1).
		if (items == 0)
			return new Result(0, 1, calls);

		Result best = new Result(1, Integer.MIN_VALUE, 0);

		for (int take = 1; take <= 3 && items - take >= 0; take++) {
			// Evaluate child state from opponent's perspective and negate score
			Result r = negamax(items - take, calls + 1);
			if (-r.score() > best.score()) {
				best = r;
			}
		}

		return best;
	}

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int heap = 20;

		System.out.println("=== MISÈRE NIM (FIXED NEGAMAX) ===");
		System.out.println("Take 1, 2, or 3 items. \nPlayer to take the LAST item LOSES!");

		while (heap > 0) {
			System.out.println("\nRemaining items: " + "| ".repeat(heap) + "(" + heap + ")\n");

			// --- HUMAN TURN ---
			System.out.print("Your turn (1-3): ");
			int userMove = scanner.nextInt();
			while (userMove < 1 || userMove > 3 || userMove > heap) {
				System.out.print("Invalid! Pick 1, 2, or 3: ");
				userMove = scanner.nextInt();
			}

			heap -= userMove;
			System.out.println("\nRemaining items: " + "| ".repeat(heap) + "(" + heap + ")\n");

			if (heap == 0) {
				System.out.println("\n💥 You took the last item! YOU LOSE!");
				break;
			}

			// --- AI TURN (Negamax) ---
			Result r = negamax(heap, 0);
			System.out.println("calls="+r.calls());
			System.out.println("score="+r.score());
			System.out.println("🤖 AI takes " + r.move() + " item(s).");

			heap -= r.move;
			if (heap == 0) {
				System.out.println("\n🎉 AI took the last item! YOU WIN!");
				break;
			}
		}
		scanner.close();
	}
}