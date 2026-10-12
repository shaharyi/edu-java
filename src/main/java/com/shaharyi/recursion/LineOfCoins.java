package com.shaharyi.recursion;
import java.util.Scanner;

public class LineOfCoins {

    // Helper record for returning both the chosen move index and the evaluated score
    record Result(int move, int score) { }

    /**
     * Negamax algorithm for the Line of Coins game:
     * @param arr   The array containing coin values.
     * @param left  The left boundary index of the active subarray.
     * @param right The right boundary index of the active subarray.
     * @return      The maximum score difference the current player can guarantee.
     */
    public static Result negamax(int[] arr, int left, int right) {
        // Base Case: No coins remain in the active range
        if (left > right) {
            return new Result(-1, 0);
        }

        // Option 1: Take the coin from the left end
        // Current player gets arr[left], minus the opponent's best score from the remaining coins
        int takeLeftScore = arr[left] - negamax(arr, left + 1, right).score();

        // Option 2: Take the coin from the right end
        int takeRightScore = arr[right] - negamax(arr, left, right - 1).score();

        // Choose the move that yields the higher net score
        if (takeLeftScore >= takeRightScore) {
            return new Result(left, takeLeftScore);
        } else {
            return new Result(right, takeRightScore);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Initial board layout
        int[] board = {3, 9, 1, 2, 8, 4}; 
        int left = 0;
        int right = board.length - 1;

        int userScore = 0;
        int aiScore = 0;

        System.out.println("=== Line of Coins Game ===");
        System.out.println("On each turn, choose to take a coin from the Left (L) or Right (R) end.");

        while (left <= right) {
            // Display current board and scores
            printBoard(board, left, right);
            System.out.println("Score: Player = " + userScore + " | AI = " + aiScore);

            // --- HUMAN TURN ---
            System.out.print("Pick (L)eft or (R)ight: ");
            String choice = scanner.next().toUpperCase();
            while (!choice.equals("L") && !choice.equals("R")) {
                System.out.print("Invalid input! Please type L or R: ");
                choice = scanner.next().toUpperCase();
            }

            if (choice.equals("L")) {
                userScore += board[left];
                left++;
            } else {
                userScore += board[right];
                right--;
            }

            // Check if game ended after human move
            if (left > right) break;

            // --- AI TURN (Negamax) ---
            Result aiResult = negamax(board, left, right);
            int chosenIndex = aiResult.move();

            if (chosenIndex == left) {
                System.out.println("\n🤖 AI takes from LEFT: " + board[left]);
                aiScore += board[left];
                left++;
            } else {
                System.out.println("\n🤖 AI takes from RIGHT: " + board[right]);
                aiScore += board[right];
                right--;
            }
        }

        // --- GAME OVER ---
        System.out.println("\n=== Game Over ===");
        System.out.println("Final Score: Player = " + userScore + " | AI = " + aiScore);

        if (userScore > aiScore) {
            System.out.println("🎉 You Win!");
        } else if (aiScore > userScore) {
            System.out.println("💥 AI Wins!");
        } else {
            System.out.println("🤝 It's a Tie!");
        }

        scanner.close();
    }

    // Helper method to display the remaining active section of the board
    private static void printBoard(int[] arr, int left, int right) {
        System.out.print("\nCurrent Board: [ ");
        for (int i = left; i <= right; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println("]");
    }
}