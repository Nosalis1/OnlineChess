package util;

import java.time.LocalTime;

public class Console {
    public enum PrintType {
        Log, Game, Gui, Socket, Util, Main
    }

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_BLACK = "\u001B[30m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_CYAN = "\u001B[36m";
    private static final String ANSI_PURPLE = "\u001B[35m";
    private static final String ANSI_WHITE = "\u001B[37m";
    private static final String ANSI_BRIGHT_BLACK = "\u001B[90m";
    private static final String ANSI_BRIGHT_BLUE = "\u001B[94m";
    private static final String ANSI_BRIGHT_CYAN = "\u001B[96m";
    private static final String ANSI_BRIGHT_PURPLE = "\u001B[95m";
    private static final String ANSI_BRIGHT_WHITE = "\u001B[97m";

    private static void printTime() {
        System.out.print("[" + LocalTime.now().toString() + "]");
    }

    private static void printType(PrintType type) {
        System.out.print("[");
        switch (type){
            case Log:
                System.out.print(ANSI_WHITE + " [Log] " + ANSI_RESET);
                break;
            case Game:
                System.out.print(ANSI_BRIGHT_BLUE + " [Game] " + ANSI_RESET);
                break;
            case Gui:
                System.out.print(ANSI_BRIGHT_PURPLE + " [Gui] " + ANSI_RESET);
                break;
            case Socket:
                System.out.print(ANSI_CYAN + " [Socket] " + ANSI_RESET);
                break;
            case Util:
                System.out.print(ANSI_PURPLE + " [Util] " + ANSI_RESET);
                break;
            default:
                System.out.print(ANSI_BLUE + " [Main] " + ANSI_RESET);
        }
        System.out.print("]");
    }

    public static void message(String message, PrintType type) {
        printTime();
        printType(type);
        System.out.println(ANSI_GREEN + " [MSG] " + ANSI_RESET + " #" + message);
    }

    public static void warning(String message, PrintType type) {
        printTime();
        printType(type);
        System.out.println(ANSI_YELLOW + " [WRN] " + ANSI_RESET + " #" + message);
    }

    public static void error(String message, PrintType type) {
        printTime();
        printType(type);
        System.out.println(ANSI_RED + " [ERR] " + ANSI_RESET + " #" + message);
    }

    public static void message(String message) {
        message(message, PrintType.Log);
    }

    public static void warning(String message) {
        warning(message, PrintType.Log);
    }

    public static void error(String message) {
        error(message, PrintType.Log);
    }
}