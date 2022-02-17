package com.goranrsbg.gi.etc;

/**
 *
 * @author goranrsbg
 */
public class SharedData {

    private static final SharedData INSTANCE = new SharedData();

    /**
     * Time app started in milliseconds.
     */
    public final long TIME_STARTED;

    private SharedData() {
        TIME_STARTED = System.currentTimeMillis();
    }

    public static SharedData get() {
        return INSTANCE;
    }

}
