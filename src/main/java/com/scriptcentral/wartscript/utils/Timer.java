package com.scriptcentral.wartscript.utils;

import java.util.concurrent.TimeUnit;

public class Timer {
    public int timer(long ms) {

        try {
            TimeUnit.MILLISECONDS.sleep(ms);

            return 3;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 1;

    }

}
