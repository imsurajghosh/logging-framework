package org.example.log4s.logger;

public interface Logger {

    void all(String format, Object... args);

    void trace(String format, Object... args);

    void debug(String format, Object... args);

    void info(String format, Object... args);

    void warn(String format, Object... args);

    void error(String format, Object... args);
}
