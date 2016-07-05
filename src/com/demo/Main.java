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

    private void start() {
        System.out.println("-> START METHOD");

        new AsyncObject<UserMock>()
                .action(new AsyncObject.Action<UserMock>() {
                    @Override
                    public UserMock action() throws Exception {
                        System.out.println("request 1 started in [" + Thread.currentThread().getName() + "] (2 seconds delay response)");
                        Mock.getInstance().pause(2000);
                        return Mock.getInstance().fakeErrorCall1();
                    }

                    @Override
                    public void done() {
                        System.out.println("request 1 done");
                    }
                })
                .execute();

        new AsyncObject<UserMock>()
                .action(new AsyncObject.Action<UserMock>() {
                    @Override
                    public UserMock action() throws Exception {
                        System.out.println("request 2 started in [" + Thread.currentThread().getName() + "] (4 seconds delay response)");
                        Mock.getInstance().pause(4000);
                        return Mock.getInstance().fakeCall2();
                    }

                    @Override
                    public void done() {
                        System.out.println("request 2 done");
                    }
                })
                .success(new AsyncObject.Success<UserMock>() {

                    @Override
                    public void success(UserMock response) {
                        System.out.println("request 2 response: {user_id=\"" + response.id + "\"" + ", user_name=\"" + response.name + "\"}  [" + Thread.currentThread().getName() + "]");
                    }
                })
                .error(new AsyncObject.Error() {

                    @Override
                    public void error(Exception e) {
                        System.out.println("request 2 error  [" + Thread.currentThread().getName() + "]");
                    }
                })
                .execute();

        ExecutorService executorService = Executors.newFixedThreadPool(2);

        new AsyncObject<UserMock>()
                .action(new AsyncObject.Action<UserMock>() {
                    @Override
                    public UserMock action() throws Exception {
                        System.out.println("request 3 started in [" + Thread.currentThread().getName() + "] (3 seconds delay response)");
                        Mock.getInstance().pause(3000);
                        return Mock.getInstance().fakeCall3();
                    }

                    @Override
                    public void done() {
                        System.out.println("request 3 done");
                    }
                })
                .success(new AsyncObject.Success<UserMock>() {

                    @Override
                    public void success(UserMock response) {
                        System.out.println("request 3 response: {user_id=\"" + response.id + "\"" + ", user_name=\"" + response.name + "\"}  [" + Thread.currentThread().getName() + "]");
                    }
                })
                .error(new AsyncObject.Error() {

                    @Override
                    public void error(Exception e) {
                        System.out.println("request 3 error  [" + Thread.currentThread().getName() + "]");
                    }
                })
                .execute(executorService);

        new AsyncObject<Void>()
                .action(new AsyncObject.Action<Void>() {
                    @Override
                    public Void action() throws Exception {
                        System.out.println("request 4 started in [" + Thread.currentThread().getName() + "] (5 seconds delay response)");
                        Mock.getInstance().pause(5000);
                        return null;
                    }

                    @Override
                    public void done() {
                        System.out.println("request 4 done");
                    }
                })
                .execute(executorService);

        AsyncObject.shutdown(executorService);

        System.out.println("-> END METHOD");
    }
}
