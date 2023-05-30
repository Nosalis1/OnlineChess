package util.events;

import util.Console;

public class Event extends util.Array<Runnable> {
    /**
     * Adds a runnable action to the event, checking for duplicates before adding.
     * If the action already exists in the list, a warning message is printed.
     *
     * @param action the action to add
     */
    @Override
    public void add(Runnable action) {
        if (contains(action)) {
            Console.warning("Action you are trying to add already exists in the list!", Console.PrintType.Util);
            return;
        }
        super.add(action);
    }

    /**
     * Runs the specified runnable action.
     *
     * @param action the action to run
     */
    private void run(Runnable action) {
        action.run();
    }

    /**
     * Runs the action at the specified index.
     *
     * @param index the index of the action to run
     */
    private void run(final int index) {
        run(get(index));
    }

    /**
     * Runs all actions in the event.
     */
    public void run() {
        for (int i = 0; i < size(); i++) {
            run(i);
        }
    }

    /**
     * Runs all actions in the event and then clears the event.
     */
    public void runOnce() {
        run();
        clear();
    }
}