package it.unina.android.ripper.logger;

public enum ConsoleLoggerLevel {
    TRACE(0),
    DEBUG(1),
    INFO(2),
    WARNING(3),
    ERROR(4);

    private final int level;

    ConsoleLoggerLevel(int level) {
        this.level = level;
    }

    public boolean isSmallerOrEqualThan(ConsoleLoggerLevel other){
        return this.level <= other.level;
    }
}
