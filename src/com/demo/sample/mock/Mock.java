package com.demo.sample.mock;

import com.demo.common.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Mock class.
 */
public class Mock {

    private static Mock instance;

    /**
     * Gets and instance of {@link Mock}
     *
     * @return {@link Mock}
     */
    public static Mock getInstance() {
        if (instance == null) {
            instance = new Mock();
        }
        return instance;
    }

    private Mock() {
        // Constructor
    }

    /**
     * Fake call 1.
     *
     * @return User
     */
    public User fakeCall1() {
        return new User(45, "Michael");
    }

    /**
     * Fake error call 1.
     *
     * @return User
     */
    public User fakeErrorCall1() throws Exception {
        fakeError();
        return fakeCall1();
    }

    /**
     * Fake call 2.
     *
     * @return User
     */
    public User fakeCall2() {
        return new User(31, "Marcus");
    }

    /**
     * Fake error call 2.
     *
     * @return User
     */
    public User fakeErrorCall2() throws Exception {
        fakeError();
        return fakeCall2();
    }

    /**
     * Fake call 3.
     *
     * @return User
     */
    public User fakeCall3() {
        return new User(15, "Thelma");
    }

    /**
     * Fake error call 3.
     *
     * @return User
     */
    public User fakeErrorCall3() throws Exception {
        fakeError();
        return fakeCall3();
    }

    /**
     * Fake call 4.
     *
     * @return User
     */
    public User fakeCall4() {
        return new User(87, "Clara");
    }

    /**
     * Fake error call 4.
     *
     * @return User
     */
    public User fakeErrorCall4() throws Exception {
        fakeError();
        return fakeCall4();
    }

    /**
     * Fake call 5.
     *
     * @return List<User>
     */
    public List<User> fakeCall5() throws Exception {
        List<User> list = new ArrayList<>();
        list.add(new User(23, "Samuel"));
        list.add(new User(47, "Lara"));
        list.add(new User(95, "Carla"));
        return list;
    }

    /**
     * Fake error call 5.
     *
     * @return List<User>
     */
    public List<User> fakeErrorCall5() throws Exception {
        fakeError();
        return fakeCall5();
    }

    /**
     * Sleeps a thread a number of milliseconds.
     *
     * @param milliseconds the milliseconds
     */
    public void pause(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Fake error.
     *
     * @throws Exception
     */
    private void fakeError() throws Exception {
        throw new Exception("Fake error");
    }
}
