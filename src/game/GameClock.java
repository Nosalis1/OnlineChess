package game;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class GameClock implements java.awt.event.ActionListener {
    private int playerTimeSeconds, opponentTimeSeconds;
    private final Timer timer;
    private final JLabel playerLabel, opponentLabel;

    public GameClock(JLabel playerLabel, JLabel opponentLabel) {
        playerTimeSeconds = 300;
        opponentTimeSeconds = 300;
        timer = new Timer(1000, this);
        timer.setRepeats(true);
        this.playerLabel = playerLabel;
        this.opponentLabel = opponentLabel;
    }

    public boolean getIsTimerRunning() {
        return timer.isRunning();
    }

    public void switchTimerState() {
        if (getIsTimerRunning()) timer.stop();
        else timer.start();
    }

    public void setTimerState(boolean setRunning) {
        if (setRunning && !getIsTimerRunning()) timer.start();
        else if (!setRunning && getIsTimerRunning()) timer.stop();
        else {
            String s = setRunning ? "running" : "stopped";
            util.Console.warning(String.format("Cannot set timer state to %s as it is already %s", s, s));
        }
    }


    public int getPlayerTimeSeconds() {
        return playerTimeSeconds;
    }

    public int getPlayerTimeMinutes() {
        return opponentTimeSeconds / 60;
    }

    public String getPlayerTimeString() {
        int minutes = getPlayerTimeMinutes(), seconds = this.playerTimeSeconds % 60;
        return String.format("%s%s", minutes > 0 ? minutes + ":" : "", seconds < 10 ? "0" + seconds : seconds);
    }


    public int getOpponentTimeSeconds() {
        return opponentTimeSeconds;
    }

    public int getOpponentTimeMinutes() {
        return (int) Math.floor((double) opponentTimeSeconds / 60);
    }

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
