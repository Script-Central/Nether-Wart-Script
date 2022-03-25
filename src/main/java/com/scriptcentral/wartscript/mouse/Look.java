package com.scriptcentral.wartscript.mouse;
import com.scriptcentral.wartscript.scGlobal;
import com.scriptcentral.wartscript.utils.Utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
//import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class Look {

    private long startTime = -1;
    private long endTime;
    private Utils utils = new Utils();

    private Vector2D startDir;
    private Vector2D target;
    private Vector2D dir;
    private Vector2D change;

    private boolean movementDirPitch;
    private boolean movementDirYaw;
    private long msPerAction = 1;

    public long leftOverTime;
    private long thisActionStartTime;
    private long thisActionEndTime;

    private boolean pitch() {

        if (this.dir.pitch >= this.target.pitch && this.movementDirPitch) {
            return true;
        } else if (this.dir.pitch <= this.target.pitch && !this.movementDirPitch) {
            return true;
        }

        this.dir.pitch += change.pitch;

        return false;
    }

    private boolean yaw() {

        if (this.dir.yaw >= this.target.yaw && this.movementDirYaw) {
            return true;
        } else if (this.dir.yaw <= this.target.yaw && !this.movementDirYaw) {
            return true;
        }

        this.dir.yaw += change.yaw;

        return false;
    }

    private void setLook() {
        EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
        player.rotationYaw = (float) this.dir.yaw;
        player.rotationPitch = (float) this.dir.pitch;
    }

    public void newLook(int ms, float targetPitch, float targetYaw) {
        while (true) {
            thisActionStartTime = System.nanoTime();

            EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;

            if (utils.xInRangeY(player.rotationPitch, targetPitch, 0.2F)
                    && utils.xInRangeY(player.rotationYaw, targetYaw, 0.2F)) {
                startTime = -1;
                Minecraft.getMinecraft().thePlayer.rotationYaw = targetYaw;
                Minecraft.getMinecraft().thePlayer.rotationPitch = targetPitch;
                new scGlobal().lookPitch = targetPitch;
                new scGlobal().lookYaw = targetYaw;
                new scGlobal().isLooking = false;

                return;
            }

            if (startTime == -1) {
                startTime = System.nanoTime();
                this.dir = new Vector2D(player.rotationYaw, player.rotationPitch);
                this.target = new Vector2D(targetYaw, targetPitch);
                endTime = startTime + (ms * 1000000);
                change = dir.getDifference(target).divide(ms);
                new scGlobal().isLooking = true;

                if (change.pitch >= 0) {
                    this.movementDirPitch = true;
                } else {
                    this.movementDirPitch = false;
                }

                if (change.yaw >= 0) {
                    this.movementDirYaw = true;
                } else {
                    this.movementDirYaw = false;
                }
//			new ScriptLogger().log(Double.toString(change.pitch), new ScriptLogger().type.INFO);
//			new ScriptLogger().log(Double.toString(change.yaw), new ScriptLogger().type.INFO);
            }
            boolean pitchDone = pitch();
            boolean yawDone = yaw();
            if (pitchDone && yawDone) {
                startTime = -1;
            }

            setLook();

            thisActionEndTime = System.nanoTime();
            leftOverTime = (msPerAction * 1000000) - (thisActionEndTime - thisActionStartTime);

            if (leftOverTime < 0) {
                leftOverTime = 0;
            }

            utils.burn(leftOverTime);
        }
    }


    public void newLook2(int ms, float targetPitch, float targetYaw) {
        while (true) {
            thisActionStartTime = System.nanoTime();

            EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;

            if (utils.xInRangeY(player.rotationPitch, targetPitch, 0.2F)
                    && utils.xInRangeY(player.rotationYaw, targetYaw, 0.2F)) {
                startTime = -1;
                Minecraft.getMinecraft().thePlayer.rotationYaw = targetYaw;
                Minecraft.getMinecraft().thePlayer.rotationPitch = targetPitch;
                new scGlobal().lookPitch = targetPitch;
                new scGlobal().lookYaw = targetYaw;
                return;
            }

            if (startTime == -1) {
                startTime = System.nanoTime();
                this.dir = new Vector2D(player.rotationYaw, player.rotationPitch);
                this.target = new Vector2D(targetYaw, targetPitch);
                endTime = startTime + (ms * 1000000);
                change = dir.getDifference(target).divide(ms);
                if (change.pitch >= 0) {
                    this.movementDirPitch = true;
                } else {
                    this.movementDirPitch = false;
                }

                if (change.yaw >= 0) {
                    this.movementDirYaw = true;
                } else {
                    this.movementDirYaw = false;
                }
//			new ScriptLogger().log(Double.toString(change.pitch), new ScriptLogger().type.INFO);
//			new ScriptLogger().log(Double.toString(change.yaw), new ScriptLogger().type.INFO);
            }
            boolean pitchDone = pitch();
            boolean yawDone = yaw();
            if (pitchDone && yawDone) {
                startTime = -1;
            }

            setLook();

            thisActionEndTime = System.nanoTime();
            leftOverTime = (msPerAction * 1000000) - (thisActionEndTime - thisActionStartTime);

            if (leftOverTime < 0) {
                leftOverTime = 0;
            }

            utils.burn(leftOverTime);
        }
    }



    public boolean smoothLook(int ms, float targetPitch, float targetYaw) {
        thisActionStartTime = System.nanoTime();

        EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;

        if (utils.xInRangeY(player.rotationPitch, targetPitch, 0.2F)
                && utils.xInRangeY(player.rotationYaw, targetYaw, 0.2F)) {
            startTime = -1;
            return false;
        }

        if (startTime == -1) {
            startTime = System.nanoTime();
            this.dir = new Vector2D(player.rotationYaw, player.rotationPitch);
            this.target = new Vector2D(targetYaw, targetPitch);
            endTime = startTime + (ms * 1000000);
            change = dir.getDifference(target).divide(ms);
            if (change.pitch >= 0) {
                this.movementDirPitch = true;
            } else {
                this.movementDirPitch = false;
            }

            if (change.yaw >= 0) {
                this.movementDirYaw = true;
            } else {
                this.movementDirYaw = false;
            }
//			new ScriptLogger().log(Double.toString(change.pitch), new ScriptLogger().type.INFO);
//			new ScriptLogger().log(Double.toString(change.yaw), new ScriptLogger().type.INFO);

        }
        boolean pitchDone = pitch();
        boolean yawDone = yaw();
        if (pitchDone && yawDone) {
            startTime = -1;
            return false;
        }

        setLook();

        thisActionEndTime = System.nanoTime();
        leftOverTime = (msPerAction * 1000000) - (thisActionEndTime - thisActionStartTime);

        if (leftOverTime < 0) {
            leftOverTime = 0;
        }

        return true;
    }

    public int normalPitch(int pitch) {
        // between 0 and 180

        return (pitch * -1) + 90;
    }

    private Vector2D getDirs() {
        return new Vector2D(Minecraft.getMinecraft().thePlayer.rotationYaw,
                Minecraft.getMinecraft().thePlayer.rotationPitch);
    }

    public int normalYaw(int yaw) {
        return yaw + 180;
    }

    public class Vector2D {
        public double yaw;
        public double pitch;

        public Vector2D(double x, double y) {
            this.yaw = x;
            this.pitch = y;
        }

        public Vector2D getDifference(Vector2D vec) {
            return new Vector2D(vec.yaw - this.yaw, vec.pitch - this.pitch);
        }

        public Vector2D divide(float x) {
            return new Vector2D(this.yaw / x, this.pitch / x);
        }

    }

}