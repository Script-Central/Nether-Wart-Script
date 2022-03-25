package com.scriptcentral.wartscript;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.scriptcentral.wartscript.actions.ActionHandler;
import com.scriptcentral.wartscript.actions.CurrentActionStates;
import com.scriptcentral.wartscript.gui.ModGui;
import com.scriptcentral.wartscript.gui.patchnotes.RenderNotes;
import com.scriptcentral.wartscript.inventory.MutantWart;
import com.scriptcentral.wartscript.enviroment.BlockGetter;
import com.scriptcentral.wartscript.mouse.Hotbar;
import com.scriptcentral.wartscript.proxy.ClientProxy;
import com.scriptcentral.wartscript.security.SecurityHandler;
import com.scriptcentral.wartscript.utils.DiscordWebhook;
import com.scriptcentral.wartscript.utils.MinecraftTweak;
import com.scriptcentral.wartscript.utils.RenderHandling;

import com.scriptcentral.wartscript.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionHelper;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.util.*;
import net.minecraftforge.client.GuiIngameForge;
import net.minecraftforge.client.event.sound.SoundEvent;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent.ClientDisconnectionFromServerEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent.ServerDisconnectionFromClientEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.http.util.TextUtils;
import org.lwjgl.opengl.Display;

import javax.sound.midi.SysexMessage;

@SideOnly(Side.CLIENT)
public class CustomEventHandler {

    private Long tickCount = 0L;
    private int keyReleased = 0;
    private boolean lastTickKeyheld = false;
    private Collection<BlockPos> wartBlocks = new ArrayList<BlockPos>();
    private BlockGetter blockGetter = new BlockGetter();
    private static SecurityHandler SecurityHandler = new SecurityHandler();
    private static PermData permdat = new PermData();
    private EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
    private boolean connecting = false;
    private int tryToFly = 0;
    public TOGGLES toggles;
    public Long reconnectStartTick = 0L;
    private Long flyStartTick = 0L;
    public static int reconnects = 0;
    public static Long startTime;
    private volatile boolean shouldSend = true;
    private volatile boolean hasStopped = false;
    private volatile boolean isCheckingWart = true;
    private volatile boolean hadWartLastCheck = false;
    private boolean portalFrameLastCheck = false;
    private boolean lastTickKeyHeldRender = false;
    private int keyReleasedRender = 0;
    public static double[] ltp = new double[3];
    public static double[] lltp = new double[3];
    private RenderHandling renderHandler = new RenderHandling();
    private MutantWart mutantWart = new MutantWart();
    private boolean isJumpingOnPortal = false;

    public static boolean shouldRender = true;

    private void initNuker() {

        this.wartBlocks = this.blockGetter.getBlocks(3);

    }

    public CustomEventHandler() {
        new DiscordWebhook().permdat = this.permdat;
        this.toggles = new TOGGLES();
        //this.actions = new ActionHandler(permdat, toggles);
    }

