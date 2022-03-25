package com.scriptcentral.wartscript.commands;

import com.scriptcentral.wartscript.ScriptLogger;
import com.scriptcentral.wartscript.movement.ClientTickChanger;
import com.scriptcentral.wartscript.scGlobal;
import com.scriptcentral.wartscript.security.SecurityHandler;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;


//WARNING
//DANGER!!
//MAY RESULT IN A BAN
//NOT FULLY TESTED
//USE AT OWN RISK
public class FastbreakCommand extends CommandBase{
        ClientTickChanger tickChanger;

        public FastbreakCommand() {

        }

        @Override
        public String getCommandName() {
            return "fastbreak";
        }

        @Override
        public String getCommandUsage(ICommandSender sender) {
            return "Toggles fastbreak";
        }



        @Override
        public void processCommand(ICommandSender sender, String[] args) throws CommandException {
            if (SecurityHandler.hasFastBreak) {
                scGlobal.isFastBreaking = !scGlobal.isFastBreaking;
                new ScriptLogger().log("Fast break toggled to: " + (scGlobal.isFastBreaking ? "Enabled" : "Disabled"), new ScriptLogger().type.INFO);
            } else {
                new ScriptLogger().log("You are not authorised to use that command", new ScriptLogger().type.ERROR);

            }
        }


        @Override
        public int getRequiredPermissionLevel() {
            return 0;
        }

}
