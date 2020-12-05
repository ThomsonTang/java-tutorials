package com.tt.java.tutorials.curator;

/**
 * 类说明
 *
 * @author Thomson Tang
 */
public class ZookeeperProperties {

  private String connectString;

  private int retryCount;
  private int elapsedTimeMs;
  private int sessionTimeoutMs;
  private int connectionTimeoutMs;

  public String getConnectString() {
    return connectString;
  }

  public void setConnectString(String connectString) {
    this.connectString = connectString;
  }

  public int getRetryCount() {
    return retryCount;
  }

  public void setRetryCount(int retryCount) {
    this.retryCount = retryCount;
  }

  public int getElapsedTimeMs() {
    return elapsedTimeMs;
  }

  public void setElapsedTimeMs(int elapsedTimeMs) {
    this.elapsedTimeMs = elapsedTimeMs;
  }

  public int getSessionTimeoutMs() {
    return sessionTimeoutMs;
  }

  public void setSessionTimeoutMs(int sessionTimeoutMs) {
    this.sessionTimeoutMs = sessionTimeoutMs;
  }

  public int getConnectionTimeoutMs() {
    return connectionTimeoutMs;
  }

  public void setConnectionTimeoutMs(int connectionTimeoutMs) {
    this.connectionTimeoutMs = connectionTimeoutMs;
  }
}
