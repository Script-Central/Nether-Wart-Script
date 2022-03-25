package com.scriptcentral.wartscript.debuglog;

import com.scriptcentral.wartscript.utils.DiscordWebhook;
import net.minecraft.client.Minecraft;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Debug {
    public void createLog() {

        File log = new File(Minecraft.getMinecraft().mcDataDir.getAbsolutePath() + "/script_central_debug_log.txt");

        if (!log.exists()) {
            try {

                log.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void log(String text) {
        FileWriter fw = null;
        try {
            LocalDateTime date = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:SS");
            String time = date.format(formatter);


            fw = new FileWriter(Minecraft.getMinecraft().mcDataDir.getAbsolutePath() + "/script_central_debug_log.txt", true);
            BufferedWriter bw = new BufferedWriter(fw);
            // [10:51:42] <text>
            bw.write("[" + time + "] " + text);
            bw.newLine();
            bw.close();
            new DiscordWebhook().sendDebugData("[" + time + "] " + text);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
