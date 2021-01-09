package org.example.log4s.logger;

import org.example.log4s.DummyThread;
import org.example.log4s.models.LoggingConfiguration;

import java.io.OutputStream;

public class LoggerFactory {

    /* maybe we can keep this unique in a thread not covering here */
    public static Logger getLogger(Class<DummyThread> dummyThreadClass) {
        LoggingConfiguration loggingConfiguration = LoggingConfiguration.configurationMap.get(dummyThreadClass.getPackage());
        if (loggingConfiguration.os != null) {
            return new AbstractLogger(dummyThreadClass) {
                @Override
                public OutputStream getOutputStream() {
                    return loggingConfiguration.os;
                }
            };
        }
        return new AbstractLogger(dummyThreadClass) {};
    }
}
