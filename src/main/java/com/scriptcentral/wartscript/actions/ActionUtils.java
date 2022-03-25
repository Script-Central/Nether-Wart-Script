package com.scriptcentral.wartscript.actions;


import com.scriptcentral.wartscript.PermData;
import com.scriptcentral.wartscript.mouse.Look;
import com.scriptcentral.wartscript.mouse.SmoothLookHandler;
import com.scriptcentral.wartscript.movement.MovementHandler;
import com.scriptcentral.wartscript.scGlobal;
import com.scriptcentral.wartscript.utils.DiscordWebhook;
import com.scriptcentral.wartscript.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;

public class ActionUtils {
    public int dirMult() {
        if (new PermData().tempLeftOrRight) {
            return -1;
        } else {
            return 1;
        }
    }


    public BlockPos forward;
    public BlockPos backward;
    public BlockPos left;
    public BlockPos right;

    private BlockPos addY(BlockPos block) {
        return block.add(0, 0, 0);
    }
    public void setDirs() {

        PermData permdat = new PermData();

        // -90 east +X, 180 north -Z, 90 west -X, 0 south +Z;
        if (permdat.tempDir.numVal == 90) {
            // forward = getPos().getX() < 0 ? new BlockPos(addY(getPos().west())) : ;
            forward = addY(getPos().west());
            backward = addY(getPos().east());
            left = addY(getPos().south());
            right = addY(getPos().north());

        } else if (permdat.tempDir.numVal == 180) {
            forward = addY(getPos().north());
            left = addY(getPos().west());
            backward = addY(getPos().south());
            right = addY(getPos().east());

        } else if (permdat.tempDir.numVal == -90) {
            forward = addY(getPos().east());
            right = addY(getPos().south());
            backward = addY(getPos().west());
            left = addY(getPos().north());

        } else if (permdat.tempDir.numVal == 0) {
            forward = addY(getPos().south());
            right = addY(getPos().west());
            backward = addY(getPos().north());
            left = addY(getPos().east());
        }
    }

    public void isFailsafeing() {

        while (new scGlobal().failSafeing) {
            new Utils().intervalBurn(100, 20);
        }
    }

    public void setSpawn() {
        Minecraft.getMinecraft().thePlayer.sendChatMessage("/spawnlocation");
    }

    public int getNextCheckDirection(int stage) {
        if (new PermData().tempLeftOrRight) {
            if (stage == 1) {
                return 8;
            } else {
                return 9;
            }
        } else {
            if (stage == 1) {
                return 9;

            } else {
                return 8;
            }
        }

    }


    public boolean ladderInFront(boolean state) {
        setDirs();
        if (Minecraft.getMinecraft().theWorld.getBlockState(forward).getBlock().equals(Blocks.ladder) == state) {
            return true;
        }
        return false;
    }

    public BlockPos getPos() {
        EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
        return new BlockPos(Math.floor(player.posX), Math.floor(player.posY), Math.floor(player.posZ));
    }

    public boolean intervalCheck(int direction, boolean isAir, int interval) {
        while (true) {
            //doesnt refresh
            setDirs();
            BlockPos tempDir = forward;

            switch (direction) {
                case 1:
                tempDir = forward;
                new MovementHandler().forward.move(true);

                break;
                case 2:
                    tempDir = backward;
                    new MovementHandler().back.move(true);

                    break;
                case 3:
                    tempDir = left;
                    new MovementHandler().left.move(true);

                    break;
                case 4:
                    tempDir = right;
                    new MovementHandler().right.move(true);

                    break;
                case 5:
                    tempDir = Minecraft.getMinecraft().thePlayer.getPosition();
                    break;
                case 6:
                    tempDir = left;
                    new MovementHandler().forward.move(true);
                    break;
                case 7:
                    tempDir = right;
                    new MovementHandler().forward.move(true);
                    break;
                case 8:
                    tempDir = forward;
                    new MovementHandler().left.move(true);
                    break;
                case 9:
                    tempDir = forward;
                    new MovementHandler().right.move(true);
                    break;
            }

            if (Minecraft.getMinecraft().theWorld.getBlockState(tempDir).getBlock().equals(Blocks.ladder)) {
                System.out.println("ladder");
                return true;
            }

            if (Minecraft.getMinecraft().theWorld.isAirBlock(tempDir) == isAir) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return false;
            }
            isFailsafeing();

            if (!checkStatus()) {
                return false;
            }


            try {
                Thread.sleep(interval);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean checkStatus() {
        boolean hadMaptcha = false;
        if (new scGlobal().hasMaptcha) {
            new DiscordWebhook().sendMaptchaMessage(new PermData().webhook);
        }

        while (new scGlobal().hasMaptcha) {
            new MovementHandler().cancelAllInputs();
            hadMaptcha = true;
            new Utils().intervalBurn(100, 100);
        }

        if (hadMaptcha) {

            new Look().smoothLook(150, scGlobal.lookPitch, scGlobal.lookYaw);
        }



        if (!new scGlobal().scriptToggle) {
            new MovementHandler().cancelAllInputs();
        }
        return new scGlobal().scriptToggle;
    }


}
