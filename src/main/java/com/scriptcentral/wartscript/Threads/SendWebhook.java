package com.scriptcentral.wartscript.Threads;


import com.scriptcentral.wartscript.CustomEventHandler;
import com.scriptcentral.wartscript.PermData;
import com.scriptcentral.wartscript.TOGGLES;
import com.scriptcentral.wartscript.inventory.MutantWart;
import com.scriptcentral.wartscript.utils.DiscordWebhook;
import net.minecraft.client.Minecraft;

import java.util.concurrent.TimeUnit;

public class SendWebhook implements Runnable {

    public void run() {
        while (true) {
            System.out.println("yeti");
            if (Minecraft.getMinecraft().thePlayer != null) {
                System.out.println("yeet");
                System.out.println(new MutantWart().getWartCount());
                try {
                    new DiscordWebhook().sendWebhook(new CustomEventHandler().reconnects, new CustomEventHandler().startTime, new TOGGLES());
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
            try {
                TimeUnit.MINUTES.sleep(new PermData().webhooktimer);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
