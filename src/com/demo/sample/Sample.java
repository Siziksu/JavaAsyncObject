package com.demo.sample;

import com.demo.common.AsyncObject;
import com.demo.common.model.User;
import com.demo.sample.mock.Mock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Sample {

    public void start() {
        System.out.println("-> START PROGRAM");

        new AsyncObject<User>()
                .action(() -> {
                    System.out.println("request 1 started in [" + Thread.currentThread().getName() + "] (2 seconds delay response)");
                    Mock.getInstance().pause(2000);
                    return Mock.getInstance().fakeErrorCall1();
                })
                .done(() -> System.out.println("request 1 done"))
                .run();

        new AsyncObject<User>()
                .action(() -> {
                    System.out.println("request 2 started in [" + Thread.currentThread().getName() + "] (4 seconds delay response)");
                    Mock.getInstance().pause(4000);
                    return Mock.getInstance().fakeCall2();
                })
                .success(response -> System.out.println("request 2 response: {user_id=\"" + response.id + "\"" + ", user_name=\"" + response.name + "\"} in [" + Thread.currentThread().getName() + "]"))
                .error(e -> System.out.println("request 2 error in [" + Thread.currentThread().getName() + "]"))
                .done(() -> System.out.println("request 2 done"))
                .run();

        ExecutorService executorService = Executors.newFixedThreadPool(2);

        new AsyncObject<User>()
                .action(() -> {
                    System.out.println("request 3 started in [" + Thread.currentThread().getName() + "] (3 seconds delay response)");
                    Mock.getInstance().pause(3000);
                    return Mock.getInstance().fakeCall3();
                })
                .success(response -> System.out.println("request 3 response: {user_id=\"" + response.id + "\"" + ", user_name=\"" + response.name + "\"} in [" + Thread.currentThread().getName() + "]"))
                .error(e -> System.out.println("request 3 error in [" + Thread.currentThread().getName() + "]"))
                .done(() -> System.out.println("request 3 done"))
                .run(executorService);

        new AsyncObject<Void>()
                .action(() -> {
                    System.out.println("request 4 started in [" + Thread.currentThread().getName() + "] (5 seconds delay response)");
                    Mock.getInstance().pause(5000);
                    return null;
                })
                .done(() -> System.out.println("request 4 done"))
                .run(executorService);

        AsyncObject.shutdown(executorService);

        new AsyncObject<User>()
                .action(() -> {
                    System.out.println("request 5 started in [" + Thread.currentThread().getName() + "] (4 seconds delay response)");
                    Mock.getInstance().pause(7000);
                    return Mock.getInstance().fakeErrorCall4();
                })
                .subscribe(
                        response -> System.out.println("request 5 response: {user_id=\"" + response.id + "\"" + ", user_name=\"" + response.name + "\"} in [" + Thread.currentThread().getName() + "]"),
                        e -> System.out.println("request 5 error in [" + Thread.currentThread().getName() + "]")
                )
                .run();

        new AsyncObject<User>()
                .action(() -> {
                    System.out.println("request 6 started in [" + Thread.currentThread().getName() + "] (4 seconds delay response)");
                    Mock.getInstance().pause(6000);
                    return Mock.getInstance().fakeCall5();
                })
                .subscribe(
                        response -> System.out.println("request 6 response: {user_id=\"" + response.id + "\"" + ", user_name=\"" + response.name + "\"} in [" + Thread.currentThread().getName() + "]"),
                        () -> System.out.println("request 6 done")
                )
                .run();

        System.out.println("-> END PROGRAM");
    }
}
