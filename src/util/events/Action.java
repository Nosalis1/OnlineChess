package util.events;

public interface Action<T> {
    void run(T arg);
}
