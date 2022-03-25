package com.scriptcentral.wartscript.commands;

import com.scriptcentral.wartscript.PermData;
import com.scriptcentral.wartscript.ScriptLogger;
import com.scriptcentral.wartscript.gui.ModGui;
import com.scriptcentral.wartscript.gui.patchnotes.RenderNotes;
import com.scriptcentral.wartscript.scGlobal;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.EnumChatFormatting;

import java.io.File;

public class Rowspersetspawn extends CommandBase {
    File folder;

    public Rowspersetspawn(File folder) {
        this.folder = folder;
    }

    @Override
    public String getCommandName() {
        return "rowspersetspawn";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "Changes the number of rows in between setting the spawnpoint.";
    }



    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {

        int numofrows = Integer.parseInt(args[0]);
        PermData permdat = new PermData();
        System.out.println(numofrows);
        permdat.writeConfigs(folder, permdat.leftOrRight, permdat.isFlying, permdat.dir.numVal, permdat.webhook,
                permdat.autostart, permdat.webhooktimer, permdat.movementType, numofrows);
    }


    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }
}