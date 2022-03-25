package com.scriptcentral.wartscript.security;

import java.net.MalformedURLException;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import net.minecraft.client.Minecraft;

public class SecurityHandler {

    private static boolean secondStage = false;
    private static boolean isDown = false;
    private static boolean hasScript = true;
    public static boolean hasCrop = true;
    public static boolean hasFastBreak = true;
    private static String username;
    private static JsonObject res;
    private static boolean hasPerf = true;

    public void init() {
        
    }

    public void checkScript() {
       
    }

    public boolean getSecondStageState() {
        return secondStage;
    }

    

    public boolean getScriptState() {
        return hasScript;
    }


    public boolean getServerState() {
        return isDown;
    }

    public boolean getScriptPerfState() {
        return hasPerf;
    }
