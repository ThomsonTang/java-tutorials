package com.tt.tutorials.java.concurrent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 测试一下 {@link ScheduledExecutorService#scheduleAtFixedRate(Runnable, long, long, TimeUnit)} 和 {@link
 * ScheduledExecutorService#scheduleWithFixedDelay(Runnable, long, long, TimeUnit)} 两种方式的不同。
 *
 * @author Thomson Tang
 * @version Created: 08/08/2017.
 */
public class ScheduleExample {
    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduleExample.class);

    public static void main(String[] args) {
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(2);
        executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                LOGGER.info("{} executing...", LocalTime.now());
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                LOGGER.info("{} executed.", LocalTime.now());
            }
        }, 1, 10, TimeUnit.SECONDS);
    }
}
