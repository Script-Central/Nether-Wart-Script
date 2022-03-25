package com.scriptcentral.wartscript.proxy;

import org.lwjgl.input.Keyboard;

import com.scriptcentral.wartscript.security.SecurityHandler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {

    public static KeyBinding[] keyBindings = new KeyBinding[3];
    public static KeyBinding[] keyBindingsPerf = new KeyBinding[4];
    public static EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
    public SecurityHandler secHand = new SecurityHandler();

    @Override
    public void preInit(FMLPreInitializationEvent preEvent) {

        super.preInit(preEvent);
        if (!secHand.getScriptPerfState()) {
            keyBindings[0] = new KeyBinding("Toggle script", Keyboard.KEY_K, "Script Central");
            keyBindings[1] = new KeyBinding("Open Config Gui", Keyboard.KEY_J, "Script Central");
            keyBindings[2] = new KeyBinding("Unfocus Minecraft", Keyboard.KEY_H, "Script Central");
        } else {
            keyBindingsPerf[0] = new KeyBinding("Toggle script", Keyboard.KEY_K, "Script Central");
            keyBindingsPerf[1] = new KeyBinding("Open Config Gui", Keyboard.KEY_J, "Script Central");
            keyBindingsPerf[2] = new KeyBinding("Unfocus Minecraft", Keyboard.KEY_H, "Script Central");
            keyBindingsPerf[3] = new KeyBinding("Toggle rendering", Keyboard.KEY_G, "Script Central");
        }
        if (!secHand.getScriptPerfState()) {
            for (int i = 0; i < keyBindings.length; i++) {
                ClientRegistry.registerKeyBinding(keyBindings[i]);
            }
        } else {
            for (int i = 0; i < keyBindingsPerf.length; i++) {
                ClientRegistry.registerKeyBinding(keyBindingsPerf[i]);
            }
        }

    }

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
    }

    @Override
    public void postInit(FMLPostInitializationEvent postEvent) {
        super.postInit(postEvent);
    }

}