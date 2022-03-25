package com.scriptcentral.wartscript.actions;

import java.util.ArrayList;
//outdated?
import com.scriptcentral.wartscript.PermData;
import com.scriptcentral.wartscript.ScriptLogger;
import com.scriptcentral.wartscript.TOGGLES;
import com.scriptcentral.wartscript.mouse.Attack;
import com.scriptcentral.wartscript.mouse.Hotbar;
import com.scriptcentral.wartscript.mouse.SmoothLookHandler;
import com.scriptcentral.wartscript.movement.MovementHandler;
import com.scriptcentral.wartscript.utils.Utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.util.BlockPos;

public class ActionHandler {
    public Attack attack = new Attack();
    public MovementHandler movement = new MovementHandler();
    public ArrayList<timeout> actions = new ArrayList<timeout>();
    private int tickCount = 0;
    private PermData permdat;
    private SmoothLookHandler look = new SmoothLookHandler();
    private WorldClient world;
    private EntityPlayerSP player;
    private TOGGLES toggles;
    private BlockPos forward;
    private BlockPos back;
    private BlockPos left;
    private int angleDiff = 35;
    private BlockPos right;
    private Hotbar hotbar = new Hotbar();
    private boolean completed = false;
    private double[] ltp = new double[3];
    private int fsto = 120;
    private boolean isSideToSide = true;
    private boolean isFailSafeing = false;
    private boolean isTeleJumping = false;
    private boolean wasPadLT = false;
    private int startFSTick = 0;
    private int FSstage = 0;
    private boolean shouldBeAttacking = false;
    private int stage = 0;
    private volatile int isSleeping = 1;

    private ArrayList<timeout> lastActions = new ArrayList<timeout>();

    private volatile int isStillLooking = 1;

    public void initActions() {
        player = Minecraft.getMinecraft().thePlayer;

        world = Minecraft.getMinecraft().theWorld;
        if (!permdat.isFlying) {
            if (permdat.leftOrRight) {
                addActions("wait", 1, true); // 12
                addActions("forward", 1, true); // 11
                addActions("forward", 500, false); // 10
                addActions("attack", 1, true); // 9
                addActions("left", 700, true); // 8
                addActions("left", 900, false); // 7
                addActions("attack", 800, false); // 6
                addActions("forward", 1, true); // 5
                addActions("forward", 1, false); // 4
                addActions("attack", 1, true); // 3
                addActions("right", 700, true); // 2
                addActions("right", 700, false); // 1
            } else {
                addActions("wait", 1, true); // 12
                addActions("forward", 1, true); // 11
                addActions("forward", 500, false); // 10
                addActions("attack", 1, true); // 9
                addActions("right", 700, true); // 8
                addActions("right", 900, false); // 7
                addActions("attack", 800, false); // 6
                addActions("forward", 1, true); // 5
                addActions("forward", 1, false); // 4
                addActions("attack", 1, true); // 3
                addActions("left", 700, true); // 2
                addActions("left", 700, false); // 1
            }
        } else {
            if (permdat.leftOrRight) {
                addActions("wait", 1, true); // 15
                addActions("forward", 1, true); // 14
                addActions("forward", 500, false); // 13
                addActions("attack", 1, true); // 12
                addActions("wait", 1, true); // 11
                addActions("forward", 700, true); // 10
                addActions("forward", 900, false); // 9
                addActions("attack", 800, false); // 8
                addActions("wait", 1, true); // 7
                addActions("forward", 1, true); // 6
                addActions("forward", 1, false); // 5
                addActions("attack", 1, true); // 4
                addActions("wait", 1, true); // 3
                addActions("forward", 700, true); // 2
                addActions("forward", 700, false); // 1
            } else {
                addActions("wait", 1, true); // 15
                addActions("forward", 1, true); // 14
                addActions("forward", 500, false); // 13
                addActions("attack", 1, true); // 12
                addActions("wait", 1, true); // 11
                addActions("forward", 700, true); // 10
                addActions("forward", 900, false); // 9
                addActions("attack", 800, false); // 8
                addActions("wait", 1, true); // 7
                addActions("forward", 1, true); // 6
                addActions("forward", 1, false); // 5
                addActions("attack", 1, true); // 4
                addActions("wait", 1, true); // 3
                addActions("forward", 700, true); // 2
                addActions("forward", 700, false); // 1
            }
        }
        setDirs();

    }

