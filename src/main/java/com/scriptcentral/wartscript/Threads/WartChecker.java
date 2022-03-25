package com.scriptcentral.wartscript.Threads;


import com.scriptcentral.wartscript.ScriptLogger;
import com.scriptcentral.wartscript.TOGGLES;
import com.scriptcentral.wartscript.debuglog.Debug;
import com.scriptcentral.wartscript.enviroment.BlockGetter;
import com.scriptcentral.wartscript.scGlobal;

import java.util.concurrent.TimeUnit;

import static sun.awt.geom.Crossings.debug;

public class WartChecker implements Runnable {

    private boolean hadWartLastCheck = false;

    public void run() {
        BlockGetter blockGetter = new BlockGetter();
        while (true) {

                    if (blockGetter.getBlocks(10).size() == 0 && !hadWartLastCheck && new scGlobal().finishedReconnect == true) {
                        if (new TOGGLES().getWartToggle()) {
                            new ScriptLogger().log("It appears you don't have any farmable material near you, huh...?",
                                    new ScriptLogger().type.WARNING);
                            new TOGGLES().toggleWart();
                            new Debug().log("I've just killed the script because there was no farmable material nearby.");
                        }
                    }
                    try {
                        TimeUnit.SECONDS.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    hadWartLastCheck = (blockGetter.getBlocks(10).size() > 0);
        }
    }

}
