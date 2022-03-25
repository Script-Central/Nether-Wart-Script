package com.scriptcentral.wartscript.actions;


import com.scriptcentral.wartscript.PermData;

public class DoActions implements Runnable {
    public void terminate() {

    }

    public void run() {


        LegacyActions legActions = new LegacyActions();
        Actions actions = new Actions();
        while (actions.actionUtils.checkStatus()) {
            if (!new PermData().movementType) {
                if (!new PermData().isFlying) {
                    actions.doActionsWalk();
                } else {
                    actions.doActionsFly();
                }
            } else {
                if (!new PermData().isFlying) {
                    legActions.doActionsWalk();
                } else {
                    legActions.doActionsFly();
                }
            }
        }
    }
}
