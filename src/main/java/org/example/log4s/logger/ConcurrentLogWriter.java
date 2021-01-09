package org.example.log4s.logger;

import org.example.log4s.models.LogMessage;

import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConcurrentLogWriter {

    public static volatile AtomicInteger pending ;
    public static Lock lock;
    public static Condition condition;
    public static ConcurrentMap<Long, Boolean> waitingForOthersToPush;
    public static ExecutorService executorService;

    static {
        waitingForOthersToPush = new ConcurrentHashMap<>();
        pending = new AtomicInteger(0);
        lock = new ReentrantLock();
        condition = lock.newCondition();
        executorService = Executors.newSingleThreadExecutor();
        executorService.submit(writeToLogs());
    }

    public static ConcurrentSkipListMap<Long, LogMessage> log = new ConcurrentSkipListMap<>();

    static void add(LogMessage logMessage) {
        log.put(logMessage.time, logMessage);
    }

    static LogMessage remove() {
        Map.Entry<Long, LogMessage> lastEntry = log.firstEntry();
        log.remove(lastEntry.getKey());
        return lastEntry.getValue();
    }

    static Runnable writeToLogs() {
    return () -> {

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        while (!Thread.currentThread().isInterrupted()) {
        lock.lock();
          try {
          while (pending.get() != 0) {
            condition.await();
          }
          LogMessage remove = remove();
          remove.writeToStream();
          waitingForOthersToPush.putIfAbsent(remove.threadId, Boolean.FALSE);
        } catch (InterruptedException e) {
          e.printStackTrace();
        } finally {
          lock.unlock();
        }
      }
    };
    }
}
