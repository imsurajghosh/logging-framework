package org.example.log4s;

import org.example.log4s.logger.ConcurrentLogWriter;
import org.example.log4s.models.LogLevel;
import org.example.log4s.models.LoggingConfiguration;

import java.io.PrintStream;

public class MainApplication {

  public static void main(String[] args) throws InterruptedException {
    // create init configuration
    PrintStream out = System.out;
    LoggingConfiguration.configurationMap.put(DummyThread.class.getPackage(),
            new LoggingConfiguration(LogLevel.INFO, out));

    for (int i = 0; i < 1_000; i++) {
      new DummyThread().start();
    }

    Thread.sleep(3_000);
    ConcurrentLogWriter.executorService.shutdown();
    out.close();
  }
}