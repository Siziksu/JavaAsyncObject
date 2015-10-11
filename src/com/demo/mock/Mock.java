package com.demo.mock;

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

  public UserMock fakeCall1() {
    return new UserMock(45, "Ford");
  }

  public UserMock fakeErrorCall1() throws Exception {
    fakeError();
    return fakeCall1();
  }

  public UserMock fakeCall2() {
    return new UserMock(31, "Marcus");
  }

  public UserMock fakeErrorCall2() throws Exception {
    fakeError();
    return fakeCall2();
  }

  public UserMock fakeCall3() {
    return new UserMock(15, "Thelma");
  }

  public UserMock fakeErrorCall3() throws Exception {
    fakeError();
    return fakeCall3();
  }

  public void pause(long milliseconds) {
    try {
      Thread.sleep(milliseconds);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public void fakeError() throws Exception {
    throw new Exception("Fake error");
  }
}
