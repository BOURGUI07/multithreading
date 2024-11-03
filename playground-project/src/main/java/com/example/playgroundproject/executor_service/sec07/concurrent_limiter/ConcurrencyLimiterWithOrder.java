package com.example.playgroundproject.executor_service.sec07.concurrent_limiter;

import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.*;

@Slf4j
public class ConcurrencyLimiterWithOrder implements AutoCloseable{
    private final ExecutorService executorService;
    private final Semaphore semaphore;
    private final Queue<Callable<?>> queue;

    public ConcurrencyLimiterWithOrder(ExecutorService executorService, int limit) {
        this.executorService = executorService;
        this.semaphore = new Semaphore(limit);
        this.queue = new ConcurrentLinkedQueue<>();
    }

    public <T> Future<T> execute(Callable<T> callable) {
        queue.add(callable);
        return executorService.submit(()-> executeTask(callable));
    }

    private <T> T executeTask(Callable<T> callable) {
        try{
            semaphore.acquire();
            return (T) queue.poll().call();
        }catch (Exception e) {
            log.error(e.getMessage(), e);
        }finally {
            semaphore.release();
        }
        return null;
    }

    @Override
    public void close() throws Exception {
        executorService.close();
    }
}
