package com.scriptcentral.wartscript.actions;


import com.scriptcentral.wartscript.CustomEventHandler;
import com.scriptcentral.wartscript.TOGGLES;
import com.scriptcentral.wartscript.movement.MovementHandler;
import com.scriptcentral.wartscript.scGlobal;
import com.scriptcentral.wartscript.utils.Utils;
import net.minecraft.client.Minecraft;

public class Failsafe implements Runnable{
    MovementHandler movement = new MovementHandler();
    private static double[] ltp = new double[3];

    public void run() {
        while (true) {



            if (Minecraft.getMinecraft().thePlayer != null && new TOGGLES().getWartToggle() && !new scGlobal().failSafeing && !new scGlobal().ladder) {
                if (ltp[0] != 0.0d) {



                    if (new Utils().xInRangeY((float) ltp[0], (float) Minecraft.getMinecraft().thePlayer.posX, 1F)
                            && new Utils().xInRangeY((float) ltp[2], (float) Minecraft.getMinecraft().thePlayer.posZ, 1F)) {
                        new scGlobal().failSafeing = true;

                        movement.cancelAllInputs();


                        switch (new scGlobal().lastAction) {
                            case 1:

                                movement.forward.move(false);
                                movement.back.move(300);
                                break;
                            case 2:
                                movement.right.move(300);
                                break;
                            case 3:
                                movement.left.move(300);
                                break;
                            case 4:
                                new CurrentActionStates().left = true;
                                new CurrentActionStates().back = true;
                                try {
                                    Thread.sleep(500);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }

                                new CurrentActionStates().left = false;
                                new CurrentActionStates().back = false;
                                break;
                            case 5:
                                new CurrentActionStates().right = true;
                                new CurrentActionStates().back = true;
                                try {
                                    Thread.sleep(700);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }

                                new CurrentActionStates().right = false;
                                new CurrentActionStates().back = false;

                                break;
//                            default:
//                                movement.forward.move(300);
//                                movement.back.move(300);
//                                movement.left.move(300);
//                                movement.right.move(300);
                        }

                        new scGlobal().failSafeing = false;
                    }
                }
                ltp[0] = Minecraft.getMinecraft().thePlayer.posX;
                ltp[1] = Minecraft.getMinecraft().thePlayer.posY;
                ltp[2] = Minecraft.getMinecraft().thePlayer.posZ;

            }

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
