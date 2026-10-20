package com.shaharyi.recursion;

import java.util.List;

public abstract class GameState {
    public abstract boolean isTerminal();
    public abstract int evaluate();
    public abstract List<Integer> getLegalMoves();
    public abstract void doMove(int move);
    public abstract void undoMove(int move);
    
    // Tracks whose turn it is (needed by Negamax for extra turns)
    public abstract int getCurrentPlayer();
}