package com.scriptcentral.wartscript.inventory;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;

public class InventoryUtils {
    public List<ItemStack> removeNullFromInv(ItemStack[] inventory) {
        List<ItemStack> cleaned = new ArrayList<>(inventory.length);
        for (ItemStack item : inventory) {
            // if (item != null) {
            cleaned.add(item);
            // }
        }
        return cleaned;
    }
}
