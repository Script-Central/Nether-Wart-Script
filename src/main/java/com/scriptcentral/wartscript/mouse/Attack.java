package com.scriptcentral.wartscript.mouse;
import com.scriptcentral.wartscript.utils.MinecraftTweak;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;

//@SideOnly(Side.CLIENT)
public class Attack {
    protected GameSettings mc = Minecraft.getMinecraft().gameSettings;

    public void attack(boolean State) {
        new MinecraftTweak().setClick(State);
    }

//	public void attackFor(int ticks, int CurrentTick) {
//
//	if (CurrentTick == ticks) {
//		attack(false);
//		System.out.println("Stopped moving");
//	} else {
//		attack(true);
//	}
//	}

    public void stop() {
        attack(false);
    }

}
