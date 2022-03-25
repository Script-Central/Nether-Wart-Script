package com.scriptcentral.wartscript.mouse;

import com.scriptcentral.wartscript.TOGGLES;
import com.scriptcentral.wartscript.scGlobal;
import net.minecraft.client.Minecraft;

import java.security.SecureRandom;
import java.util.Random;

public class RandomLook implements Runnable {
    private float x, y;
    private float range = 10;
    private static SecureRandom random = new SecureRandom();
    private Look Look = new Look();


    public void run() {

        while (true) {
            if (new TOGGLES().getWartToggle()) {
                if (!new scGlobal().isLooking) {
                    System.out.println(x);
                    System.out.println(y);
                    Look.newLook2(500, y, x);
                    isAcceptable();
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {


                        y = Minecraft.getMinecraft().thePlayer.rotationPitch;
                        x = Minecraft.getMinecraft().thePlayer.rotationYaw;
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
            }

        }
    }
    private void isAcceptable() {



        this.x = (float) generate((int)(this.x-this.range), (int)(this.x + this.range));
        this.y = (float) generate((int)(this.y-this.range), (int)(this.y + this.range));

    }

    public static int generate(int min, int max) {
        return min + random.nextInt((max - min) + 1);
    }



}
