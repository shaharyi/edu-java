package com.shaharyi.recursion;

import java.util.List;
import java.util.Scanner;

public class Mancala {

    public static void main(String[] args) {
        MancalaState game = new MancalaState();
        Scanner scanner = new Scanner(System.in);

        printWelcomeMessage();
        runGameLoop(game, scanner);
        printGameResult(game);

        scanner.close();
    }

    private static void printWelcomeMessage() {
        System.out.println("=================================================");
        System.out.println("             WELCOME TO MANCALA AI               ");
        System.out.println("=================================================");
        System.out.println("You are Player 0 (South). The AI is Player 1 (North).");
        System.out.println("Your pits: 0-5  | Your store: 6");
        System.out.println("AI pits:   7-12 | AI store:   13");
        System.out.println("-------------------------------------------------");
    }

    private static void runGameLoop(MancalaState game, Scanner scanner) {
        while (!game.isTerminal()) {
            game.printBoard();

            int currentPlayer = game.getCurrentPlayer();
            List<Integer> legalMoves = game.getLegalMoves();

            if (legalMoves.isEmpty()) {
                System.out.println("Player " + currentPlayer + " has no legal moves remaining!");
                break;
            }

            int chosenMove;
            if (currentPlayer == 0) {
                chosenMove = getHumanMove(scanner, legalMoves);
            } else {
                chosenMove = getAIMove(game);
            }

            game.doMove(chosenMove);
            System.out.println("-------------------------------------------------");
        }
    }

    private static int getHumanMove(Scanner scanner, List<Integer> legalMoves) {
        System.out.print("Your turn (Player 0). Choose a pit " + legalMoves + ": ");
        while (true) {
            if (scanner.hasNextInt()) {
                int move = scanner.nextInt();
                if (legalMoves.contains(move)) {
                    return move;
                } else {
                    System.out.print("Invalid choice. Select a non-empty pit from " + legalMoves + ": ");
                }
            } else {
                System.out.print("Please enter a valid integer: ");
                scanner.next(); // Clear invalid token
            }
        }
    }

    private static int getAIMove(MancalaState game) {
        System.out.println("AI (Player 1) is searching decision tree...");

        // Search depth of 8 to 10 plies provides strong play and fast response times
        int searchDepth = 8;
        int move = Negamax.getBestMove(game, searchDepth);

        System.out.println("AI chooses pit: " + move);
        return move;
    }

    private static void printGameResult(MancalaState game) {
        System.out.println("\nFinal Board State:");
        game.printBoard();

        int p0Score = game.getPlayerScore(0);
        int p1Score = game.getPlayerScore(1);

        System.out.println("=================================================");
        System.out.println("                    GAME OVER                    ");
        System.out.println("=================================================");
        System.out.println("Player 0 (Human) Score : " + p0Score);
        System.out.println("Player 1 (AI)    Score : " + p1Score);
        System.out.println("-------------------------------------------------");

        if (p0Score > p1Score) {
            System.out.println("VICTORY! You defeated the AI by " + (p0Score - p1Score) + " stones!");
        } else if (p1Score > p0Score) {
            System.out.println("DEFEAT! The AI won by " + (p1Score - p0Score) + " stones!");
        } else {
            System.out.println("DRAW! It's a tie game!");
        }
    }
}