package com.scriptcentral.wartscript.Threads;


import com.scriptcentral.wartscript.TOGGLES;
import com.scriptcentral.wartscript.inventory.MutantWart;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.Display;

public class Title implements Runnable {


    public void run() {
    while (true) {

            if (Minecraft.getMinecraft().theWorld != null) {
                if (!Minecraft.getMinecraft().theWorld.isRemote) {
                    if (new TOGGLES().getWartToggle()) {
//                    Display.setTitle("Forge 1.8.9 - IGN: " + Minecraft.getMinecraft().getSession().getUsername()
//                            + " - Mutant Wart: " + new MutantWart().getWartCount() + " - Status: " + new TOGGLES().getWartToggle());
                    }
                }
            }

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    }
}
