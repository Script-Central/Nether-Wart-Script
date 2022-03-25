package com.scriptcentral.wartscript;
import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class scGlobal {
    public static final String MOD_ID = "rachmaninoff";
    public static final String MOD_NAME = "Script Central Wart Script";
    public static final String VERSION = "V 1.4.3";
    public static File parentFolder;

    public static final String SC_CLIENT_PROXY = "com.scriptcentral.wartscript.proxy.ClientProxy";
    public static final String SC_COMMON_PROXY = "com.scriptcentral.wartscript.proxy.CommonProxy";
    public static boolean scriptToggle;
    public static boolean failSafeing = false;
    public static Thread scriptThread;
    public static boolean hasMaptcha = false;
    public static boolean ladder;
    public static int lastAction = 0;
    public static float lookPitch = 0F;
    public static float lookYaw = 0F;
    public static boolean finishedReconnect = true;
    public static boolean isFastBreaking = false;


    public static boolean isLooking = true;
}
