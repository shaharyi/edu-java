package com.shaharyi.recursion;

import java.util.List;

public class Negamax {
    public static final int INF = 1_000_000;

    public static int negamax(GameState state, int depth, int alpha, int beta) {
        if (depth == 0 || state.isTerminal()) {
            return state.evaluate();
        }

        int maxScore = -INF;

        for (int move : state.getLegalMoves()) {
            int playerBefore = state.getCurrentPlayer();

            state.doMove(move);
            int playerAfter = state.getCurrentPlayer();

            int score;
            if (playerBefore == playerAfter) {
                // Extra turn! Do NOT negate or swap bounds
                score = negamax(state, depth - 1, alpha, beta);
            } else {
                // Turn changed: negate and swap alpha/beta
                score = -negamax(state, depth - 1, -beta, -alpha);
            }

            state.undoMove(move);

            if (score > maxScore) {
                maxScore = score;
            }
            if (maxScore > alpha) {
                alpha = maxScore;
            }
            if (alpha >= beta) {
                break;
            }
        }
        return maxScore;
    }

    public static int getBestMove(GameState state, int depth) {
        int bestMove = -1;
        int maxScore = -INF;
        int alpha = -INF;
        int beta = INF;

        for (int move : state.getLegalMoves()) {
            int playerBefore = state.getCurrentPlayer();

            state.doMove(move);
            int playerAfter = state.getCurrentPlayer();

            int score;
            if (playerBefore == playerAfter) {
                score = negamax(state, depth - 1, alpha, beta);
            } else {
                score = -negamax(state, depth - 1, -beta, -alpha);
            }

            state.undoMove(move);

            if (score > maxScore) {
                maxScore = score;
                bestMove = move;
            }
            if (maxScore > alpha) {
                alpha = maxScore;
            }
        }
        return bestMove;
    }
}