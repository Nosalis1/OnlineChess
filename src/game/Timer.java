package game;

public class Timer {
    private int playerTimeSeconds, opponentTimeSeconds;
    private boolean isRunning = true;
    private Thread timerThread;

    private Timer() {
        opponentTimeSeconds = playerTimeSeconds = 300;
        timerThread = new Thread(new TimerRunnable());
    }

    public boolean getIsTimerInterrupted() {
        return timerThread.isInterrupted();
    }

    public void switchTimerPaused() {
        if (getIsTimerInterrupted()) timerThread.start();
        else timerThread.interrupt();
    }

    public void setTimerPaused(boolean setPaused) {
        if (setPaused && !getIsTimerInterrupted()) timerThread.interrupt();
        else if (!setPaused && getIsTimerInterrupted()) timerThread.start();
        else util.Console.warning("Cannot set timer thread to ");
    }

    public int getPlayerTimeSeconds() {
        return playerTimeSeconds;
    }

    public int getOpponentTimeSeconds() {
        return opponentTimeSeconds;
    }

    private class TimerRunnable implements Runnable {
        @Override
        public void run() {
            while (isRunning) {
                try {
//                    if (playerTimeSeconds <= 0) GameManager.instance.endGame();
//                    else if (opponentTimeSeconds <= 0) GameManager.instance.endGame();

                    if (GameManager.instance.canPlay()) playerTimeSeconds--;
                    else opponentTimeSeconds--;

                    Thread.sleep(1000);
                } catch (InterruptedException ignore) {
                }
            }
        }
    }
}
