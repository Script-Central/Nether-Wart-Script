package com.scriptcentral.wartscript.Threads;


import com.scriptcentral.wartscript.security.SecurityHandler;

import java.util.concurrent.TimeUnit;

public class AuthCheck implements Runnable {
    public void run() {
        while (true) {

            try {
                TimeUnit.MINUTES.sleep(15);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
