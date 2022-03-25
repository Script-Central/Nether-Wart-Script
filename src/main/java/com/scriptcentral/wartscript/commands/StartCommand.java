package com.scriptcentral.wartscript.commands;


import java.io.File;

import com.scriptcentral.wartscript.PermData;
import com.scriptcentral.wartscript.ScriptLogger;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.EnumChatFormatting;

public class StartCommand extends CommandBase {
    PermData permdat;
    File folder;

    public StartCommand(PermData permdat, File folder) {
        this.permdat = permdat;
        this.folder = folder;
    }

    @Override
    public String getCommandName() {
        return "script";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "Starts the chosen script!";
    }

    private String getScriptType(String[] args) {

        if (args[0].toLowerCase().equals("wart")) {
            return "Wart";
        } else {
            return "";
        }
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if (args.length > 0) {
            if (args[0].toLowerCase().equals("lor")) {
                if (args[1].toLowerCase().equals("left")) {
                    permdat.writeConfigs(folder, true, permdat.isFlying, permdat.dir.numVal, permdat.webhook,
                            permdat.autostart, permdat.webhooktimer, permdat.movementType, permdat.rowsPerSetSpawn);
                } else if (args[1].toLowerCase().equals("right")) {
                    permdat.writeConfigs(folder, false, permdat.isFlying, permdat.dir.numVal, permdat.webhook,
                            permdat.autostart, permdat.webhooktimer, permdat.movementType, permdat.rowsPerSetSpawn);

                }

            } else if (args[0].toLowerCase().equals("flying")) {

                if (args[1].toLowerCase().equals("true")) {
                    permdat.writeConfigs(folder, permdat.leftOrRight, true, permdat.dir.numVal, permdat.webhook,
                            permdat.autostart, permdat.webhooktimer, permdat.movementType, permdat.rowsPerSetSpawn);
                } else if (args[1].toLowerCase().equals("false")) {
                    permdat.writeConfigs(folder, permdat.leftOrRight, false, permdat.dir.numVal, permdat.webhook,
                            permdat.autostart, permdat.webhooktimer, permdat.movementType, permdat.rowsPerSetSpawn);
                }
            } else if (args[0].toLowerCase().equals("dir")) {

                if (args[1].toLowerCase().equals("90")) {
                    permdat.writeConfigs(folder, permdat.leftOrRight, permdat.isFlying, 90, permdat.webhook,
                            permdat.autostart, permdat.webhooktimer, permdat.movementType, permdat.rowsPerSetSpawn);
                } else if (args[1].toLowerCase().equals("180")) {
                    permdat.writeConfigs(folder, permdat.leftOrRight, permdat.isFlying, 180, permdat.webhook,
                            permdat.autostart, permdat.webhooktimer, permdat.movementType, permdat.rowsPerSetSpawn);

                } else if (args[1].toLowerCase().equals("0")) {
                    permdat.writeConfigs(folder, permdat.leftOrRight, permdat.isFlying, 0, permdat.webhook,
                            permdat.autostart, permdat.webhooktimer, permdat.movementType, permdat.rowsPerSetSpawn);

                } else if (args[1].toLowerCase().equals("-90")) {
                    permdat.writeConfigs(folder, permdat.leftOrRight, permdat.isFlying, -90, permdat.webhook,
                            permdat.autostart, permdat.webhooktimer, permdat.movementType, permdat.rowsPerSetSpawn);

                }
            } else if (args[0].toLowerCase().equals("help")) {
                new ScriptLogger().log("Help:\n" + EnumChatFormatting.AQUA + "/script help - shows this list\n"
                                + EnumChatFormatting.AQUA
                                + "/script dir [0, 90, 180, -90] - sets the direction you will be facing\n"
                                + EnumChatFormatting.AQUA
                                + "/script flying [true, false] - whether your farm is a flying one or not\n"
                                + EnumChatFormatting.AQUA
                                + "/script lor [left, right] - whether your first movement is a left one or a right one\n",
                        new ScriptLogger().type.INFO);
            } else if (args[0].toLowerCase().equals("gui")) {
                Minecraft.getMinecraft().gameSettings.keyBindJump
                        .setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindJump.getKeyCode(), true);
            }
        }
//		ScriptLogger logger = new ScriptLogger();
//		String scriptType = this.getScriptType(args);
//		if (!scriptType.equals("")) {
//		logger.log("Starting " +  scriptType + " Script", logger.type.INFO);
//		} else {
//			logger.log("You must provide a valid script type - use /myScripts to list all available scripts or /script gui to open the menu.", logger.type.WARNING);
//		}
//		//EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
//		//logger.log(String.valueOf(Minecraft.getMinecraft().theWorld.getBlockState(new BlockPos(player.posX, player.posY-1, player.posZ)).getBlock().blockParticleGravity), logger.type.INFO);
//
//    	Minecraft.getMinecraft().displayGuiScreen(new ModGui());
//		}
//		} else {
//			ScriptLogger logger = new ScriptLogger();
//
//			logger.log("You must provide a script type - use /myScripts to list all available scripts or /script gui to open the menu.", logger.type.WARNING);
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }
}