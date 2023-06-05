package game;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class GameClock implements java.awt.event.ActionListener {
    private int playerTimeSeconds, opponentTimeSeconds;
    private final Timer timer;

    private GameClock() {
        opponentTimeSeconds = playerTimeSeconds = 300;
        timer = new Timer(1000, this);
        timer.setRepeats(false);
    }

    public boolean getIsTimerRunning() {
        return timer.isRunning();
    }

    public void switchTimerRunning() {
        if (getIsTimerRunning()) timer.stop();
        else timer.start();
    }

    public void setTimerState(boolean setRunning) {
        if (setRunning && getIsTimerRunning()) timer.stop();
        else if (!setRunning && !getIsTimerRunning()) timer.start();
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
        return String.format("%s%s", minutes > 0 ? minutes + ":" : "", seconds);
    }


    public int getOpponentTimeSeconds() {
        return opponentTimeSeconds;
    }

    public int getOpponentTimeMinutes() {
        return opponentTimeSeconds / 60;
    }

    public String getOpponentTimeString() {
        int minutes = getOpponentTimeMinutes(), seconds = this.opponentTimeSeconds % 60;
        return String.format("%s%s", minutes > 0 ? minutes + ":" : "", seconds);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (GameManager.localUser.canPlay()) playerTimeSeconds--;
        else opponentTimeSeconds--;

        if (playerTimeSeconds <= 0)
            // TODO: pozivanje endgame funkcije, player gubi
            util.Console.message("Playeru je isteklo vreme");
        else if (opponentTimeSeconds <= 0)
            // TODO: pozivanje endgame funkcije, opponent gubi
            util.Console.message("Opponentu je isteklo vreme");
    }
}
