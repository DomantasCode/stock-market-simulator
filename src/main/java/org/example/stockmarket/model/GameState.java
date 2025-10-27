package org.example.stockmarket.model;

import org.example.stockmarket.util.Constants;
import org.example.stockmarket.util.GameStatus;

/**
 * Žaidimo būsena - seka ėjimus ir statusą
 * Encapsulation - visi laukai private su getteriais
 */
public class GameState {
    private int currentTurn;
    private GameStatus status;

    public GameState() {
        this.currentTurn = 0;
        this.status = GameStatus.PLAYING;
    }

    public void nextTurn() {
        if (status == GameStatus.PLAYING) {
            currentTurn++;
            if (currentTurn > Constants.MAX_TURNS) {
                status = GameStatus.FINISHED;
            }
        }
    }

    public void setBankrupt() {
        status = GameStatus.BANKRUPT;
    }

    public void setFinished() {
        status = GameStatus.FINISHED;
    }

    public boolean isGameOver() {
        return status == GameStatus.FINISHED || status == GameStatus.BANKRUPT;
    }

    public int getCurrentTurn() {
        return currentTurn;
    }

    public GameStatus getStatus() {
        return status;
    }

    public int getRemainingTurns() {
        return Math.max(0, Constants.MAX_TURNS - currentTurn + 1);
    }
}
