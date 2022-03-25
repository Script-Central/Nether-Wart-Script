package com.scriptcentral.wartscript.movement;
import com.scriptcentral.wartscript.actions.CurrentActionStates;
import com.scriptcentral.wartscript.utils.Utils;
import net.minecraft.util.MovementInput;

public class Left extends GenericMovement{
    public void move(boolean State) {
        new CurrentActionStates().left = State;
    }
    public void move(int time) {
        move(true);
        new Utils().intervalBurn(time, 20);
        move(false);
    }
}
