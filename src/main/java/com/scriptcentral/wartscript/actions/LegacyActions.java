package com.scriptcentral.wartscript.actions;


import com.scriptcentral.wartscript.PermData;
import com.scriptcentral.wartscript.mouse.Attack;
import com.scriptcentral.wartscript.movement.MovementHandler;
import com.scriptcentral.wartscript.scGlobal;
import com.scriptcentral.wartscript.utils.MinecraftTweak;
import com.scriptcentral.wartscript.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import com.scriptcentral.wartscript.mouse.Look;
public class LegacyActions {

    private PermData permdat = new PermData();
    private MovementHandler movement = new MovementHandler();
    public ActionUtils actionUtils = new ActionUtils();
    private Utils utils = new Utils();
    private int WALK_ANGLE = 15;
    private int intervalDelay = 50;
    private int endOfRowWait = 50;
    private int FLY_ANGLE = 0;
    private Look Look = new Look();
    private int leftOfRightMult;
    private static int rows = 0;

    public void doActionsFly() {



        //if (!Minecraft.getMinecraft().theWorld.isAirBlock(actionUtils.getPos().add(0, -1, 0)) && Minecraft.getMinecraft().theWorld.isAirBlock(actionUtils.getPos())) {
        if (Minecraft.getMinecraft().thePlayer.onGround) {
            movement.jump.move(true);
            try {
                Thread.sleep(75);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            movement.jump.move(false);
            try {
                Thread.sleep(75);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            movement.jump.move(true);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            movement.jump.move(false);



        }


        new Attack().attack(true);
        leftOfRightMult = actionUtils.dirMult();
        Look.newLook(150, 0, permdat.tempDir.numVal);
        //

        if (permdat.tempLeftOrRight) {
            new scGlobal().lastAction = 2;
        } else {
            new scGlobal().lastAction = 3;
        }

        if (!actionUtils.checkStatus()) {
            return;
        }
        actionUtils.isFailsafeing();

        //
        movement.forward.sprint(true);
        boolean isLadder = actionUtils.intervalCheck(1, false, intervalDelay);
        movement.forward.sprint(false);
        //
        if (!actionUtils.checkStatus()) {
            return;
        }
        actionUtils.isFailsafeing();

        //

        //ladder
//        if (isLadder) {
//            ladder();
//            return;
//        }
        utils.intervalBurn(endOfRowWait, 5);

        Look.newLook(150, 0, permdat.tempDir.numVal + (leftOfRightMult * FLY_ANGLE));
        //

        if (permdat.tempLeftOrRight) {
            new scGlobal().lastAction = 5;
        } else {
            new scGlobal().lastAction = 6;
        }


        if (!actionUtils.checkStatus()) {
            return;
        }
        actionUtils.isFailsafeing();

        //n

        movement.forward.move(true);
        actionUtils.intervalCheck((permdat.tempLeftOrRight ? 3 : 4), false, intervalDelay);
        movement.forward.move(true);
        //
        if (!actionUtils.checkStatus()) {
            return;
        }
        actionUtils.isFailsafeing();

        //
        utils.intervalBurn(endOfRowWait, 5);

        Look.newLook(150, 0, permdat.tempDir.numVal);
        //

        if (permdat.tempLeftOrRight) {
            new scGlobal().lastAction = 3;
        } else {
            new scGlobal().lastAction = 2;
        }


        if (!actionUtils.checkStatus()) {
            return;
        }
        actionUtils.isFailsafeing();

        //
        movement.forward.sprint(true);
        isLadder = actionUtils.intervalCheck(1, false, intervalDelay);
        movement.forward.sprint(false);
        //
        if (!actionUtils.checkStatus()) {
            return;
        }
        actionUtils.isFailsafeing();

        //

        //ladder
//        if (isLadder) {
//            ladder();
//            return;
//        }

        utils.intervalBurn(endOfRowWait, 5);


        Look.newLook(150, 0, permdat.tempDir.numVal + (-leftOfRightMult * FLY_ANGLE));
        //

        if (permdat.tempLeftOrRight) {
            new scGlobal().lastAction = 6;
        } else {
            new scGlobal().lastAction = 5;
        }

        if (!actionUtils.checkStatus()) {
            return;
        }
        actionUtils.isFailsafeing();

        //
        movement.forward.move(true);
        actionUtils.intervalCheck((permdat.tempLeftOrRight ? 4 : 3), false, intervalDelay);
        movement.forward.move(true);

        //
        if (!actionUtils.checkStatus()) {
            return;
        }
        actionUtils.isFailsafeing();

        //

        utils.intervalBurn(endOfRowWait, 5);


    }




    public void doActionsWalk() {
        rows++;
        if (rows % permdat.rowsPerSetSpawn == 0) {
            actionUtils.setSpawn();
        }
        new Attack().attack(true);
        leftOfRightMult = actionUtils.dirMult();
        Look.newLook(150, 0, permdat.tempDir.numVal);
        //

        if (permdat.tempLeftOrRight) {
            new scGlobal().lastAction = 2;
        } else {
            new scGlobal().lastAction = 3;
        }


        if (!actionUtils.checkStatus()) {
            return;
        }
        actionUtils.isFailsafeing();

        //
        movement.forward.sprint(true);
        boolean isLadder = actionUtils.intervalCheck(1, false, intervalDelay);
        movement.forward.sprint(false);
        //
        if (!actionUtils.checkStatus()) {
            return;
        }
        actionUtils.isFailsafeing();

        //

        //ladder
        if (isLadder) {
            ladder(false);
            return;
        }

        while (Minecraft.getMinecraft().theWorld.isAirBlock(actionUtils.getPos().add(0, -1, 0)) && Minecraft.getMinecraft().theWorld.isAirBlock(actionUtils.getPos())) {

            utils.intervalBurn(100, 50);

            if (!actionUtils.checkStatus()) {
                return;
            }
            actionUtils.isFailsafeing();

        }
        if (Minecraft.getMinecraft().theWorld.getBlockState(actionUtils.getPos().add(0, -1, 0)).getBlock().equals(Blocks.glass)) {
            fallen();
            return;
        }

        utils.intervalBurn(endOfRowWait, 5);

        //
        if (!actionUtils.checkStatus()) {
            return;
        }
        actionUtils.isFailsafeing();

        //

        if (permdat.tempLeftOrRight) {
            movement.left.move(true);
        } else {
            movement.right.move(true);
        }

        new scGlobal().lastAction = 1;

        actionUtils.intervalCheck((permdat.tempLeftOrRight ? 3 : 4), false, intervalDelay);

        movement.right.move(false);
        movement.left.move(false);
        //
        if (!actionUtils.checkStatus()) {
            return;
        }
        actionUtils.isFailsafeing();

        //
        utils.intervalBurn(endOfRowWait, 5);

        Look.newLook(150, 0, permdat.tempDir.numVal);
        //
        if (permdat.tempLeftOrRight) {
            new scGlobal().lastAction = 3;
        } else {
            new scGlobal().lastAction = 2;
        }


        if (!actionUtils.checkStatus()) {
            return;
        }
        actionUtils.isFailsafeing();

        //
        movement.forward.sprint(true);
        isLadder = actionUtils.intervalCheck(1, false, intervalDelay);
        movement.forward.sprint(false);
        //
        if (!actionUtils.checkStatus()) {
            return;
        }
        actionUtils.isFailsafeing();

        //

        //ladder
        if (isLadder) {
            ladder(true);
            return;
        }

        while (Minecraft.getMinecraft().theWorld.isAirBlock(actionUtils.getPos().add(0, -1, 0)) && Minecraft.getMinecraft().theWorld.isAirBlock(actionUtils.getPos())) {
            utils.intervalBurn(100, 50);
            if (!actionUtils.checkStatus()) {
                return;
            }
            actionUtils.isFailsafeing();

        }
        if (Minecraft.getMinecraft().theWorld.getBlockState(actionUtils.getPos().add(0, -1, 0)).getBlock().equals(Blocks.glass)) {
            fallen();
            return;
        }

        utils.intervalBurn(endOfRowWait, 5);


        //
        if (!actionUtils.checkStatus()) {
            return;
        }
        actionUtils.isFailsafeing();

        //
        if (!permdat.tempLeftOrRight) {
            movement.left.move(true);
        } else {
            movement.right.move(true);
        }

        new scGlobal().lastAction = 1;

        actionUtils.intervalCheck((permdat.tempLeftOrRight ? 4 : 3), false, intervalDelay);
        movement.right.move(false);
        movement.left.move(false);
        //
        if (!actionUtils.checkStatus()) {
            return;
        }
        actionUtils.isFailsafeing();

        //

        utils.intervalBurn(endOfRowWait, 5);

    }


    private void fallen() {

        switch (permdat.tempDir.numVal) {
            case 180:
                permdat.tempDir = permdat.tempDir.SOUTH;
                break;
            case 0:
                permdat.tempDir = permdat.tempDir.NORTH;
                break;
            case 90:
                permdat.tempDir = permdat.tempDir.EAST;
                break;
            case -90:
                permdat.tempDir = permdat.tempDir.WEST;
                break;
        }

        //permdat.tempLeftOrRight = !permdat.tempLeftOrRight;

        Look.newLook(150, 0, permdat.tempDir.numVal);

    }
    //true is same, false is opposite
    private void ladder(boolean side) {
        new scGlobal().ladder = true;
        Look.newLook(150, 0, permdat.tempDir.numVal);
        movement.forward.sprint(true);
        utils.intervalBurn(20, 5);
        while (Minecraft.getMinecraft().theWorld.getBlockState(actionUtils.getPos()).getBlock().equals(Blocks.ladder) || actionUtils.ladderInFront(true)) {
            try {
                Thread.sleep(intervalDelay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (!actionUtils.checkStatus()) {
                return;
            }
            actionUtils.isFailsafeing();


        }
        movement.forward.sprint(false);
        switch (permdat.tempDir.numVal) {
            case 180:
                permdat.tempDir = permdat.tempDir.SOUTH;
                break;
            case 0:
                permdat.tempDir = permdat.tempDir.NORTH;
                break;
            case 90:
                permdat.tempDir = permdat.tempDir.EAST;
                break;
            case -90:
                permdat.tempDir = permdat.tempDir.WEST;
                break;
        }
        if (!side) {
            permdat.tempLeftOrRight = !permdat.tempLeftOrRight;
        }
        Look.newLook(150, 0, permdat.tempDir.numVal);

        new scGlobal().ladder = false;
    }

    public void cancelActions() {
        movement.jump.move(false);
        movement.forward.move(false);
        movement.forward.sprint(false);
        new MinecraftTweak().setClick(false);
        movement.back.move(false);
        movement.left.move(false);
        movement.right.move(false);
    }
}
