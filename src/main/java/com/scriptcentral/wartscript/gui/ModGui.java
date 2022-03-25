package com.scriptcentral.wartscript.gui;


import java.io.File;
import java.io.IOException;

import org.apache.commons.lang3.StringUtils;

import com.scriptcentral.wartscript.PermData;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.ResourceLocation;

public class ModGui extends GuiScreen {
    PermData permdat;
    File folder;

    public ModGui(PermData permdat, File folder) {
        this.permdat = permdat;
        this.folder = folder;
    }

    final ResourceLocation texture = new ResourceLocation("scriptcentral", "wart gui.png");
    int guiHeight = 161;
    int guiWidth = 187;
    GuiButton flyingButton;
    GuiButton dirButton;
    GuiButton lorButton;
    GuiButton movementType;

    GuiTextField webhooktimer;

    GuiTextField webhook;
    GuiButton autostart;

    final int LORBUTTON = 2;
    final int MOVEMENTTYPE = 6;

    final int DIRBUTTON = 1;
    final int FLYINGBUTTON = 0;
    final int WEBHOOKTIMER = 5;

    final int AUTOSTART = 3;
    final int WEBHOOK = 4;
    // final int FLYINGBUTTON = 0;

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        Minecraft.getMinecraft().renderEngine.bindTexture(texture);
        int centerX1 = (width / 2) - ((guiWidth * 2) + 20) / 2;
        int centerX2 = ((width / 2) - ((guiWidth * 2) + 20) / 2) + guiWidth + 20;

        int centerY = (height / 2) - guiWidth / 2;
        drawTexturedModalRect(centerX1, centerY, 0, 0, guiWidth, guiHeight);
        drawTexturedModalRect(centerX2, centerY, 0, 0, guiWidth, guiHeight);

        drawString(fontRendererObj, "Flying:", centerX1 + 20, height / 2 - 30, 0xFFFFFF);
        drawString(fontRendererObj, "Dir:", centerX1 + 20, height / 2, 0xFFFFFF);
        drawString(fontRendererObj, "Left Or Right:", centerX1 + 20, height / 2 + 30, 0xFFFFFF);

        drawString(fontRendererObj, "AutoStart:", centerX2 + 20, height / 2 - 35, 0xFFFFFF);
        drawString(fontRendererObj, "WebHook Url:", centerX2 + 20, height / 2 - 10, 0xFFFFFF);
        drawString(fontRendererObj, "WH Timer (Mins):", centerX2 + 20, height / 2 + 20, 0xFFFFFF);
        drawString(fontRendererObj, "Movement Type:", centerX2 + 20, height / 2 + 45, 0xFFFFFF);

        webhook.drawTextBox();
        webhooktimer.drawTextBox();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void initGui() {
        int centerX1 = (width / 2) - ((guiWidth * 2) + 20) / 2;
        int centerX2 = ((width / 2) - ((guiWidth * 2) + 20) / 2) + guiWidth + 20;

        webhook = new GuiTextField(WEBHOOK, fontRendererObj, centerX2 + 90, height / 2 - 15, 80, 15);
        webhook.setMaxStringLength(500);
        webhook.setText(permdat.webhook);
        webhooktimer = new GuiTextField(WEBHOOKTIMER, fontRendererObj, centerX2 + 110, height / 2 + 15, 50, 15);
        webhooktimer.setMaxStringLength(10);
        webhooktimer.setText(Integer.toString(permdat.webhooktimer));
        buttonList.clear();
        buttonList.add(flyingButton = new GuiButton(FLYINGBUTTON, centerX1 + 120, height / 2 - 35, 40, 15,
                permdat.isFlying ? "true" : "false"));
        System.out.println(permdat.dir.numVal);
        buttonList.add(dirButton = new GuiButton(DIRBUTTON, centerX1 + 120, height / 2 - 5, 40, 15,
                Integer.toString(permdat.dir.numVal)));

        buttonList.add(autostart = new GuiButton(AUTOSTART, centerX2 + 120, height / 2 - 40, 40, 15,
                Boolean.toString(permdat.autostart)));

        buttonList.add(lorButton = new GuiButton(LORBUTTON, centerX1 + 120, height / 2 + 25, 40, 15,
                permdat.leftOrRight ? "left" : "right"));
        buttonList.add(movementType = new GuiButton(MOVEMENTTYPE, centerX2 + 110, height / 2 + 40, 65, 15,
                permdat.movementType ? "Legacy" : "Standard"));

        super.initGui();
    }

    @Override
    protected void keyTyped(char par1, int par2) throws IOException {
        super.keyTyped(par1, par2);
        if (this.webhook.isFocused()) {
            this.webhook.textboxKeyTyped(par1, par2);
            permdat.writeConfigs(folder, permdat.leftOrRight, permdat.isFlying, permdat.dir.numVal,
                    this.webhook.getText(), permdat.autostart, permdat.webhooktimer, permdat.movementType, permdat.rowsPerSetSpawn);
        }
        if (this.webhooktimer.isFocused()) {
            this.webhooktimer.textboxKeyTyped(par1, par2);
            if (this.webhooktimer.getText().equals("")) {
                permdat.writeConfigs(folder, permdat.leftOrRight, permdat.isFlying, permdat.dir.numVal, permdat.webhook,
                        permdat.autostart, 30, permdat.movementType, permdat.rowsPerSetSpawn);
            } else if (StringUtils.isNumeric(this.webhooktimer.getText())) {
                try {
                    System.out.println(this.webhooktimer.getText());
                    permdat.writeConfigs(folder, permdat.leftOrRight, permdat.isFlying, permdat.dir.numVal,
                            permdat.webhook, permdat.autostart, Integer.parseInt(this.webhooktimer.getText()), permdat.movementType, permdat.rowsPerSetSpawn);
                } catch (NumberFormatException e) {
                    System.out.println("NaN");
                }
            }
        }
    }

