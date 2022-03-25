package com.scriptcentral.wartscript.inventory;


import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class MutantWart {
    private static int sessionWartCount = 0;
    public static int potCount = 0;
    public static int hbCount = 0;
    public static int carCount = 0;
    public static int wheatCount = 0;
    public static long tickCount = 0;
    private static List<ItemStack> lastInv = null;
    private InventoryUtils invUtils = new InventoryUtils();

    public int getWartCount() {
        return sessionWartCount;
    }

    public void resetWartCount() {
        sessionWartCount = 0;
        potCount = 0;
        carCount = 0;
        wheatCount = 0;
        hbCount = 0;
    }

    public void saveInitialInv() {
        ItemStack[] inv = Minecraft.getMinecraft().thePlayer.inventory.mainInventory;
        lastInv = invUtils.removeNullFromInv(inv);
    }

    @SubscribeEvent
    public void clientTick(PlayerTickEvent event) {
        if (tickCount % 100 == 0) {


        if (Minecraft.getMinecraft().thePlayer == null)
            return;
        List<ItemStack> currentInv = invUtils
                .removeNullFromInv(Minecraft.getMinecraft().thePlayer.inventory.mainInventory);
        if (lastInv == null) {
            lastInv = currentInv;
        }
        for (int i = 0; i < currentInv.size() - 1; i++) {
            if (currentInv.get(i) == null || lastInv.get(i) == null) {
                continue;
            }


            if (currentInv.get(i).getDisplayName().endsWith("Mutant Nether Wart")
                    && lastInv.get(i).getDisplayName().endsWith("Mutant Nether Wart")) {
                int difference = currentInv.get(i).stackSize - lastInv.get(i).stackSize;
                if (difference == 1) {
                    sessionWartCount++;

                }
            } else if (currentInv.get(i).getDisplayName().endsWith("Enchanted Baked Potato")
                    && lastInv.get(i).getDisplayName().endsWith("Enchanted Baked Potato")) {
                int difference = currentInv.get(i).stackSize - lastInv.get(i).stackSize;
                if (difference == 1) {
                    potCount++;
                }
            } else if (currentInv.get(i).getDisplayName().endsWith("Enchanted Carrot")
                    && lastInv.get(i).getDisplayName().endsWith("Enchanted Carrot")) {
                int difference = currentInv.get(i).stackSize - lastInv.get(i).stackSize;
                if (difference == 1) {
                    carCount++;
                }
            } else if (currentInv.get(i).getDisplayName().endsWith("Tightly-Tied Hay Bale")
                    && lastInv.get(i).getDisplayName().endsWith("Tightly-Tied Hay Bale")) {
                int difference = currentInv.get(i).stackSize - lastInv.get(i).stackSize;
                if (difference == 1) {
                    wheatCount++;
                }
            } else if (currentInv.get(i).getDisplayName().endsWith("Enchanted Hay Bale")
                    && lastInv.get(i).getDisplayName().endsWith("Enchanted Hay Bale")) {
                int difference = currentInv.get(i).stackSize - lastInv.get(i).stackSize;
                if (difference == 1) {
                    hbCount++;
                }
            }
        }
        saveInitialInv();
    }
    tickCount++;
    }

}