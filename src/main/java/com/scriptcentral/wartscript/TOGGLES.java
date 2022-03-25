package com.scriptcentral.wartscript;

import com.scriptcentral.wartscript.actions.ActionHandler;
import com.scriptcentral.wartscript.actions.Actions;
import com.scriptcentral.wartscript.actions.DoActions;
import com.scriptcentral.wartscript.mouse.Hotbar;
import com.scriptcentral.wartscript.mouse.Look;
import com.scriptcentral.wartscript.security.SecurityHandler;

import com.scriptcentral.wartscript.utils.Utils;
import net.minecraft.client.Minecraft;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TOGGLES {
    private Actions actionsThing = new Actions();
    ScriptLogger logger = new ScriptLogger();

    // non persistant:
    private static boolean wartToggle = false;
    static SecurityHandler secHand;
    static PermData permdat;
    public static boolean scheduledStop = false;

    // persistant

    public void initToggles(SecurityHandler secHand, PermData permdat) {
        this.secHand = secHand;
        this.permdat = permdat;
    }

    // toggle functions
    public void toggleWart() {
                if (wartToggle) {
                    wartToggle = false;
                    new scGlobal().scriptToggle = false;
                    if (new SecurityHandler().hasCrop) {
                        logger.log("Stopping the Farming script", logger.type.INFO);

                    } else {
                        logger.log("Stopping the Wart script", logger.type.INFO);
                    }
                    new Actions().cancelActions();

                    //actions.movement.cancelAllInputs();
                    //actions.attack.stop();
                    //actions.clearActions();
                    new Hotbar().randomiseHotbar(Minecraft.getMinecraft().thePlayer);

                } else {
                    wartToggle = true;

                    new scGlobal().scriptToggle = true;
                    new scGlobal().scriptThread = new Thread(new DoActions());

                    new scGlobal().scriptThread.start();
                    if (new SecurityHandler().hasCrop) {
                        logger.log("Starting the Farming script", logger.type.INFO);

                    } else {
                        logger.log("Starting the Wart script", logger.type.INFO);
                    }
                }
            


    }

    public void scheduledWartStop() {

                if (wartToggle) {
                    wartToggle = false;
                    scheduledStop = true;
                    logger.log("Please do not press any keys.", logger.type.INFO);
                    logger.log("This is a scheduled stop every 30 minutes to seem even less suspicious to Hypixel, do not panic.", logger.type.INFO);
                    logger.log("The script will restart in 15 seconds.", logger.type.INFO);

                    //actions.movement.cancelAllInputs();
                    //actions.attack.stop();


                } else {
                    wartToggle = true;
                    scheduledStop = false;
                    logger.log("Restarting the script, thanks for the patience :D", logger.type.SUCCESS);
                }


    }


    public boolean getWartToggle() {
        return wartToggle;
    }
}
