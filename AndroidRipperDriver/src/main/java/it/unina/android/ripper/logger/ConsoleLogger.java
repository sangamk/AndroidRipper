package it.unina.android.ripper.logger;

import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.fusesource.jansi.Ansi.Color.*;
import static org.fusesource.jansi.Ansi.ansi;

public class ConsoleLogger {

    private static ConsoleLoggerLevel logLevel = ConsoleLoggerLevel.TRACE;
    private static String baseDir;
    private static String apkName;

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
            writeLogFile("INFO | " + message);
        }
    }

    public static void warning(String message){
        if (logLevel.isSmallerOrEqualThan(ConsoleLoggerLevel.WARNING)) {
            write(message, YELLOW);
            writeLogFile("WARN | " + message);
        }
    }

    public static void error(String message){
        if (logLevel.isSmallerOrEqualThan(ConsoleLoggerLevel.ERROR)) {
            write(message, RED);
            writeLogFile("ERRO | " + message);
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

    private static void writeLogFile(String message){
        if (baseDir == null || apkName == null){
            return;
        }
        String path = baseDir + "/" + apkName + ".log";
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(path, true));
            writer.write(message);
            writer.newLine();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String timeStampMessage(String message){
        return "[" + System.currentTimeMillis() + "] " + message;
    }

    public static void outputFolder(String baseDir) {
        ConsoleLogger.baseDir = baseDir;
    }

    public static void apk(String apkName) {
        ConsoleLogger.apkName = apkName;
    }
}
