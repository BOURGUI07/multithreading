package com.example.playgroundproject.executor_service.sec07.concurrent_limiter;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;

@Slf4j
public class ConcurrencyLimiter implements AutoCloseable{
    private final ExecutorService executorService;
    private final Semaphore semaphore;

    public ConcurrencyLimiter(ExecutorService executorService, int limit) {
        this.executorService = executorService;
        this.semaphore = new Semaphore(limit);
    }

    public <T> Future<T> execute(Callable<T> callable) {
        return executorService.submit(()-> wrapCallable(callable));
    }

    private <T> T wrapCallable(Callable<T> callable) {
        try{
            semaphore.acquire();
            return callable.call();
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
