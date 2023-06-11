package utility.eventSystem;

import utility.math.Array;

/**
 * Represents an event that triggers a list of registered actions when invoked.
 */
@SuppressWarnings("unused")
public class Event {
    private final Array<Runnable> actions;

    /**
     * Constructs a new Event.
     */
    public Event() {
        this.actions = new Array<>();
    }

    /**
     * Adds an action to the event.
     *
     * @param action The action to be added.
     */
    public void addAction(Runnable action) {
        if (actions.contains(action)) {
            utility.Console.warning("Action you are trying to add already exists in the list!", this);
            return;
        }
        actions.add(action);
    }

    private void runAction(Runnable action) {
        action.run();
    }

    /**
     * Triggers the event, invoking all registered actions.
     */
    public void run() {
        for (int i = 0; i < actions.size(); i++) {
            runAction(actions.get(i));
        }
    }

    /**
     * Triggers the event, invoking all registered actions,
     * and clears the registered actions after execution.
     */
    public void runOnce() {
        run();
        actions.clear();
    }

    /**
     * Clears the registered actions.
     */
    public void clear(){
        actions.clear();
    }
}