    @Override
    protected void mouseClicked(int x, int y, int btn) throws IOException {
        super.mouseClicked(x, y, btn);
        this.webhook.mouseClicked(x, y, btn);
        this.webhooktimer.mouseClicked(x, y, btn);

    }

    public void updateButtons(int buttonId) {
        System.out.println(buttonId);
        if (buttonId == FLYINGBUTTON) {
            if (flyingButton.displayString.equals("true")) {
                flyingButton.displayString = "false";
                permdat.writeConfigs(folder, permdat.leftOrRight, false, permdat.dir.numVal, permdat.webhook,
                        permdat.autostart, permdat.webhooktimer, permdat.movementType, permdat.rowsPerSetSpawn);
            } else {
                flyingButton.displayString = "true";
                permdat.writeConfigs(folder, permdat.leftOrRight, true, permdat.dir.numVal, permdat.webhook,
                        permdat.autostart, permdat.webhooktimer, permdat.movementType, permdat.rowsPerSetSpawn);
            }
        } else if (buttonId == LORBUTTON) {
            if (lorButton.displayString.equals("left")) {
                lorButton.displayString = "right";
                permdat.writeConfigs(folder, false, permdat.isFlying, permdat.dir.numVal, permdat.webhook,
                        permdat.autostart, permdat.webhooktimer, permdat.movementType, permdat.rowsPerSetSpawn);
            } else {
                lorButton.displayString = "left";
                permdat.writeConfigs(folder, true, permdat.isFlying, permdat.dir.numVal, permdat.webhook,
                        permdat.autostart, permdat.webhooktimer, permdat.movementType, permdat.rowsPerSetSpawn);
            }
        } else if (buttonId == DIRBUTTON) {
            System.out.println(dirButton.displayString);
            if (dirButton.displayString.equals("0")) {
                dirButton.displayString = "90";
                permdat.writeConfigs(folder, permdat.leftOrRight, permdat.isFlying, 90, permdat.webhook,
                        permdat.autostart, permdat.webhooktimer, permdat.movementType, permdat.rowsPerSetSpawn);
            } else if (dirButton.displayString.equals("90")) {
                System.out.println("yeet");
                dirButton.displayString = "180";
                permdat.writeConfigs(folder, permdat.leftOrRight, permdat.isFlying, 180, permdat.webhook,
                        permdat.autostart, permdat.webhooktimer, permdat.movementType, permdat.rowsPerSetSpawn);
            } else if (dirButton.displayString.equals("180")) {
                dirButton.displayString = "-90";
                permdat.writeConfigs(folder, permdat.leftOrRight, permdat.isFlying, -90, permdat.webhook,
                        permdat.autostart, permdat.webhooktimer, permdat.movementType, permdat.rowsPerSetSpawn);
            } else if (dirButton.displayString.equals("-90")) {
                dirButton.displayString = "0";
                permdat.writeConfigs(folder, permdat.leftOrRight, permdat.isFlying, 0, permdat.webhook,
                        permdat.autostart, permdat.webhooktimer, permdat.movementType, permdat.rowsPerSetSpawn);
            }
        } else if (buttonId == AUTOSTART) {
            if (permdat.autostart) {
                autostart.displayString = "false";
                permdat.writeConfigs(folder, permdat.leftOrRight, permdat.isFlying, permdat.dir.numVal, permdat.webhook,
                        false, permdat.webhooktimer, permdat.movementType, permdat.rowsPerSetSpawn);
            } else {
                autostart.displayString = "true";
                permdat.writeConfigs(folder, permdat.leftOrRight, permdat.isFlying, permdat.dir.numVal, permdat.webhook,
                        true, permdat.webhooktimer, permdat.movementType, permdat.rowsPerSetSpawn);
            }
        } else if (buttonId == MOVEMENTTYPE) {
            if (permdat.movementType) {
                movementType.displayString = "Standard";
                permdat.writeConfigs(folder, permdat.leftOrRight, permdat.isFlying, permdat.dir.numVal, permdat.webhook,
                        false, permdat.webhooktimer, false, permdat.rowsPerSetSpawn);
            } else {
                movementType.displayString = "Legacy";
                permdat.writeConfigs(folder, permdat.leftOrRight, permdat.isFlying, permdat.dir.numVal, permdat.webhook,
                        false, permdat.webhooktimer, true, permdat.rowsPerSetSpawn);
            }
        }
    }

    @Override
    public void updateScreen() {
        webhook.updateCursorCounter();
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        updateButtons(button.id);
        super.actionPerformed(button);
    }

    @Override
    public boolean doesGuiPauseGame() {

        return false;
    }

    @Override
    public void onGuiClosed() {

        super.onGuiClosed();
    }

}