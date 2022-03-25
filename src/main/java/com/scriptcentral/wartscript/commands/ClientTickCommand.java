package com.scriptcentral.wartscript.commands;

import com.scriptcentral.wartscript.PermData;
import com.scriptcentral.wartscript.ScriptLogger;
import com.scriptcentral.wartscript.movement.ClientTickChanger;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

import java.io.File;
//WARNING
//DANGER!!
//MAY RESULT IN A BAN
//USE AT OWN RISK
public class ClientTickCommand extends CommandBase {
    ClientTickChanger tickChanger;

    public ClientTickCommand(ClientTickChanger tc) {
        tickChanger = tc;
    }

    @Override
    public String getCommandName() {
        return "clienttps";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "Changes the clientside tps";
    }



    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        int newTps = Integer.parseInt(args[0]);
        if (newTps > 30) {
            new ScriptLogger().log("A tps that high is dangerous, calm yourself now.", ScriptLogger.logType.ERROR);
        } else {
            int old = (int) tickChanger.setClientTicks((float) newTps);
            new ScriptLogger().log("Your clientside tps has been changed from " + old + " to " + newTps, ScriptLogger.logType.SUCCESS);

        }
    }


    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }
}
