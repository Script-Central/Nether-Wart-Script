package com.scriptcentral.wartscript.movement;

import com.scriptcentral.wartscript.actions.CurrentActionStates;
import com.scriptcentral.wartscript.utils.Utils;
import net.minecraft.util.MovementInput;

public class Forward extends GenericMovement{
    public void move(boolean State) {
        new CurrentActionStates().forward = State;
    }
    public void move(int time) {
        move(true);
        new Utils().intervalBurn(time, 20);
        move(false);
    }

    public void sprint(boolean state) {
        new CurrentActionStates().sprint = state;
        new CurrentActionStates().forward = state;
    }
}