    @SubscribeEvent
    public void hasTicked(PlayerTickEvent event) {






        if (this.tickCount == 0) {
            this.initNuker();
            toggles.initToggles(SecurityHandler, this.permdat);
        }
        if (!shouldRender) {
            Minecraft.getMinecraft().skipRenderWorld = true;
        } else {
            Minecraft.getMinecraft().skipRenderWorld = false;
        }

        if (SecurityHandler.getScriptPerfState()) {
            Minecraft.getMinecraft().effectRenderer.clearEffects(Minecraft.getMinecraft().theWorld);
        }

        act();
        if (Minecraft.getMinecraft().thePlayer != null && toggles.getWartToggle()) {
            lltp = ltp;
            ltp[0] = Minecraft.getMinecraft().thePlayer.posX;
            ltp[1] = Minecraft.getMinecraft().thePlayer.posY;
            ltp[2] = Minecraft.getMinecraft().thePlayer.posZ;
        }

        tickCount++;

//



//        if (tryToFly == 1) {
//            if (tickCount - flyStartTick == 2) {
//                Minecraft.getMinecraft().gameSettings.keyBindJump
//                        .setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindJump.getKeyCode(), true);
//                System.out.println("jump");
//                tryToFly = 2;
//                flyStartTick = tickCount;
//            }
//        }
//
//        else if (tryToFly == 2) {
//            if (tickCount - flyStartTick == 2) {
//                Minecraft.getMinecraft().gameSettings.keyBindJump
//                        .setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindJump.getKeyCode(), false);
//                tryToFly = 3;
//                flyStartTick = tickCount;
//            }
//        } else if (tryToFly == 3) {
//            if (tickCount - flyStartTick == 10) {
//                Minecraft.getMinecraft().gameSettings.keyBindJump
//                        .setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindJump.getKeyCode(), true);
//                tryToFly = 4;
//                System.out.println("jump");
//
//                flyStartTick = tickCount;
//            }
//        } else if (tryToFly == 4) {
//            if (tickCount - flyStartTick == 10) {
//                Minecraft.getMinecraft().gameSettings.keyBindJump
//                        .setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindJump.getKeyCode(), false);
//                tryToFly = 0;
//                // flyStartTick = tickCount;
//            }
//        }
//

            if (toggles.getWartToggle()) {
                if (!(Minecraft.getMinecraft().currentScreen instanceof ModGui)) {
                    if (Minecraft.getMinecraft().currentScreen != null) {
                        Minecraft.getMinecraft().currentScreen = null;
                    }
                }

                new scGlobal().hasMaptcha = new Hotbar().hasMaptcha();
            }

//
    if (tickCount % 100 == 0) {
        if (toggles.getWartToggle() && !new Hotbar().hasMaptcha()) {
            if (!new Hotbar().findHoe(Minecraft.getMinecraft().thePlayer)) {
                new ScriptLogger().log("Cannot find a hoe. Please Try Again.", new ScriptLogger().type.ERROR);
                toggles.toggleWart();
                return;
            }


        }
    }

//


        // check for key release
        if (lastTickKeyheld && keyReleased == 1) {
            keyReleased = 0;
            if (!toggles.getWartToggle()) {
                if (blockGetter.getBlocks(10).size() >= 1) {
                    new Utils().setTempDirToDir();
                    permdat.tempLeftOrRight = permdat.leftOrRight;
                    toggles.toggleWart();
                    mutantWart.resetWartCount();
                } else {
                    if (new SecurityHandler().hasCrop) {
                        new ScriptLogger().log("It appears you don't have any crops near you, huh...?",
                                new ScriptLogger().type.WARNING);
                    } else {
                        new ScriptLogger().log("It appears you don't have any wart near you, huh...?",
                                new ScriptLogger().type.WARNING);
                    }
                }
            } else {
                toggles.toggleWart();
            }
            if (toggles.getWartToggle()) {
                startTime = System.nanoTime();
                //actions.initActions();
                reconnects = 0;

            } else {
                startTime = null;
            }
        }
        lastTickKeyheld = false;
        // //

        if (lastTickKeyHeldRender && keyReleasedRender == 1) {
            keyReleasedRender = 0;
            if (Minecraft.getMinecraft().skipRenderWorld) {
                renderHandler.enableRender();
            } else {
                renderHandler.disableRender();
            }
        }
        lastTickKeyHeldRender = false;



        //check if on server


//        // stop every x seconds
//        if (toggles.getWartToggle()) {
//
//            if (!hasStopped) {
//                hasStopped = true;
//                ExecutorService execstop = Executors.newFixedThreadPool(1);
//                execstop.submit(() -> {
//                    try {
//
//                        TimeUnit.MINUTES.sleep(30);
//                        if (toggles.getWartToggle()) {
//                            toggles.scheduledWartStop();
//                        }
//                        System.gc();
//                        TimeUnit.SECONDS.sleep(15);
//                        if (!toggles.getWartToggle() && toggles.scheduledStop) {
//                            toggles.scheduledWartStop();
//                        }
//
//                        hasStopped = false;
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//
//                });
//            }
//        }
//
//        if (tickCount % 100 == 0)
//
//        {
//
//            if (toggles.getWartToggle()) {
////                Display.setTitle("Forge 1.8.9 - IGN: " + Minecraft.getMinecraft().getSession().getUsername()
////                        + " - Mutant Wart: " + mutantWart.getWartCount() + " - Status: " + toggles.getWartToggle());
//            }
//        }

//


        if (toggles.getWartToggle())

        {


            if (!permdat.isFlying) {
                if (!event.player.onGround) {
                    Minecraft.getMinecraft().gameSettings.keyBindSneak
                            .setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindSneak.getKeyCode(), true);
                } else {

                    Minecraft.getMinecraft().gameSettings.keyBindSneak
                            .setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindSneak.getKeyCode(), false);
                }
            }

        }


    }

    public void act() {
        if (toggles.getWartToggle()) {
            CurrentActionStates actions = new CurrentActionStates();
            GameSettings settings = Minecraft.getMinecraft().gameSettings;
            new MinecraftTweak().click();
            settings.keyBindLeft.setKeyBindState(settings.keyBindLeft.getKeyCode(), actions.left);
            settings.keyBindRight.setKeyBindState(settings.keyBindRight.getKeyCode(), actions.right);
            settings.keyBindJump.setKeyBindState(settings.keyBindJump.getKeyCode(), actions.jump);
            settings.keyBindForward.setKeyBindState(settings.keyBindForward.getKeyCode(), actions.forward);
            settings.keyBindBack.setKeyBindState(settings.keyBindBack.getKeyCode(), actions.back);
            settings.keyBindSneak.setKeyBindState(settings.keyBindSneak.getKeyCode(), actions.sneak);
            settings.keyBindSprint.setKeyBindState(settings.keyBindSprint.getKeyCode(), actions.sprint);


        }
    }



    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onKeyPress(InputEvent.KeyInputEvent event) {


        if (SecurityHandler.getScriptPerfState()) {
            if ((ClientProxy.keyBindingsPerf[0]).isPressed()) {
                keyReleased++;
                lastTickKeyheld = true;
            } else if ((ClientProxy.keyBindingsPerf[1]).isPressed()) {
                if (Minecraft.getMinecraft().theWorld.isRemote) {

                    Minecraft.getMinecraft().displayGuiScreen(new ModGui(permdat, new scGlobal().parentFolder));
                    //Minecraft.getMinecraft().displayGuiScreen(new RenderNotes());

                }
            } else if ((ClientProxy.keyBindingsPerf[2]).isPressed()) {
                Minecraft.getMinecraft().setIngameNotInFocus();
            }
            if (ClientProxy.keyBindingsPerf[3].isPressed()) {
                keyReleasedRender++;
                lastTickKeyHeldRender = true;
            }

        } else {
            if ((ClientProxy.keyBindings[0]).isPressed()) {
                System.out.println(ClientProxy.keyBindings[0].getKeyDescription());
                keyReleased++;
                lastTickKeyheld = true;
            } else if ((ClientProxy.keyBindings[1]).isPressed()) {
                if (Minecraft.getMinecraft().theWorld.isRemote) {

                    Minecraft.getMinecraft().displayGuiScreen(new ModGui(permdat, new scGlobal().parentFolder));
                    //Minecraft.getMinecraft().displayGuiScreen(new RenderNotes());
                }
            } else if ((ClientProxy.keyBindings[2]).isPressed()) {
                Minecraft.getMinecraft().setIngameNotInFocus();
                // new DiscordWebhook().sendWebhookTest(reconnects, startTime, toggles);
            }
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onDisconnect(ClientDisconnectionFromServerEvent event) {
        if (toggles.getWartToggle()) {
            if (!connecting) {
                System.out.println("connecting");
                if (!permdat.autostart) {
                    Minecraft.getMinecraft().addScheduledTask(new Runnable() {
                        @Override
                        public void run() {
                            try {

                                // FMLClientHandler.instance().connectToServer(

                                // FMLClientHandler.instance().getClient().currentScreen,

                                // field_146811_z)
                                FMLClientHandler.instance().displayGuiScreen(Minecraft.getMinecraft().thePlayer,
                                        new GuiMainMenu());
                                FMLClientHandler.instance().connectToServer(new GuiMainMenu(),
                                        new ServerData("", "play.hypixel.net", false));
                            } catch (Throwable e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
                toggles.toggleWart();
                reconnectStartTick = tickCount;
                connecting = true;

            }
        }

    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onDisconnectFrom(ServerDisconnectionFromClientEvent event) {
        if (toggles.getWartToggle()) {
            if (!connecting) {
                System.out.println("connecting");
                if (!permdat.autostart) {
                    Minecraft.getMinecraft().addScheduledTask(new Runnable() {
                        @Override
                        public void run() {
                            try {

                                // FMLClientHandler.instance().connectToServer(

                                // FMLClientHandler.instance().getClient().currentScreen,

                                // field_146811_z)
                                FMLClientHandler.instance().displayGuiScreen(Minecraft.getMinecraft().thePlayer,
                                        new GuiMainMenu());
                                FMLClientHandler.instance().connectToServer(new GuiMainMenu(),
                                        new ServerData("", "play.hypixel.net", false));
                            } catch (Throwable e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
                toggles.toggleWart();
                reconnectStartTick = tickCount;
                connecting = true;

            }
        }

    }


}