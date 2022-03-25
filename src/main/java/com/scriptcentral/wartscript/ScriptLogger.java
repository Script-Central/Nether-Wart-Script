package com.scriptcentral.wartscript;


import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class ScriptLogger {

    public static enum logType {
        ERROR,
        INFO,
        SUCCESS,
        TRYING,
        WARNING;
    }

    public logType type;

    public void log(String text, logType type) {
        EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
        switch (type) {
            case ERROR:
                player.addChatMessage(new ChatComponentText(EnumChatFormatting.YELLOW + "[Script Central] " + EnumChatFormatting.DARK_RED + "[ERROR]: " + text));
                break;
            case INFO:

                player.addChatMessage(new ChatComponentText(EnumChatFormatting.YELLOW + "[Script Central] " + EnumChatFormatting.AQUA + "[INFO]: " + text));
                break;
            case TRYING:
                player.addChatMessage(new ChatComponentText(EnumChatFormatting.YELLOW + "[Script Central] " + EnumChatFormatting.DARK_GREEN + "[TRYING]: " + text));
                break;
            case SUCCESS:
                player.addChatMessage(new ChatComponentText(EnumChatFormatting.YELLOW + "[Script Central] " + EnumChatFormatting.GREEN + "[SUCCESS]: " + text));
                break;
            case WARNING:
                player.addChatMessage(new ChatComponentText(EnumChatFormatting.YELLOW + "[Script Central] " + EnumChatFormatting.RED + "[WARNING]: " + text));
                break;
            default:
                break;
        }
    }
}
