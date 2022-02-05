package com.goranrsbg.gi.service;

import com.goranrsbg.gi.etc.Formats;
import java.time.LocalTime;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 *
 * @author goranrsbg
 */
public class ClockService extends Service<Integer> {

    @Override
    protected Task<Integer> createTask() {
        Task<Integer> task = new Task() {
            @Override
            protected Integer call() throws Exception {
                while (!isCancelled()) {
                    String time = LocalTime.now().format(Formats.TIME);
                    updateMessage(time);
                }
                return 0;
            }
        };
        return task;
    }

}
