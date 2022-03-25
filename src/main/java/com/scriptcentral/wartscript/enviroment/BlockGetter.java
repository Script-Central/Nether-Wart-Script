package com.scriptcentral.wartscript.enviroment;

import java.util.ArrayList;
import java.util.Collection;

import com.scriptcentral.wartscript.security.SecurityHandler;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;

public class BlockGetter {

    public int TICKS_DELAY = 100;

    public Collection<BlockPos> getBlocks(int radius) {
        if (!new SecurityHandler().hasCrop) {
            Collection<BlockPos> wartOnly = new ArrayList<BlockPos>();
            EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
            if (player != null) {
                Iterable<BlockPos> allBlocks = BlockPos.getAllInBox(
                        new BlockPos(player.posX - radius, player.posY - radius, player.posZ - radius),
                        new BlockPos(new BlockPos(player.posX + radius, player.posY + radius, player.posZ + radius)));
                allBlocks.forEach((element) -> {
                    if (Minecraft.getMinecraft().theWorld.getBlockState(element).getBlock().equals(Blocks.nether_wart)) {
                        wartOnly.add(element);

                    }
                });
            }
            return wartOnly;
        } else {
            Collection<BlockPos> crops = new ArrayList<BlockPos>();
            EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
            if (player != null) {
                Iterable<BlockPos> allBlocks = BlockPos.getAllInBox(
                        new BlockPos(player.posX - radius, player.posY - radius, player.posZ - radius),
                        new BlockPos(new BlockPos(player.posX + radius, player.posY + radius, player.posZ + radius)));
                allBlocks.forEach((element) -> {
                    Block block = Minecraft.getMinecraft().theWorld.getBlockState(element).getBlock();
                    if (block.equals(Blocks.nether_wart) || block.equals(Blocks.carrots) || block.equals(Blocks.potatoes) || block.equals(Blocks.wheat)) {
                        crops.add(element);

                    }
                });
            }
            return crops;        }

    }

}
