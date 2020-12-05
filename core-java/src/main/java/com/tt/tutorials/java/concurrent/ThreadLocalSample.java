package com.tt.tutorials.java.concurrent;

/**
 * ThreadLocal的实例展示
 *
 * @author Thomson Tang
 */
public class ThreadLocalSample {

    private static ThreadLocal<Integer> seqNum = ThreadLocal.withInitial(() -> 0);

    public int getNextNum() {
        seqNum.set(seqNum.get() + 1);
        return seqNum.get();
    }

    public static void main(String[] args) {
        ThreadLocalSample sample = new ThreadLocalSample();
        Thread thread1 = new Thread(new Task(sample));
        Thread thread2 = new Thread(new Task(sample));
        Thread thread3 = new Thread(new Task(sample));
        thread1.start();
        thread2.start();
        thread3.start();
    }

    public static class Task implements Runnable {
        private ThreadLocalSample sample;

        public Task(ThreadLocalSample sample) {
            this.sample = sample;
        }

        @Override
        public void run() {
            for (int i = 0; i < 5; i++) {
                System.out.println(Thread.currentThread().getName() + " ===> " + sample.getNextNum());
            }
        }
    }
}
