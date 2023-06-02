package util;

import java.time.LocalTime;

public class Console {
    public enum Color {
        RESET("\u001B[0m"),
        GREEN("\u001B[32m"),
        YELLOW("\u001B[33m"),
        RED("\u001B[31m"),
        BLACK("\u001B[30m"),
        BLUE("\u001B[34m"),
        CYAN("\u001B[36m"),
        PURPLE("\u001B[35m"),
        WHITE("\u001B[37m"),
        BRIGHT_BLACK("\u001B[90m"),
        BRIGHT_BLUE("\u001B[94m"),
        BRIGHT_CYAN("\u001B[96m"),
        BRIGHT_PURPLE("\u001B[95m"),
        BRIGHT_WHITE("\u001B[97m");

        private final String code;

        Color(String code) {
            this.code = code;
        }

        public String getCode() {
            return this.code;
        }
    }

    public static void println(String message) {
        System.out.println(message);
    }

    public static void print(String message) {
        System.out.print(message);
    }

    public static void println(String message, Color color) {
        System.out.println(color.getCode() + message + Color.RESET.getCode());
    }

    public static void print(String message, Color color) {
        System.out.print(color.getCode() + message + Color.RESET.getCode());
    }

    private static void printTime() {
        System.out.print("[" + LocalTime.now().toString() + "]");
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

    public static void message(String message, Object sender) {
        printTime();
        if (sender != null) printSender(sender);
        print("[ MSG ]", Color.GREEN);
        println("# " + message);
    }

    public static <T> void staticMessage(String message,T sender) {
        printTime();
        if (sender != null) printSender(sender);
        print("[ MSG ]", Color.GREEN);
        println("# " + message);
    }
    public static void message(String message) {
        message(message, null);
    }

    public static void warning(String message, Object sender) {
        printTime();
        if (sender != null) printSender(sender);
        print("[ WRN ]", Color.YELLOW);
        println("# " + message);
    }
    public static <T> void staticWarning(String message, T sender) {
        printTime();
        if (sender != null) printSender(sender);
        print("[ WRN ]", Color.YELLOW);
        println("# " + message);
    }

    public static void warning(String message) {
        warning(message, null);
    }

    public static void error(String message, Object sender) {
        printTime();
        if (sender != null) printSender(sender);
        print("[ ERR ]", Color.RED);
        println("# " + message);
    }

    public static <T> void staticError(String message, T sender) {
        printTime();
        if (sender != null) printSender(sender);
        print("[ ERR ]", Color.RED);
        println("# " + message);
    }

    public static void error(String message) {
        error(message, null);
    }
}