package com.scriptcentral.wartscript.utils;

import com.scriptcentral.wartscript.enviroment.BlockGetter;
import com.scriptcentral.wartscript.mouse.Hotbar;
import com.scriptcentral.wartscript.scGlobal;
import com.scriptcentral.wartscript.security.SecurityHandler;
import net.minecraft.block.Block;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.init.Blocks;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Timer;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import org.lwjgl.input.Mouse;

import com.scriptcentral.wartscript.TOGGLES;

import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MouseHelper;
import net.minecraft.util.MovingObjectPosition;
import scala.collection.parallel.ParIterableLike;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;

public class MinecraftTweak {

    private static boolean isClicking = false;
    private static BlockPos lastBlock = null;
    public void click() {
//		if (!leftClick) {
//			Minecraft.getMinecraft().leftClickCounter = 0;
//		}
        try {
            if (!Minecraft.getMinecraft().thePlayer.isUsingItem()) {
                if (!SecurityHandler.hasCrop) {
                    Block block = Minecraft.getMinecraft().theWorld.getBlockState(Minecraft.getMinecraft().objectMouseOver.getBlockPos()).getBlock();

                    if (isClicking && block.equals(Blocks.nether_wart) && Minecraft
                            .getMinecraft().objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && new Hotbar().findHoe(Minecraft.getMinecraft().thePlayer)) {
                        BlockPos blockpos = Minecraft.getMinecraft().objectMouseOver.getBlockPos();
                        if (SecurityHandler.hasFastBreak && scGlobal.isFastBreaking) {
                            if (Minecraft.getMinecraft().theWorld.getBlockState(blockpos).getBlock().getMaterial() != Material.air
                                    && this.packety(blockpos,
                                    Minecraft.getMinecraft().objectMouseOver.sideHit, Minecraft.getMinecraft().thePlayer.getHorizontalFacing())) {
                            }
                        } else {
                            if (Minecraft.getMinecraft().theWorld.getBlockState(blockpos).getBlock().getMaterial() != Material.air
                                    && Minecraft.getMinecraft().playerController.onPlayerDamageBlock(blockpos,
                                    Minecraft.getMinecraft().objectMouseOver.sideHit)) {
                                Minecraft.getMinecraft().thePlayer.swingItem();
                            }
                        }


                    } else {
                        Minecraft.getMinecraft().playerController.resetBlockRemoving();
                    }
                } else {
                    Block block = Minecraft.getMinecraft().theWorld.getBlockState(Minecraft.getMinecraft().objectMouseOver.getBlockPos()).getBlock();
                    if (isClicking && (block.equals(Blocks.nether_wart) || block.equals(Blocks.wheat) || block.equals(Blocks.potatoes) || block.equals(Blocks.carrots)) && Minecraft
                            .getMinecraft().objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && new Hotbar().findHoe(Minecraft.getMinecraft().thePlayer)) {
                        BlockPos blockpos = Minecraft.getMinecraft().objectMouseOver.getBlockPos();

                        if (SecurityHandler.hasFastBreak && scGlobal.isFastBreaking) {
                            if (Minecraft.getMinecraft().theWorld.getBlockState(blockpos).getBlock().getMaterial() != Material.air
                                    && this.packety(blockpos,
                                    Minecraft.getMinecraft().objectMouseOver.sideHit, Minecraft.getMinecraft().thePlayer.getHorizontalFacing())) {
                            }
                        } else {
                            if (Minecraft.getMinecraft().theWorld.getBlockState(blockpos).getBlock().getMaterial() != Material.air
                                    && Minecraft.getMinecraft().playerController.onPlayerDamageBlock(blockpos,
                                    Minecraft.getMinecraft().objectMouseOver.sideHit)) {
                                Minecraft.getMinecraft().thePlayer.swingItem();
                            }
                        }
                    } else {
                        Minecraft.getMinecraft().playerController.resetBlockRemoving();
                    }
                }
            }

        } catch (Exception s) {

        }
    }

    public boolean packety(BlockPos posBlock, EnumFacing directionFacing, EnumFacing pDir) {
        if (lastBlock != null) {
            if (lastBlock.getX() == Minecraft.getMinecraft().objectMouseOver.getBlockPos().getX() && lastBlock.getY() == Minecraft.getMinecraft().objectMouseOver.getBlockPos().getY() && lastBlock.getZ() == Minecraft.getMinecraft().objectMouseOver.getBlockPos().getZ()) {
                return true;
            }
        }
            lastBlock = Minecraft.getMinecraft().objectMouseOver.getBlockPos();
            ArrayList<BlockPos> possibleBlocks = new ArrayList<>();
            BlockPos current = Minecraft.getMinecraft().thePlayer.getPosition();
            BlockPos looking = Minecraft.getMinecraft().objectMouseOver.getBlockPos();

            for (int i = 0; i < 5; i++) {


                BlockPos newBlock = new BlockPos(current.getX() + (pDir.getDirectionVec().getX() * i), looking.getY(), current.getZ() + (pDir.getDirectionVec().getZ() * i));
                if (Minecraft.getMinecraft().theWorld.getBlockState(newBlock).getBlock().equals(Blocks.nether_wart)) {
                    possibleBlocks.add(newBlock);
                }
            }

            for (int i = 0; i < possibleBlocks.size(); i++) {
                Minecraft.getMinecraft().getNetHandler().addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, possibleBlocks.get(i), directionFacing));
                Minecraft.getMinecraft().getNetHandler().addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, possibleBlocks.get(i), directionFacing));
            }

        return true;

    }

    public MouseHelper customMouseHelper = new MouseHelper() {
        @Override
        public void mouseXYChange() {
            if (new TOGGLES().getWartToggle() || new TOGGLES().scheduledStop) {
                deltaX = 0;
                deltaY = 0;
            } else {
                this.deltaX = Mouse.getDX();
                this.deltaY = Mouse.getDY();
            }
        }
    };



    public void setClick(boolean click) {
        isClicking = click;
    }

    public boolean getClick() {
        return isClicking;
    }

}
