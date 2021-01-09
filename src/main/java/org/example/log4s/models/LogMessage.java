package org.example.log4s.models;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;

public class LogMessage {

    public Long time;
    public Long threadId;
    public String message;
    public LogLevel logLevel;
    public OutputStream outputStream;

    public LogMessage(String message, LogLevel logLevel) {
        this.message = message;
        this.logLevel = logLevel;
        this.time = System.nanoTime();
        this.threadId = Thread.currentThread().getId();
    }

    public void setOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public void writeToStream() {
        PrintWriter printWriter = new PrintWriter(this.outputStream);
        printWriter.println(this.logLevel.name()
                .concat(" ")
                .concat(new Date(this.time).toString())
                .concat(" ")
                .concat(this.message));
        printWriter.flush();
    }
}