package game;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Represents the game clock for tracking player and opponent time.
 */
public class GameClock implements java.awt.event.ActionListener {
    private int playerTimeSeconds, opponentTimeSeconds;
    private final Timer timer;
    private final JLabel playerLabel, opponentLabel;

    /**
     * Initializes the game clock with player and opponent labels.
     *
     * @param playerLabel   The label for displaying player's time.
     * @param opponentLabel The label for displaying opponent's time.
     */
    public GameClock(JLabel playerLabel, JLabel opponentLabel) {
        playerTimeSeconds = 300;
        opponentTimeSeconds = 300;
        timer = new Timer(1000, this);
        timer.setRepeats(true);
        this.playerLabel = playerLabel;
        this.opponentLabel = opponentLabel;
    }

    /**
     * Checks if the timer is currently running.
     *
     * @return True if the timer is running, false otherwise.
     */
    public boolean getIsTimerRunning() {
        return timer.isRunning();
    }

    /**
     * Switches the state of the timer (running/stopped).
     */
    @SuppressWarnings("unused")
    public void switchTimerState() {
        if (getIsTimerRunning())
            timer.stop();
        else
            timer.start();
    }

    /**
     * Sets the state of the timer (running/stopped).
     *
     * @param setRunning True to set the timer to running, false to set it to stopped.
     */
    public void setTimerState(boolean setRunning) {
        if (setRunning && !getIsTimerRunning())
            timer.start();
        else if (!setRunning && getIsTimerRunning())
            timer.stop();
        else {
            String s = setRunning ? "running" : "stopped";
            utility.Console.warning(String.format("Cannot set timer state to %s as it is already %s", s, s));
        }
    }

    /**
     * Returns the remaining time in seconds for the player.
     *
     * @return The player's time in seconds.
     */
    @SuppressWarnings("unused")
    public int getPlayerTimeSeconds() {
        return playerTimeSeconds;
    }

    /**
     * Returns the remaining time in minutes for the player.
     *
     * @return The player's time in minutes.
     */
    public int getPlayerTimeMinutes() {
        return opponentTimeSeconds / 60;
    }

    /**
     * Returns the formatted string representation of the player's time.
     *
     * @return The player's time string.
     */
    public String getPlayerTimeString() {
        int minutes = getPlayerTimeMinutes(), seconds = this.playerTimeSeconds % 60;
        return String.format("%s%s", minutes > 0 ? minutes + ":" : "", seconds < 10 ? "0" + seconds : seconds);
    }

    /**
     * Returns the remaining time in seconds for the opponent.
     *
     * @return The opponent's time in seconds.
     */
    @SuppressWarnings("unused")
    public int getOpponentTimeSeconds() {
        return opponentTimeSeconds;
    }

    /**
     * Returns the remaining time in minutes for the opponent.
     *
     * @return The opponent's time in minutes.
     */
    public int getOpponentTimeMinutes() {
        return (int) Math.floor((double) opponentTimeSeconds / 60);
    }

    /**
     * Returns the formatted string representation of the opponent's time.
     *
     * @return The opponent's time string.
     */
    public String getOpponentTimeString() {
        int minutes = getOpponentTimeMinutes(), seconds = this.opponentTimeSeconds % 60;
        return String.format("%s%s", minutes > 0 ? minutes + ":" : "", seconds < 10 ? "0" + seconds : seconds);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (GameManager.localUser.canPlay()) {
            playerTimeSeconds--;
            playerLabel.setText(GameManager.localUser.getUserName() + ": " + getPlayerTimeString());
        } else {
            opponentTimeSeconds--;
            opponentLabel.setText(GameManager.opponent.getUserName() + ": " + getOpponentTimeString());
        }

        if (playerTimeSeconds <= 0 || opponentTimeSeconds <= 0) {
            setTimerState(false);
            GameManager.onGameEnded.run();
        }
    }
}