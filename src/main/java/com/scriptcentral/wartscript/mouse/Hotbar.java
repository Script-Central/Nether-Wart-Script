package com.scriptcentral.wartscript.mouse;
import java.util.concurrent.ThreadLocalRandom;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.Display;


public class Hotbar {
    //EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
    Item diahoe = Items.diamond_hoe;
    Item ironhoe = Items.iron_hoe;
    Item stonehoe = Items.stone_hoe;

    public boolean findHoe(EntityPlayerSP player) {
//			Item diahoe = Items.diamond_hoe;
//			Item ironhoe = Items.iron_hoe;
//			ItemStack[] inv = player.inventory.mainInventory;
//			Item stonehoe = Items.stone_hoe;
//			for (int i = 27; i < 36; i++) {
//			if (inv[i].getItem() == diahoe || inv[i].getItem() == stonehoe || inv[i].getItem() == ironhoe) {
//				player.inventory.currentItem = i-27;
//				return true;
//
//		}
//			}
        for (int i = 0; i < 8; i++) {

            player.inventory.currentItem = i;
            if (player.inventory.getCurrentItem() != null) {
                Item item = player.inventory.getCurrentItem().getItem();
                if (item != null) {
                    if (item == diahoe || item == stonehoe || item == ironhoe) {
                        return true;

                    }
                }
            }
        }
        return false;

    }


    public boolean hasMaptcha() {
        if (Minecraft.getMinecraft().thePlayer == null) return false;
        if (Minecraft.getMinecraft().thePlayer.inventory == null) return false;
        for (int i = 0; i < 8; i++) {
            Minecraft.getMinecraft().thePlayer.inventory.currentItem = i;
            if (Minecraft.getMinecraft().thePlayer.inventory.getCurrentItem() != null) {
                if (!(Minecraft.getMinecraft().thePlayer.inventory.getCurrentItem().getItem().equals(Items.filled_map) || Minecraft.getMinecraft().thePlayer.inventory.getCurrentItem().getItem().equals(Items.map))) {

                    findHoe(Minecraft.getMinecraft().thePlayer);
                    return false;
                }
            }
        }
        return true;
    }

    public void randomiseHotbar(EntityPlayerSP player) {
        int randomNum = ThreadLocalRandom.current().nextInt(1, 9 + 1);
        for (int i = 0; i < randomNum; i++) {
            player.inventory.currentItem = i;
        }
    }


}