    public ActionHandler(PermData permdat, TOGGLES toggles) {
        this.permdat = permdat;
        this.toggles = toggles;
    }

    public void tick() {

        // look.lookTo(0.0F, lookAngle);

        // actually do stuff
        if (actions.size() == 0) {
            // tickCount = 0;
            initActions();

        }

        if (isTeleJumping) {
            movement.jump.move(false);
            isTeleJumping = false;
        }

        if (shouldBeAttacking) {
            attack.attack(true);
        }

        tickCount++;
        if (!hotbar.findHoe(player)) {
            new ScriptLogger().log("Cannot find a hoe. Please Try Again.", new ScriptLogger().type.ERROR);
            toggles.toggleWart();
            return;
        }

        // failsafe

        // handle all actions
        setDirs();
        handleActionString(actions.get(0));

        // failsafe
        if (tickCount % fsto == 0) {

            if (ltp[0] != 0) {
                if (new Utils().xInRangeY((float) ltp[0], (float) player.posX, 0.2F)
                        && new Utils().xInRangeY((float) ltp[2], (float) player.posZ, 0.2F)) {
                    if (!isFailSafeing) {

                        clearActions();

                        movement.forward.move(true);
                        FSstage = 1;
                        startFSTick = tickCount;
                        isFailSafeing = true;
                    }

                }


            }
            ltp[0] = player.posX;
            ltp[1] = player.posY;
            ltp[2] = player.posZ;
        }

        if (isFailSafeing) {
            if (tickCount - startFSTick == 20) {
                if (FSstage == 1) {
                    movement.forward.move(false);
                    movement.left.move(true);
                    FSstage++;
                    startFSTick = tickCount;
                } else if (FSstage == 2) {

                    movement.left.move(false);
                    movement.back.move(true);
                    FSstage++;
                    startFSTick = tickCount;
                } else if (FSstage == 3) {

                    movement.back.move(false);
                    movement.right.move(true);
                    FSstage++;
                    startFSTick = tickCount;
                } else if (FSstage == 4) {

                    movement.right.move(false);
                    FSstage = 0;
                    isFailSafeing = false;
                    startFSTick = tickCount;
                    actions = lastActions;
                }
            }
        }

        if (!isFailSafeing && !this.isTeleJumping) {
            if (!permdat.isFlying) {
                if (actions.size() == 12) {
                    if (look.lookTo(300, 0, permdat.dir.numVal)) {
                        actions.get(0).isComplete = true;

                    }

                } else if (actions.size() == 11) {
                    if (world.isAirBlock(forward) == false) {

                        actions.get(0).isComplete = true;

                        setSpawn();

                    }
                } else if (actions.size() == 10) {

                    actions.get(0).isComplete = true;

                } else if (actions.size() == 9) {
                    actions.get(0).isComplete = true;
                    shouldBeAttacking = true;

                } else if (actions.size() == 8) {
                    if (!world.isAirBlock(permdat.leftOrRight == true ? left : right)) {

                        actions.get(0).isComplete = true;

                    }

                } else if (actions.size() == 7) {
                    actions.get(0).isComplete = true;

                } else if (actions.size() == 6) {
                    actions.get(0).isComplete = true;

                    shouldBeAttacking = false;
                } else if (actions.size() == 5) {
                    if (!world.isAirBlock(forward)) {

                        actions.get(0).isComplete = true;

                    }
                } else if (actions.size() == 4) {

                    actions.get(0).isComplete = true;

                } else if (actions.size() == 3) {

                    actions.get(0).isComplete = true;
                    shouldBeAttacking = true;

                } else if (actions.size() == 2) {

                    if (!world.isAirBlock(permdat.leftOrRight == true ? right : left)) {
                        actions.get(0).isComplete = true;

                    }
                } else if (actions.size() == 1) {

                    actions.get(0).isComplete = true;
                    shouldBeAttacking = false;
                }
            } else {
                // flying
                if (actions.size() == 15) {

                    if (look.lookTo(300, 0, permdat.dir.numVal)) {
                        actions.get(0).isComplete = true;

                    }

                } else if (actions.size() == 14) {
                    if (world.isAirBlock(forward) == false) {

                        actions.get(0).isComplete = true;

                    }

                } else if (actions.size() == 13) {

                    actions.get(0).isComplete = true;

                } else if (actions.size() == 12) {
                    actions.get(0).isComplete = true;
                    shouldBeAttacking = true;

                } else if (actions.size() == 11) {
                    if (look.lookTo(300, 0F,
                            permdat.leftOrRight ? permdat.dir.numVal - angleDiff : permdat.dir.numVal + angleDiff)) {
                        actions.get(0).isComplete = true;

                    }

                    if (isStillLooking == 3) {
                        actions.get(0).isComplete = true;
                        isStillLooking = 1;

                    }

                } else if (actions.size() == 10) {

                    if (!world.isAirBlock(permdat.leftOrRight == true ? left : right)) {

                        actions.get(0).isComplete = true;

                    }

                } else if (actions.size() == 9) {
                    actions.get(0).isComplete = true;

                } else if (actions.size() == 8) {
                    actions.get(0).isComplete = true;
                    shouldBeAttacking = false;

                } else if (actions.size() == 7) {

                    if (look.lookTo(300, 0, permdat.dir.numVal)) {
                        actions.get(0).isComplete = true;

                    }

                } else if (actions.size() == 6) {
                    if (!world.isAirBlock(forward)) {

                        actions.get(0).isComplete = true;

                    }
                } else if (actions.size() == 5) {
                    actions.get(0).isComplete = true;

                } else if (actions.size() == 4) {
                    actions.get(0).isComplete = true;
                    shouldBeAttacking = true;

                } else if (actions.size() == 3) {

                    if (look.lookTo(300, 0F,
                            permdat.leftOrRight ? permdat.dir.numVal + angleDiff : permdat.dir.numVal - angleDiff)) {
                        actions.get(0).isComplete = true;

                    }

                } else if (actions.size() == 2) {
                    if (!world.isAirBlock(permdat.leftOrRight == true ? right : left)) {

                        actions.get(0).isComplete = true;

                    }
                } else if (actions.size() == 1) {
                    actions.get(0).isComplete = true;
                    shouldBeAttacking = false;

                }
            }

        }

    }

