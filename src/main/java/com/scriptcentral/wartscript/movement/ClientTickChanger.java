package com.scriptcentral.wartscript.movement;

import net.minecraft.client.Minecraft;
import net.minecraft.util.Timer;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import java.lang.reflect.Field;
//WARNING
//DANGEROUS
//RESULTS IN A BAN
//USE AT OWN RISK!!
public class ClientTickChanger {
    private Field timer;
    private int oldTps;
    public float setClientTicks(float tps) {
        int retval = oldTps;
        try {
            this.timer.set(Minecraft.getMinecraft(), new Timer(tps));
            oldTps = (int) tps;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return retval;
    }
    public ClientTickChanger() {
        this.timer = ReflectionHelper.findField(Minecraft.class, "field_71428_T", "timer");
        this.timer.setAccessible(true);
        try {
            this.timer.set(Minecraft.getMinecraft(), new Timer(20.0F));
            this.oldTps = 20;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
