package com.demo;

import com.demo.commons.AsyncObject;
import com.demo.mock.Mock;
import com.demo.mock.UserMock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

  public static void main(String args[]) {
    new Main().start();
  }

  public void start() {
    System.out.println("-> START METHOD");

    AsyncObject<UserMock> asyncObject = new AsyncObject<UserMock>(new AsyncObject.Request<UserMock>() {

      @Override
      public UserMock request() throws Exception {
        System.out.println("request 1 started  [" + Thread.currentThread().getName() + "]");
        Mock.getInstance().pause(2000);
        return Mock.getInstance().fakeErrorCall1();
      }

      @Override
      public void onSuccess(long id, UserMock response) {
        System.out.println("request 1 response: {user_id=\"" + response.id + "\"" + ", user_name=\"" + response.name + "\"}  [" + Thread.currentThread().getName() + "]");
      }

      @Override
      public void onError(long id, Exception e) {
        System.out.println("request 1 error  [" + Thread.currentThread().getName() + "]");
      }
    });
    asyncObject.execute();

    new AsyncObject<UserMock>(new AsyncObject.Request<UserMock>() {

      @Override
      public UserMock request() throws Exception {
        System.out.println("request 2 started  [" + Thread.currentThread().getName() + "]");
        Mock.getInstance().pause(4000);
        return Mock.getInstance().fakeCall2();
      }

      @Override
      public void onSuccess(long id, UserMock response) {
        System.out.println("request 2 response: {user_id=\"" + response.id + "\"" + ", user_name=\"" + response.name + "\"}  [" + Thread.currentThread().getName() + "]");
      }

      @Override
      public void onError(long id, Exception e) {
        System.out.println("request 2 error  [" + Thread.currentThread().getName() + "]");
      }
    }).execute();

    ExecutorService executorService = Executors.newFixedThreadPool(2);

    new AsyncObject<UserMock>(new AsyncObject.Request<UserMock>() {

      @Override
      public UserMock request() throws Exception {
        System.out.println("request 3 started  [" + Thread.currentThread().getName() + "]");
        Mock.getInstance().pause(3000);
        return Mock.getInstance().fakeCall3();
      }

      @Override
      public void onSuccess(long id, UserMock response) {
        System.out.println("request 3 response: {user_id=\"" + response.id + "\"" + ", user_name=\"" + response.name + "\"}  [" + Thread.currentThread().getName() + "]");
      }

      @Override
      public void onError(long id, Exception e) {
        System.out.println("request 3 error  [" + Thread.currentThread().getName() + "]");
      }
    }).execute(executorService);

    new AsyncObject<Void>(new AsyncObject.Request<Void>() {

      @Override
      public Void request() throws Exception {
        System.out.println("request void started  [" + Thread.currentThread().getName() + "]");
        Mock.getInstance().pause(5000);
        return null;
      }

      @Override
      public void onSuccess(long id, Void response) {
        System.out.println("request void response  [" + Thread.currentThread().getName() + "]");
      }

      @Override
      public void onError(long id, Exception e) {
        System.out.println("request void error  [" + Thread.currentThread().getName() + "]");
      }
    }).execute(executorService);

    AsyncObject.shutdown(executorService);

    System.out.println("-> END METHOD");
  }
}
