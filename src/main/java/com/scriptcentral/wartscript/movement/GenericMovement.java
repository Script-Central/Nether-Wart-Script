package com.scriptcentral.wartscript.movement;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MovementInput;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.relauncher.Side;

@SideOnly(Side.CLIENT)
public class GenericMovement {
    //protected EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
    protected GameSettings mc = Minecraft.getMinecraft().gameSettings;

    public void move(boolean State) {
    }


//	public void moveFor(int ticks, int CurrentTick) {
//
//	if (CurrentTick == ticks) {
//		move(false);
//		System.out.println("Stopped moving");
//	} else {
//		move(true);
//	}
//	}

//	public void stop() {
//		move(false);
//	}





}