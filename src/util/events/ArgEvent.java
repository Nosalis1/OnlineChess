package util.events;

import util.Console;

public class ArgEvent<T> extends util.Array<Action<T>> {

    @Override
    public void add(Action<T> action) {
        if (contains(action)) {
            util.Console.warning("Action you are trying to add already exist to list!", Console.PrintType.Util);
            return;
        }
        super.add(action);
    }

    private void run(Action<T> action, T arg) {
        action.run(arg);
    }

    private void run(final int index, T arg) {
        run(get(index), arg);
    }

    public void run(T arg) {
        for (int i = 0; i < size(); i++)
            run(i, arg);
    }

    public void runOnce(T arg) {
        run(arg);
        clear();
    }
}