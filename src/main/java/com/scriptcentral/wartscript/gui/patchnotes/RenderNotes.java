package com.scriptcentral.wartscript.gui.patchnotes;


import com.scriptcentral.wartscript.PermData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;

public class RenderNotes extends GuiScreen {


    //final ResourceLocation texture = new ResourceLocation("scriptcentral", "wart gui.png");
    int guiHeight = 161;
    int guiWidth = 187;


    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        //Minecraft.getMinecraft().renderEngine.bindTexture(texture);

        String text = new GetNotes().text;
        drawLines(width, 60, text, 5, fontRendererObj);
        GlStateManager.pushMatrix();
        //GlStateManager.scale(20, 20, 1);
        drawCenteredString(fontRendererObj, "§b§nPatch Notes".substring(1, 3) + "§b§nPatch Notes".substring(4), width/2, 20, 0xFFFFFF);
        GlStateManager.popMatrix();

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void initGui() {
        super.initGui();
    }

    @Override
    public void updateScreen() {
    }


    private void drawLines(int screenWidth, int minY, String text, int textSize, FontRenderer fontrenderer) {
        String[] textArr = text.split("\\r?\\n|\\r");
        for (int i = 0; i < textArr.length; i++) {
            drawCenteredString(fontrenderer, textArr[i], screenWidth/2, minY + i*15, 0xFFFFFF);
        }
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