package com.demo.sample.mock;

import com.demo.common.model.User;

public class Mock {

  private static Mock instance;

  public static Mock getInstance() {
    if (instance == null) {
      instance = new Mock();
    }
    return instance;
  }

  private Mock() {
  }

  public User fakeCall1() {
    return new User(45, "Michael");
  }

  public User fakeErrorCall1() throws Exception {
    fakeError();
    return fakeCall1();
  }

  public User fakeCall2() {
    return new User(31, "Marcus");
  }

  public User fakeErrorCall2() throws Exception {
    fakeError();
    return fakeCall2();
  }

  public User fakeCall3() {
    return new User(15, "Thelma");
  }

  public User fakeErrorCall3() throws Exception {
    fakeError();
    return fakeCall3();
  }

  public User fakeCall4() {
    return new User(87, "Clara");
  }

  public User fakeErrorCall4() throws Exception {
    fakeError();
    return fakeCall4();
  }

  public User fakeCall5() {
    return new User(54, "Frank");
  }

  public User fakeErrorCall5() throws Exception {
    fakeError();
    return fakeCall5();
  }

  public void pause(long milliseconds) {
    try {
      Thread.sleep(milliseconds);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  private void fakeError() throws Exception {
    throw new Exception("Fake error");
  }
}
