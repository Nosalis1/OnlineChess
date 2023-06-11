package utility.eventSystem;

/**
 * Represents an action that takes an argument of type T and performs some operation.
 * @param <T> the type of the argument
 */
public interface Action<T> {
    /**
     * Runs the action with the specified argument.
     * @param arg the argument for the action
     */
    void run(T arg);
}