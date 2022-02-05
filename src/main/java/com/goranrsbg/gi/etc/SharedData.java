package com.goranrsbg.gi.etc;

/**
 *
 * @author goranrsbg
 */
public class SharedData {

    private static SharedData INSTANCE;

    /**
     * Time app started in milliseconds.
     */
    public final long TIME_STARTED;

    private SharedData() {
        TIME_STARTED = System.currentTimeMillis();
    }

    public static SharedData get() {
        if (INSTANCE == null) {
            INSTANCE = new SharedData();
        }
        return INSTANCE;
    }

}
