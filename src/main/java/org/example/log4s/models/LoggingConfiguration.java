package org.example.log4s.models;


import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class LoggingConfiguration {

    // initialize this from globalconfig
    public static Map<Package, LoggingConfiguration> configurationMap = new HashMap<>();

    public LogLevel loglevel = LogLevel.INFO;
    public OutputStream os = System.out;

    public LoggingConfiguration(LogLevel loglevel, OutputStream os) {
        this.loglevel = loglevel;
        this.os = os;
    }
}
