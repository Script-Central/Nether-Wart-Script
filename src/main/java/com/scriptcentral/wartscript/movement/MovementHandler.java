package com.scriptcentral.wartscript.movement;

public class MovementHandler {
    public Forward forward = new Forward();
    public Left left = new Left();
    public Back back = new Back();
    public Right right = new Right();
    public Jump jump = new Jump();

    public void cancelAllInputs() {
        forward.move(false);
        back.move(false);
        new Forward().sprint(false);
        left.move(false);
        right.move(false);
        back.move(false);
        jump.move(false);

    }

    // TODO: this - not really needed rn but like whenever I do

    // public void moveTo() {
    //
    // }

}
