package com._Project.MySystem.util;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class BookingManager {
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public void executeWriter(Runnable writerTask) {
        lock.writeLock().lock();
        try {
            writerTask.run();
        } finally {
            lock.writeLock().unlock();
        }
    }
    public void executeReader(Runnable readerTask) {
        lock.readLock().lock();
        try {
            readerTask.run();
        } finally {
            lock.readLock().unlock();
        }
    }
}

