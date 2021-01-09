package org.example.log4s.logger;

import org.example.log4s.models.LoggingConfiguration;
import org.example.log4s.models.LogLevel;
import org.example.log4s.models.LogMessage;

import java.io.OutputStream;

public abstract class AbstractLogger implements Logger {

  private static ThreadLocal<ThreadContext> threadContext = new ThreadLocal<>();

  public static ThreadContext getInstance() {
    if (threadContext.get() == null) {
      threadContext.set(new ThreadContext());
    }
    return threadContext.get();
  }

  private final Class<?> klazz;

  protected AbstractLogger(Class<?> klazz) {
    this.klazz = klazz;
  }

  private boolean shouldWrite(LogLevel logLeveL) {
    LoggingConfiguration loggingConfiguration =
        LoggingConfiguration.configurationMap.get(klazz.getPackage());
    return loggingConfiguration.loglevel.compareTo(logLeveL) <= 0;
  }

  @Override
  public void all(String format, Object... args) {
    if (shouldWrite(LogLevel.ALL)) {
      write(new LogMessage(String.format(format, args), LogLevel.ALL));
    }
  }

  @Override
  public void trace(String format, Object... args) {
    if (shouldWrite(LogLevel.TRACE)) {
      write(new LogMessage(String.format(format, args), LogLevel.TRACE));
    }
  }

  @Override
  public void debug(String format, Object... args) {
    if (shouldWrite(LogLevel.DEBUG)) {
      write(new LogMessage(String.format(format, args), LogLevel.DEBUG));
    }
  }

  @Override
  public void info(String format, Object... args) {
    if (shouldWrite(LogLevel.INFO)) {
      write(new LogMessage(String.format(format, args), LogLevel.INFO));
    }
  }

  @Override
  public void warn(String format, Object... args) {
    if (shouldWrite(LogLevel.WARN)) {
      write(new LogMessage(String.format(format, args), LogLevel.WARN));
    }
  }

  @Override
  public void error(String format, Object... args) {
    if (shouldWrite(LogLevel.ERROR)) {
      write(new LogMessage(String.format(format, args), LogLevel.ERROR));
    }
  }

  public OutputStream getOutputStream() {
    return System.out;
  }

  private void write(LogMessage logMessage) {
    logMessage.outputStream = getOutputStream();
    ThreadContext instance = getInstance();
    instance.add(logMessage);
  }
}
