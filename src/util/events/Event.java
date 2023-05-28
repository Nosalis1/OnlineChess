package util.events;

import util.Console;

public class Event extends util.Array<Runnable> {

    @Override
    public void add(Runnable action) {
        if (contains(action)) {
            util.Console.warning("Action you are trying to add already exist to list!", Console.PrintType.Util);
            return;
        }
        super.add(action);
    }

    private void run(Runnable action) {
        action.run();
    }

    private void run(final int index) {
        run(get(index));
    }

    public void run() {
        for (int i = 0; i < size(); i++)
            run(i);
    }

    public void runOnce() {
        run();
        clear();
    }
}