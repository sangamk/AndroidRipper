package it.unina.android.ripper.logger;

import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;

import static org.fusesource.jansi.Ansi.Color.*;
import static org.fusesource.jansi.Ansi.ansi;

public class ConsoleLogger {

    private static ConsoleLoggerLevel logLevel = ConsoleLoggerLevel.TRACE;

    public static void trace(String message){
        if (logLevel.isSmallerOrEqualThan(ConsoleLoggerLevel.TRACE)) {
            write(message, CYAN);
        }
    }

    public static void debug(String message){
        if (logLevel.isSmallerOrEqualThan(ConsoleLoggerLevel.DEBUG)) {
            write(message, MAGENTA);
        }
    }

    public static void info(String message)
    {
        if (logLevel.isSmallerOrEqualThan(ConsoleLoggerLevel.INFO)){
            write(message);
        }
    }

    public static void warning(String message){
        if (logLevel.isSmallerOrEqualThan(ConsoleLoggerLevel.WARNING)) {
            write(message, YELLOW);
        }
    }

    public static void error(String message){
        if (logLevel.isSmallerOrEqualThan(ConsoleLoggerLevel.ERROR)) {
            write(message, RED);
        }
    }

    public static void level(ConsoleLoggerLevel level){
        logLevel = level;
    }

    private static void write(String message, Ansi.Color color){
        AnsiConsole.systemInstall();
        System.out.println( ansi().fgBright(color).a(timeStampMessage(message)).reset() );
        AnsiConsole.systemUninstall();
    }

    private static void write(String message){
        System.out.println( timeStampMessage(message));
    }

    private static String timeStampMessage(String message){
        return "[" + System.currentTimeMillis() + "] " + message;
    }
}
