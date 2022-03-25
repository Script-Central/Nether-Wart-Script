package com.scriptcentral.wartscript.movement;

import com.scriptcentral.wartscript.actions.CurrentActionStates;

public class Jump extends GenericMovement {
    @Override
    public void move(boolean State) {
        new CurrentActionStates().jump = State;
    }

}
