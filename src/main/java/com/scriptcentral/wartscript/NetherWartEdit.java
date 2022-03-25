package com.scriptcentral.wartscript;
import com.scriptcentral.wartscript.security.SecurityHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockNetherWart;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;

public class NetherWartEdit {
    private Block wart = Blocks.nether_wart;
    void setWartProperties() {
        if (new SecurityHandler().hasCrop) {
            Blocks.potatoes.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
            Blocks.carrots.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
            Blocks.wheat.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);

        }

            wart.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }
}