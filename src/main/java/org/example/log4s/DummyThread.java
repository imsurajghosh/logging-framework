package org.example.log4s;

import org.example.log4s.logger.Logger;
import org.example.log4s.logger.LoggerFactory;

public class DummyThread extends Thread {

    private static Logger log = LoggerFactory.getLogger(DummyThread.class);

    @Override
    public void run() {
        log.info(" I am %s", Thread.currentThread().getId());
    }
}