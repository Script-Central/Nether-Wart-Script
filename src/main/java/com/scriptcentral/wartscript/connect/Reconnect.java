package com.scriptcentral.wartscript.connect;

import java.sql.Time;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.scriptcentral.wartscript.CustomEventHandler;
import com.scriptcentral.wartscript.PermData;
import com.scriptcentral.wartscript.TOGGLES;

import com.scriptcentral.wartscript.debuglog.Debug;
import com.scriptcentral.wartscript.gui.patchnotes.GetNotes;
import com.scriptcentral.wartscript.gui.patchnotes.RenderNotes;
import com.scriptcentral.wartscript.scGlobal;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Reconnect {
    private boolean newLobby = false;
    private TOGGLES toggles = new TOGGLES();
    private int reconnecting = 0;
    private int tryToFly = 0;
    public static boolean leftServer = false;

    private boolean connecting = false;

    public void doReconnect() {
        // if (reconnecting == 1) {
        connecting = true;
        new Debug().log("Detected reconnect; sequence starting");

        try {
            TimeUnit.SECONDS.sleep(15);
            // reconnecting++;

            System.out.println(1);

            newLobby = false;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        while (!newLobby) {
            Minecraft.getMinecraft().thePlayer.sendChatMessage("/l");
            new Debug().log("Going to main Lobby");
            try {
                TimeUnit.SECONDS.sleep(15);
                System.out.println(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        new Debug().log("In main Lobby");
        newLobby = false;
        while (!newLobby) {
            try {
                TimeUnit.SECONDS.sleep(15);
                System.out.println(3);

                // fly
                if (new PermData().isFlying) {
                    tryToFly = 1;
                    // flyStartTick = tickCount;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Minecraft.getMinecraft().thePlayer.sendChatMessage("/play skyblock");
            new Debug().log("Going to Skyblock");

        }
        newLobby = false;
        new Debug().log("In Skyblock");

        while (!newLobby) {
            try {
                TimeUnit.SECONDS.sleep(15);
                System.out.println(4);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Minecraft.getMinecraft().thePlayer.sendChatMessage("/is");
            new Debug().log("Going to island");

        }
        new Debug().log("On island; Starting the script again");
        toggles.toggleWart();
        System.out.println("restarted");
        new CustomEventHandler().reconnects++;
        new scGlobal().finishedReconnect = true;
        newLobby = false;
        connecting = false;

    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {
        if (event.message.getUnformattedText().equals("You're already sitting on this island!")) {
            newLobby = true;
        }
    }
    private String yay()
    {
        return Minecraft.getMinecraft().getIntegratedServer() != null ? (Minecraft.getMinecraft().getIntegratedServer().getPublic() ? "hosting_lan" : "singleplayer") : (Minecraft.getMinecraft().getCurrentServerData() != null ? (Minecraft.getMinecraft().getCurrentServerData().func_181041_d() ? "playing_lan" : "multiplayer") : "out_of_game");
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void tick(TickEvent.ClientTickEvent event) {


            if (!connecting) {
                if (!leftServer) {
                    if (yay().equals("out_of_game")) {
                        new Debug().log("In theory you just left the server and I'm about to reconnect you.");

                        //in main menu so set leftServer to true
                        leftServer = true;
                    }
                } else {
                    if (yay().equals("multiplayer")) {
                        new Debug().log("You should have just joined the server after disconnecting, rejoining you");
                        new scGlobal().finishedReconnect = false;
                        if (toggles.getWartToggle()) {
                            toggles.toggleWart();
                            //in a multiplayer server
                            ExecutorService execconnect = Executors.newFixedThreadPool(1);
                            execconnect.submit(() -> {

                                newLobby = true;
                                doReconnect();

                            });
                            if (!newLobby) {
                                new Debug().log("ok so I'm not 100% but I'm pretty sure this means that I failed in creating a new thread - do not mistake this to be for sure because this is just a guess.");
                            }
                        }
                        leftServer = false;
                    }
                }
            }

    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onEntityJoinWorldForPatchNotes(EntityJoinWorldEvent event) {
        if (event.entity instanceof EntityPlayerSP) {

            ExecutorService execconnect = Executors.newFixedThreadPool(1);
            execconnect.submit(() -> {
                while (Minecraft.getMinecraft().currentScreen != null) {
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                //display patch notes
                if (!new PermData().patchNotesId.equals(new GetNotes().id)) {
                    Minecraft.getMinecraft().displayGuiScreen(new RenderNotes());
                    new PermData().writeID(new scGlobal().parentFolder, new GetNotes().id);
                }


            });
        }


    }



    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinWorldEvent event) {



        ExecutorService execconnect = Executors.newFixedThreadPool(1);
        execconnect.submit(() -> {




            if (event.entity != null && event.entity instanceof EntityPlayerSP) {
                if (!connecting) {
                    //pass
                    if (toggles.getWartToggle()) {
                        toggles.toggleWart();


                            newLobby = true;
                            doReconnect();
                            new scGlobal().finishedReconnect = false;

                    }
                } else {
                    newLobby = true;
                }
                System.out.println("world changed!");
            } else {

                    try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (event.entity != null && event.entity instanceof EntityPlayerSP) {

                    if (!connecting) {
                        if (toggles.getWartToggle()) {
                            toggles.toggleWart();


                            newLobby = true;
                            doReconnect();
                            new scGlobal().finishedReconnect = false;

                        }

                    } else {
                        newLobby = true;
                    }
                    // if (event.entity != null && e instanceof EntityPlayerSP) {
                    System.out.println("world changed!");
                }

            }

        });
        // }
    }
}