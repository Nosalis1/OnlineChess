package util.events;

/**
 * Represents an event that accepts an argument of type T and invokes registered actions when triggered.
 *
 * @param <T> The type of the argument.
 */
@SuppressWarnings("unused")
public class ArgumentEvent<T> {
    private final util.Array<Action<T>> actions;

    /**
     * Constructs a new ArgumentEvent.
     */
    public ArgumentEvent() {
        this.actions = new util.Array<>();
    }

    /**
     * Adds an action to the event.
     *
     * @param action The action to be added.
     */
    public void add(Action<T> action) {
        if (actions.contains(action)) {
            util.Console.warning("Action you are trying to add already exists in the list!", this);
            return;
        }
        actions.add(action);
    }

    private void run(Action<T> action, T arg) {
        action.run(arg);
    }

    /**
     * Triggers the event, invoking all registered actions with the specified argument.
     *
     * @param arg The argument to be passed to the actions.
     */
    public void run(T arg) {
        for (int i = 0; i < actions.size(); i++) {
            run(actions.get(i), arg);
        }
    }

    /**
     * Triggers the event, invoking all registered actions with the specified argument,
     * and clears the registered actions after execution.
     *
     * @param arg The argument to be passed to the actions.
     */
    public void runOnce(T arg) {
        run(arg);
        actions.clear();
    }

    /**
     * Clears the registered actions.
     */
    public void clear() {
        actions.clear();
    }
}