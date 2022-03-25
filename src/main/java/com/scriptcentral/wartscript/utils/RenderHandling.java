package com.scriptcentral.wartscript.utils;


import com.scriptcentral.wartscript.CustomEventHandler;
import com.scriptcentral.wartscript.ScriptLogger;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.RenderTickEvent;

public class RenderHandling {
    public static int renderCalls = 0;
    public static boolean disableRender = false;

    public void disableRender() {
        disableRender = true;
        new ScriptLogger().log(
                "Rendering has been disabled. This includes your GUI and any menus. Press the same key to restore.",
                new ScriptLogger().type.INFO);

    }

    public void enableRender() {
        Minecraft.getMinecraft().skipRenderWorld = false;
        new CustomEventHandler().shouldRender = true;

        new ScriptLogger().log("Rendering has been enabled. Press the same key to disable.",
                new ScriptLogger().type.INFO);
    }

    @SubscribeEvent
    public void renderingNow(RenderTickEvent event) {
        if (Minecraft.getMinecraft().thePlayer != null) {
            if (renderCalls == 2) {
                Minecraft.getMinecraft().skipRenderWorld = true;
                new CustomEventHandler().shouldRender = false;
                renderCalls = 0;
                disableRender = false;
            }
            if (disableRender) {
                renderCalls++;
            }

        }
    }

}
