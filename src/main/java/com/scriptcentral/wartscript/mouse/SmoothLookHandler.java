package com.scriptcentral.wartscript.mouse;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.scriptcentral.wartscript.PermData;
import com.scriptcentral.wartscript.utils.Utils;
import net.minecraft.client.Minecraft;

public class SmoothLookHandler {
    private Look look = new Look();
    private boolean isStillLooking = true;
    private PermData permdat;
    private boolean started = false;

    public boolean lookTo(int ms, float pitch, float yaw) {

        if (!started) {
            started = true;
            isStillLooking = true;
            doLook(ms, pitch, yaw);
        }
        if (started) {
            if (!isStillLooking) {
                started = false;
                Minecraft.getMinecraft().thePlayer.rotationYaw = yaw;
                Minecraft.getMinecraft().thePlayer.rotationPitch = pitch;
                return true;

            }
        }
        return false;
    }

    private void doLook(int ms, float pitch, float yaw) {
        ExecutorService exec = Executors.newFixedThreadPool(1);
        exec.submit(() -> {
            while (isStillLooking) {
                isStillLooking = look.smoothLook(ms, pitch, yaw);

                new Utils().burn(look.leftOverTime);

            }
        });
    }
}