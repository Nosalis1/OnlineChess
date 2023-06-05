package util.events;

import util.Console;

public class ArgEvent<T> extends util.Array<Action<T>> {
    /**
     * Adds an action to the event, checking for duplicates before adding.
     * If the action already exists in the list, a warning message is printed.
     *
     * @param action the action to add
     */
    @Override
    public void add(Action<T> action) {
        if (contains(action)) {
            Console.warning("Action you are trying to add already exists in the list!", this);
            return;
        }
        super.add(action);
    }

    /**
     * Runs the specified action with the provided argument.
     *
     * @param action the action to run
     * @param arg    the argument for the action
     */
    private void run(Action<T> action, T arg) {
        action.run(arg);
    }

    /**
     * Runs the action at the specified index with the provided argument.
     *
     * @param index the index of the action to run
     * @param arg   the argument for the action
     */
    private void run(final int index, T arg) {
        run(get(index), arg);
    }

    /**
     * Runs all actions in the event with the provided argument.
     *
     * @param arg the argument for the actions
     */
    public void run(T arg) {
        for (int i = 0; i < size(); i++) {
            run(i, arg);
        }
    }

    /**
     * Runs all actions in the event with the provided argument and then clears the event.
     *
     * @param arg the argument for the actions
     */
    @SuppressWarnings("unused")
    public void runOnce(T arg) {
        run(arg);
        clear();
    }
}