package com.tt.tutorials.java.concurrent;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * 线程间通信的几种方式
 *
 * @author Thomson Tang
 */
public class ThreadCommunicationApp {

  public static void main(String[] args) {
    //1.线程A和线程B会同时打印
//        runAAndBSimultaneously();

    //2.线程B在线程A打印完以后再打印
//        runBAfterA();

    //3.线程A先执行一部分，然后线程B开始执行，线程B执行完以后再让线程A继续
//        runAThenBThenA();

    //4.线程D在等待A，B，C执行完成后再开始执行
//        runDAfterABC();

    //5.所有线程准备好以后一起开始执行
//        runABCWhenAllReady();

    //6.子线程返回执行结果给主线程
    runTaskWithResultWorker();
  }

  //1.这种情况下，A和B是同时打印的
  public static void runAAndBSimultaneously() {
    Thread threadA = new Thread(new Runnable() {
      @Override
      public void run() {
        printNumber("thread-A");
      }
    });

    Thread threadB = new Thread(new Runnable() {
      @Override
      public void run() {
        printNumber("thread-B");
      }
    });

    threadA.start();
    threadB.start();
  }


  //2. 这种情况下，我们想要的是B在A打印完以后再打印，此处使用的Thread.join()方法来让两个线程通信
  public static void runBAfterA() {
    Thread threadA = new Thread(() -> printNumber("thread-A"));

    Thread threadB = new Thread(() -> {
      System.out.println("thread-B 开始等待 thread-A ...");
      try {
        threadA.join();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      printNumber("thread-B");
    });

    threadA.start();
    threadB.start();
  }

  //3. 粒度更细的交互方式，想要的结果是：A1，B1，B2，B3，A2，A3
  public static void runAThenBThenA() {
    //首先创建一个对象锁，线程A和线程B共享该对象锁
    Object lock = new Object();

    Thread threadA = new Thread(() -> {
      System.out.println("INFO: thread-A 等待锁...");
      //需要注意的是lock在此处是共享的，所以要进行同步
      synchronized (lock) {
        System.out.println("INFO: thread-A 得到了锁.");
        System.out.println("A1");
        try {
          System.out.println("INFO: thread-A 准备进入等待状态，放弃对lock的控制权.");
          lock.wait();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        System.out.println("INFO: thread-A 被唤醒了，重新获得了lock的控制权...");
        System.out.println("A2");
        System.out.println("A3");
      }
    });

    Thread threadB = new Thread(() -> {
      System.out.println("INFO: thread-B 等待锁...");
      synchronized (lock) {
        System.out.println("INFO: thread-B 得到了锁.");
        System.out.println("B1");
        System.out.println("B2");
        System.out.println("B3");

        System.out.println("INFO: thread-B 完成了工作，调用notify唤醒等待锁的进程.");
        lock.notify();
      }
    });

    threadA.start();
    threadB.start();
  }

  /************************************************************************************
   * 4. 现有四个线程：A，B，C，D，要求线程D在线程A，B，C执行完成后再执行，且线程A，B，C是同步运行的
   ************************************************************************************/
  public static void runDAfterABC() {
    int worker = 3;
    CountDownLatch countDownLatch = new CountDownLatch(worker);

    new Thread(() -> {
      System.out.println("D is waiting for other three threads");

      try {
        countDownLatch.await();
        System.out.println("All done, D starts working...");
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }).start();

    for (char name = 'A'; name <= 'C'; name++) {
      String threadName = String.valueOf(name);
      new Thread(() -> {
        System.out.println(threadName + " is working...");
        try {
          TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        System.out.println(threadName + "finished.");
        countDownLatch.countDown();
      }).start();
    }
  }

  /****************************************************************
   * 5. 现有三个线程A，B，C，要求当所有线程都准备好以后，再开始一起执行
   * 类似于赛跑比赛中，运动员在各自准备好以后再开始跑
   ***************************************************************/
  public static void runABCWhenAllReady() {
    int runner = 3;
    CyclicBarrier cyclicBarrier = new CyclicBarrier(runner);
    Random random = new Random();

    for (char name = 'A'; name <= 'C'; name++) {
      String runnerName = String.valueOf(name);
      new Thread(() -> {
        int periodTime = random.nextInt(10);
        System.out.println(runnerName + " is preparing for time: " + periodTime);

        try {
          TimeUnit.SECONDS.sleep(periodTime);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }

        System.out.println(runnerName + " is prepared, waiting for others");
        try {
          cyclicBarrier.await(); //当前线程准备完毕，等待其他线程准备好
        } catch (InterruptedException | BrokenBarrierException e) {
          e.printStackTrace();
        }

        System.out.println(runnerName + " starts running"); //所有线程准备好了，开始一起跑
      }).start();
    }
  }

  /*************************************************************************
   * 6. 配合使用FutureTask和Callable实现子线程完成执行任务后将结果返回给主线程
   ************************************************************************/
  public static void runTaskWithResultWorker() {
    Callable<Integer> callable = () -> {
      System.out.println("task starts");
      TimeUnit.SECONDS.sleep(1);
      int result = 0;
      for (int i = 0; i < 100; i++) {
        result += i;
      }
      System.out.println("task finished and return result");
      return result;
    };

    FutureTask<Integer> futureTask = new FutureTask<>(callable);
    new Thread(futureTask).start();

    try {
      System.out.println("Before futureTask.get()");
      System.out.println("Result: " + futureTask.get());
      System.out.println("After futureTask.get()");
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
    }
  }

  //默认每间隔1秒打印一个数，共打印三次
  private static void printNumber(String threadName) {
    for (int i = 1; i <= 3; i++) {
      try {
        TimeUnit.SECONDS.sleep(1);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      System.out.println(threadName + " print: " + i);
    }
  }
}
