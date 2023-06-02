package util;

import java.time.LocalTime;

/**
 * The Console class provides utility methods for printing messages to the console with different colors and timestamps.
 */
public class Console {

    /**
     * An enumeration of console colors.
     */
    public enum Color {
        RESET("\u001B[0m"),
        GREEN("\u001B[32m"),
        YELLOW("\u001B[33m"),
        RED("\u001B[31m"),
        @SuppressWarnings("unused")
        BLACK("\u001B[30m"),
        BLUE("\u001B[34m"),
        CYAN("\u001B[36m"),
        PURPLE("\u001B[35m"),
        @SuppressWarnings("unused")
        WHITE("\u001B[37m"),
        BRIGHT_BLACK("\u001B[90m"),
        @SuppressWarnings("unused")
        BRIGHT_BLUE("\u001B[94m"),
        @SuppressWarnings("unused")
        BRIGHT_CYAN("\u001B[96m"),
        @SuppressWarnings("unused")
        BRIGHT_PURPLE("\u001B[95m"),
        @SuppressWarnings("unused")
        BRIGHT_WHITE("\u001B[97m");

        private final String code;

        Color(String code) {
            this.code = code;
        }

        /**
         * Returns the ANSI escape code for the color.
         *
         * @return The ANSI escape code
         */
        public String getCode() {
            return this.code;
        }
    }

    /**
     * Prints a message followed by a newline character.
     *
     * @param message The message to be printed
     */
    public static void println(String message) {
        System.out.println(message);
    }

    /**
     * Prints a message without a newline character.
     *
     * @param message The message to be printed
     */
    @SuppressWarnings("unused")
    public static void print(String message) {
        System.out.print(message);
    }

    /**
     * Prints a colored message followed by a newline character.
     *
     * @param message The message to be printed
     * @param color   The color of the message
     */
    @SuppressWarnings("unused")
    public static void println(String message, Color color) {
        System.out.println(color.getCode() + message + Color.RESET.getCode());
    }

    /**
     * Prints a colored message without a newline character.
     *
     * @param message The message to be printed
     * @param color   The color of the message
     */
    public static void print(String message, Color color) {
        System.out.print(color.getCode() + message + Color.RESET.getCode());
    }

    private static void printTime() {
        System.out.print("[" + LocalTime.now() + "]");
    }

    private static String getColorCode(String packageName) {
        return switch (packageName) {
            case "audio", "audio.sfx" -> Color.BLUE.getCode();
            case "game", "game.users" -> Color.YELLOW.getCode();
            case "gui", "gui.images" -> Color.PURPLE.getCode();
            case "socket", "socket.events", "socket.packages" -> Color.CYAN.getCode();
            case "util", "util.events" -> Color.BRIGHT_BLACK.getCode();
            default -> Color.RED.getCode();
        };
    }

    private static void printSender(Object sender) {
        System.out.print("[ " + getColorCode(sender.getClass().getPackageName()) + sender.getClass().getName() + Color.RESET.getCode() + " ]");
    }

    /**
     * Prints a message with a timestamp and optional sender information.
     *
     * @param message The message to be printed
     * @param sender  The sender object (can be null)
     */
    public static void message(String message, Object sender) {
        printTime();
        if (sender != null) printSender(sender);
        print("[ MSG ]", Color.GREEN);
        println("# " + message);
    }

    /**
     * Prints a message with a timestamp, optional sender information, and a generic type parameter.
     *
     * @param message The message to be printed
     * @param sender  The sender object (can be null)
     * @param <T>     The type of the sender object
     */
    @SuppressWarnings("unused")
    public static <T> void staticMessage(String message, T sender) {
        printTime();
        if (sender != null) printSender(sender);
        print("[ MSG ]", Color.GREEN);
        println("# " + message);
    }

    /**
     * Prints a message with a timestamp.
     *
     * @param message The message to be printed
     */
    public static void message(String message) {
        message(message, null);
    }

    /**
     * Prints a warning message with a timestamp and optional sender information.
     *
     * @param message The warning message to be printed
     * @param sender  The sender object (can be null)
     */
    public static void warning(String message, Object sender) {
        printTime();
        if (sender != null) printSender(sender);
        print("[ WRN ]", Color.YELLOW);
        println("# " + message);
    }

    /**
     * Prints a warning message with a timestamp, optional sender information, and a generic type parameter.
     *
     * @param message The warning message to be printed
     * @param sender  The sender object (can be null)
     * @param <T>     The type of the sender object
     */
    @SuppressWarnings("unused")
    public static <T> void staticWarning(String message, T sender) {
        printTime();
        if (sender != null) printSender(sender);
        print("[ WRN ]", Color.YELLOW);
        println("# " + message);
    }

    /**
     * Prints a warning message with a timestamp.
     *
     * @param message The warning message to be printed
     */
    @SuppressWarnings("unused")
    public static void warning(String message) {
        warning(message, null);
    }

    /**
     * Prints an error message with a timestamp and optional sender information.
     *
     * @param message The error message to be printed
     * @param sender  The sender object (can be null)
     */
    public static void error(String message, Object sender) {
        printTime();
        if (sender != null) printSender(sender);
        print("[ ERR ]", Color.RED);
        println("# " + message);
    }

    /**
     * Prints an error message with a timestamp, optional sender information, and a generic type parameter.
     *
     * @param message The error message to be printed
     * @param sender  The sender object (can be null)
     * @param <T>     The type of the sender object
     */
    @SuppressWarnings("unused")
    public static <T> void staticError(String message, T sender) {
        printTime();
        if (sender != null) printSender(sender);
        print("[ ERR ]", Color.RED);
        println("# " + message);
    }

    /**
     * Prints an error message with a timestamp.
     *
     * @param message The error message to be printed
     */
    public static void error(String message) {
        error(message, null);
    }
}