    public void handleActionString(timeout element) {
        String action = element.id;
        int tick = element.tick;
        boolean state = element.state;
        // if (tickCount == tick) {
        if (element.isComplete) {
            actions.remove(0);
        }
        if (action.equals("forward")) {
            movement.forward.move(state);
        } else if (action.equals("left")) {

            movement.left.move(state);
        } else if (action.equals("right")) {

            movement.right.move(state);
        } else if (action.equals("back")) {

            movement.back.move(state);
        } else if (action.equals("attack")) {

            attack.attack(state);
        }
    }

    public void clearActions() {
        this.actions.clear();
    }

    private void setSpawn() {
        player.sendChatMessage("/spawnlocation");
    }

    public class timeout {
        public String id;
        public int tick;
        public boolean isComplete = false;
        public boolean state;

        timeout(String id, int tick, boolean state) {
            this.id = id;
            this.tick = tick;
            this.state = state;
        }
    }

    private void addActions(String action, int tick, boolean state) {
        actions.add(new timeout(action, tick, state));
    }

    private BlockPos addY(BlockPos block) {
        return new BlockPos(block.getX(), block.getY() + 1, block.getZ());
    }

    private BlockPos getPos() {
        return new BlockPos(Math.floor(player.posX), Math.floor(player.posY), Math.floor(player.posZ));
    }

    private void setDirs() {
        // -90 east +X, 180 north -Z, 90 west -X, 0 south +Z;
        if (permdat.dir.numVal == 90) {
            // forward = getPos().getX() < 0 ? new BlockPos(addY(getPos().west())) : ;
            forward = addY(getPos().west());
            back = addY(getPos().east());
            left = addY(getPos().south());
            right = addY(getPos().north());

        } else if (permdat.dir.numVal == 180) {
            forward = addY(getPos().north());
            left = addY(getPos().west());
            back = addY(getPos().south());
            right = addY(getPos().east());

        } else if (permdat.dir.numVal == -90) {
            forward = addY(getPos().east());
            right = addY(getPos().south());
            back = addY(getPos().west());
            left = addY(getPos().north());

        } else if (permdat.dir.numVal == 0) {
            forward = addY(getPos().south());
            right = addY(getPos().west());
            back = addY(getPos().north());
            left = addY(getPos().east());
        }
    }

}
