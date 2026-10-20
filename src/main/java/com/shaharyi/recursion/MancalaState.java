package com.shaharyi.recursion;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class MancalaState extends GameState {
    
    private int[] board;
    private int currentPlayer; // 0 for South (Human), 1 for North (AI)
    private Stack<HistorySnapshot> history;

    private static class HistorySnapshot {
        int[] previousBoard;
        int previousPlayer;

        HistorySnapshot(int[] board, int player) {
            this.previousBoard = board.clone();
            this.previousPlayer = player;
        }
    }

    public MancalaState() {
        this.board = new int[14];
        for (int i = 0; i < 14; i++) {
            if (i != 6 && i != 13) {
                board[i] = 4; // 4 seeds per pit initially
            }
        }
        this.currentPlayer = 0;
        this.history = new Stack<>();
    }

    @Override
    public int getCurrentPlayer() {
        return this.currentPlayer;
    }

    @Override
    public List<Integer> getLegalMoves() {
        List<Integer> moves = new ArrayList<>();
        int start = (currentPlayer == 0) ? 0 : 7;
        int end = (currentPlayer == 0) ? 5 : 12;

        for (int i = start; i <= end; i++) {
            if (board[i] > 0) {
                moves.add(i);
            }
        }
        return moves;
    }

    @Override
    public void doMove(int move) {
        // 1. Save history snapshot before altering the board
        history.push(new HistorySnapshot(board, currentPlayer));

        // 2. Pick up stones
        int stones = board[move];
        board[move] = 0;
        int currentIndex = move;

        // 3. Sow stones
        while (stones > 0) {
            currentIndex = (currentIndex + 1) % 14;
            
            // Skip opponent's store
            if ((currentPlayer == 0 && currentIndex == 13) || 
                (currentPlayer == 1 && currentIndex == 6)) {
                continue;
            }

            board[currentIndex]++;
            stones--;
        }

        // 4. Capture rule
        if (board[currentIndex] == 1 && currentIndex != 6 && currentIndex != 13) {
            boolean isMyPit = (currentPlayer == 0 && currentIndex < 6) || 
                              (currentPlayer == 1 && currentIndex > 6 && currentIndex < 13);
            int oppositePit = 12 - currentIndex;

            if (isMyPit && board[oppositePit] > 0) {
                int myStore = (currentPlayer == 0) ? 6 : 13;
                board[myStore] += board[currentIndex] + board[oppositePit];
                board[currentIndex] = 0;
                board[oppositePit] = 0;
            }
        }

        // 5. Extra turn rule (if last stone lands in your store)
        boolean extraTurn = (currentPlayer == 0 && currentIndex == 6) || 
                            (currentPlayer == 1 && currentIndex == 13);
        if (!extraTurn) {
            currentPlayer = 1 - currentPlayer;
        }

        // 6. Sweep rule if the game ends
        if (isTerminal()) {
            sweepRemainingStones();
        }
    }

    @Override
    public void undoMove(int move) {
        HistorySnapshot past = history.pop();
        this.board = past.previousBoard.clone();
        this.currentPlayer = past.previousPlayer;
    }

    @Override
    public boolean isTerminal() {
        return isSideEmpty(0) || isSideEmpty(1);
    }

    private boolean isSideEmpty(int player) {
        int start = (player == 0) ? 0 : 7;
        int end = (player == 0) ? 5 : 12;
        for (int i = start; i <= end; i++) {
            if (board[i] > 0) return false;
        }
        return true;
    }

    private void sweepRemainingStones() {
        for (int i = 0; i <= 5; i++) {
            board[6] += board[i];
            board[i] = 0;
        }
        for (int i = 7; i <= 12; i++) {
            board[13] += board[i];
            board[i] = 0;
        }
    }

    @Override
    public int evaluate() {
        int storeDiff = board[6] - board[13];
        
        int side0Stones = 0;
        for (int i = 0; i <= 5; i++) side0Stones += board[i];
        
        int side1Stones = 0;
        for (int i = 7; i <= 12; i++) side1Stones += board[i];

        int rawScore = (storeDiff * 10) + (side0Stones - side1Stones);
        return (currentPlayer == 0) ? rawScore : -rawScore;
    }

    public int getPlayerScore(int player) {
        return (player == 0) ? board[6] : board[13];
    }

    public void printBoard() {
        System.out.println("\n      [AI's Side - Player 1]");
        System.out.println("     12  11  10   9   8   7");
        System.out.print("   -------------------------\n");
        System.out.printf("[%2d] %2d  %2d  %2d  %2d  %2d  %2d [%2d]\n", 
            board[13], board[12], board[11], board[10], board[9], board[8], board[7], board[6]);
        System.out.printf("     %2d  %2d  %2d  %2d  %2d  %2d\n", 
            board[0], board[1], board[2], board[3], board[4], board[5]);
        System.out.print("   -------------------------\n");
        System.out.println("      0   1   2   3   4   5");
        System.out.println("      [Your Side - Player 0]\n");
        System.out.println("Turn: Player " + currentPlayer);
    }
}