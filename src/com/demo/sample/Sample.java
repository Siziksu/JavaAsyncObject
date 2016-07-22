package com.demo.sample;

import com.demo.common.AsyncObject;
import com.demo.common.model.User;
import com.demo.sample.mock.Mock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Sample {

    private ExecutorService executorService;

    public void start() {
        System.out.println("-> START PROGRAM");
        request1();
        request2();
        executorService = Executors.newFixedThreadPool(3);
        request3();
        request4();
        request5();
        AsyncObject.shutdownExecutor(executorService);
        request6();
        System.out.println("-> END PROGRAM");
    }

    private void request1() {
        new AsyncObject<User>()
                .action(() -> {
                    System.out.println("request 1 started in [" + Thread.currentThread().getName() + "] (2 seconds delay response)");
                    Mock.getInstance().pause(2000);
                    return Mock.getInstance().fakeErrorCall1();
                })
                .done(() -> System.out.println("request 1 done"))
                .run();
    }

    private void request2() {
        new AsyncObject<User>()
                .action(() -> {
                    System.out.println("request 2 started in [" + Thread.currentThread().getName() + "] (4 seconds delay response)");
                    Mock.getInstance().pause(4000);
                    return Mock.getInstance().fakeCall2();
                })
                .done(() -> System.out.println("request 2 done"))
                .subscribe(
                        response -> System.out.println("request 2 response: {user_id=\"" + response.id + "\"" + ", user_name=\"" + response.name + "\"} in [" + Thread.currentThread().getName() + "]"),
                        e -> System.out.println("request 2 error in [" + Thread.currentThread().getName() + "]")
                );
    }

    private void request3() {
        new AsyncObject<User>()
                .executeInto(executorService)
                .action(() -> {
                    System.out.println("request 3 started in [" + Thread.currentThread().getName() + "] (3 seconds delay response)");
                    Mock.getInstance().pause(3000);
                    return Mock.getInstance().fakeCall3();
                })
                .done(() -> System.out.println("request 3 done"))
                .subscribe(
                        response -> System.out.println("request 3 response: {user_id=\"" + response.id + "\"" + ", user_name=\"" + response.name + "\"} in [" + Thread.currentThread().getName() + "]"),
                        e -> System.out.println("request 3 error in [" + Thread.currentThread().getName() + "]"));
    }

    private void request4() {
        new AsyncObject<Void>()
                .executeInto(executorService)
                .action(() -> {
                    System.out.println("request 4 started in [" + Thread.currentThread().getName() + "] and won't have response feedback (5 seconds process)");
                    Mock.getInstance().pause(5000);
                    return null;
                })
                .run();
    }

    private void request5() {
        new AsyncObject<User>()
                .executeInto(executorService)
                .action(() -> {
                    System.out.println("request 5 started in [" + Thread.currentThread().getName() + "] (4 seconds delay response)");
                    Mock.getInstance().pause(7000);
                    return Mock.getInstance().fakeErrorCall4();
                })
                .subscribe(
                        response -> System.out.println("request 5 response: {user_id=\"" + response.id + "\"" + ", user_name=\"" + response.name + "\"} in [" + Thread.currentThread().getName() + "]"),
                        e -> System.out.println("request 5 error in [" + Thread.currentThread().getName() + "]")
                );
    }

    private void request6() {
        new AsyncObject<User>()
                .action(() -> {
                    System.out.println("request 6 started in [" + Thread.currentThread().getName() + "] (4 seconds delay response)");
                    Mock.getInstance().pause(6000);
                    return Mock.getInstance().fakeCall5();
                })
                .done(() -> System.out.println("request 6 done"))
                .subscribe(this::afterRequest6);
    }

    private void afterRequest6(User user) {
        new AsyncObject<Boolean>()
                .action(() -> {
                    System.out.println("request 7 started in [" + Thread.currentThread().getName() + "]");
                    return user.name.equals("Michael");
                })
                .done(() -> System.out.println("request 7 done"))
                .subscribe(response -> System.out.println("request 7 response (" + user.name + " == Michael) " + response));
    }
}
