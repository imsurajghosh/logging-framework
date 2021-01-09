package org.example.log4s.logger;

import org.example.log4s.logger.ConcurrentLogWriter;
import org.example.log4s.models.LogMessage;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.Lock;

// a thread specific object
public class ThreadContext {

  public ConcurrentLinkedQueue<LogMessage> messageConcurrentLinkedQueue =
      new ConcurrentLinkedQueue<>();

  public void add(LogMessage logMessage) {

    messageConcurrentLinkedQueue.add(logMessage);
    ConcurrentLogWriter.waitingForOthersToPush.putIfAbsent(
        Thread.currentThread().getId(), Boolean.TRUE);
    ConcurrentLogWriter.pending.incrementAndGet();

    Lock lock = ConcurrentLogWriter.lock;
    lock.lock();
    try {
      if (ConcurrentLogWriter.waitingForOthersToPush.get(Thread.currentThread().getId())
          == Boolean.FALSE) {
      } else {
        // write to queue of logwriter
        LogMessage poll = messageConcurrentLinkedQueue.poll();
        ConcurrentLogWriter.add(poll);
        ConcurrentLogWriter.waitingForOthersToPush.put(
            Thread.currentThread().getId(), Boolean.FALSE);
      }
      ConcurrentLogWriter.pending.decrementAndGet();
      ConcurrentLogWriter.condition.signal();
    } finally {
      lock.unlock();
    }
  }
}
