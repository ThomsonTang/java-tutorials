package com.tt.tutorials.guava.concurrent;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.SettableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 通过使用Guava来监听Future。
 * <p>
 * 可以参考<a href="http://www.jesperdj.com/2015/09/21/listen-to-the-future-with-google-guava/">博客文章</a>
 *
 * @author Thomson Tang
 */
public class FutureTest {

  private static final Logger LOGGER = LoggerFactory.getLogger(FutureTest.class);
  private static final int DURATION_SECONDS = 10;
  private final ExecutorService executorService = Executors.newCachedThreadPool();
  private final ListeningExecutorService listeningExecutorService = MoreExecutors
      .listeningDecorator(Executors.newCachedThreadPool());

  /**
   * 使用java自带的future时，如果通过get来获取Future，那么当任务还没有执行完成时，会导致线程阻塞。
   *
   * @return Future
   */
  public Future<String> prepareFuture() {
    return executorService.submit(() -> {
      LOGGER.info("子任务开始执行....");
      for (int i = 0; i < DURATION_SECONDS; i++) {
        LOGGER.info("sub: {}", i);
        TimeUnit.SECONDS.sleep(1);
      }
      LOGGER.info("子任务执行完成。");
      return "completed";
    });
  }

  /**
   * 这里使用了SettableFuture对象
   *
   * @return
   */
  public ListenableFuture<String> prepareListenableFuture() {
    SettableFuture<String> future = SettableFuture.create();
    executorService.execute(() -> {
      LOGGER.info("子任务开始执行....");
      for (int i = 0; i < DURATION_SECONDS; i++) {
        LOGGER.info("sub: {}", i);
        try {
          TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
          LOGGER.error("error occurred: ", e);
        }
      }
      LOGGER.info("子任务执行完成。");
      //这个设置的动作是有必要的，如果没有这步操作，future的状态一直都是pending，这会导致主线程一直以为子线程还没有执行完，
      //并且一直等待下去，知道给future设置值以后，future的状态变为completed，才会触发监听器，执行callback回调。
      future.set("completed");
      LOGGER.info("子任务future的状态: {}", future);
    });
    //此时的future的状态是pending，待定
    LOGGER.info("子任务future的状态: {}", future);
    return future;
  }

  /**
   * 这里直接使用了ListeningExecutorService对象。
   *
   * @return
   */
  public ListenableFuture<String> prepareListenableFuture2() {
    return listeningExecutorService.submit(() -> {
      LOGGER.info("子任务开始执行....");
      for (int i = 0; i < DURATION_SECONDS; i++) {
        LOGGER.info("sub: {}", i);
        try {
          TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
          LOGGER.error("error occurred: ", e);
        }
      }
      LOGGER.info("子任务执行完成。");
      return "completed";
    });
  }

  public static void main(String[] args) {
    LOGGER.info("Main thread start...");
    FutureTest test = new FutureTest();
    ExecutorService callbackService = Executors.newCachedThreadPool();

    //***************Future.get()*******************
//        Future<String> future = test.prepareFuture();
//        test.doingMainTask();
//        try {
//            //这个调用本质上会阻塞主线程，因为当子线程还没执行完成时，它会等待子线程执行完成后拿到结果
//            String s = future.get();
//            LOGGER.info("the result is: {}", s);
//        } catch (InterruptedException | ExecutionException e) {
//            e.printStackTrace();
//        }

    //******************** ListenableFuture and FutureCallback ****************
    ListenableFuture<String> listenableFuture = test.prepareListenableFuture();
//        ListenableFuture<String> listenableFuture = test.prepareListenableFuture2();
    FutureCallback<String> callback = new FutureCallback<String>() {
      @Override
      public void onSuccess(@Nullable String s) {
        LOGGER.info("子任务执行结果是：{}", s);
      }

      @Override
      public void onFailure(Throwable throwable) {
        LOGGER.error("我去，子任务执行失败了：{}", throwable);
      }
    };
    Futures.addCallback(listenableFuture, callback, callbackService);
    test.doingMainTask();
    //这是为了测试给SettableFuture设置值以后才会触发callback
//        ((SettableFuture) listenableFuture).set("over");
  }

  private void doingMainTask() {
    for (int i = 0; i < 2 * DURATION_SECONDS; i++) {
      LOGGER.info("Main: {}", i);
      try {
        TimeUnit.SECONDS.sleep(1);
      } catch (InterruptedException e) {
        LOGGER.error("the error occurred in main thread. {}", e);
      }
    }
  }
}
