package com.scriptcentral.wartscript;

        import java.net.MalformedURLException;

        import com.scriptcentral.wartscript.Threads.AuthCheck;
        import com.scriptcentral.wartscript.Threads.SendWebhook;
        import com.scriptcentral.wartscript.Threads.Title;
        import com.scriptcentral.wartscript.Threads.WartChecker;
        import com.scriptcentral.wartscript.actions.Failsafe;
        import com.scriptcentral.wartscript.commands.ClientTickCommand;
        import com.scriptcentral.wartscript.commands.FastbreakCommand;
        import com.scriptcentral.wartscript.commands.Rowspersetspawn;
        import com.scriptcentral.wartscript.commands.StartCommand;
        import com.scriptcentral.wartscript.connect.Reconnect;
        import com.scriptcentral.wartscript.debuglog.Debug;
        import com.scriptcentral.wartscript.gui.patchnotes.GetNotes;
        import com.scriptcentral.wartscript.inventory.MutantWart;
        import com.scriptcentral.wartscript.mouse.RandomLook;
        import com.scriptcentral.wartscript.movement.ClientTickChanger;
        import com.scriptcentral.wartscript.proxy.CommonProxy;
        import com.scriptcentral.wartscript.security.SecurityHandler;
        import com.scriptcentral.wartscript.utils.MinecraftTweak;
        import com.scriptcentral.wartscript.utils.RenderHandling;

        import net.minecraft.client.Minecraft;
        import net.minecraftforge.client.ClientCommandHandler;
        import net.minecraftforge.common.MinecraftForge;
        import net.minecraftforge.fml.common.Mod;
        import net.minecraftforge.fml.common.Mod.EventHandler;
        import net.minecraftforge.fml.common.Mod.Instance;
        import net.minecraftforge.fml.common.SidedProxy;
        import net.minecraftforge.fml.common.event.FMLInitializationEvent;
        import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
        import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
        import org.lwjgl.opengl.Display;

@Mod(modid = scGlobal.MOD_ID, name = scGlobal.MOD_NAME, version = scGlobal.VERSION)
public class Main {

    @Instance(scGlobal.MOD_ID)
    public static Main instance;

    @SidedProxy(clientSide = scGlobal.SC_CLIENT_PROXY, serverSide = scGlobal.SC_COMMON_PROXY)
    public static CommonProxy proxy;

    // ConfigHandler ConfigHandler = new ConfigHandler();
    SecurityHandler securityHandler = new SecurityHandler();
    PermData permdat = new PermData();
    ClientTickChanger tickChanger;
    @EventHandler
    public void preInit(FMLPreInitializationEvent preEvent) {

            securityHandler.init();

        Display.setTitle("Minecraft 1.8.9 - IGN: " + Minecraft.getMinecraft().getSession().getUsername());

        this.proxy.preInit(preEvent);
        new GetNotes().doPN();
        scGlobal.parentFolder = preEvent.getModConfigurationDirectory().getParentFile();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) throws MalformedURLException {
        this.proxy.init(event);
        new Debug().createLog();
        new Debug().log("Launching");
        new Thread(new AuthCheck()).start();

        new Thread(new WartChecker()).start();
        //new Thread(new RandomLook()).start();

        new Thread(new Failsafe()).start();

        permdat.readConfigs(scGlobal.parentFolder);
        MinecraftForge.EVENT_BUS.register(new CustomEventHandler());
        MinecraftForge.EVENT_BUS.register(new RenderHandling());
        MinecraftForge.EVENT_BUS.register(new MutantWart());
        MinecraftForge.EVENT_BUS.register(new Reconnect());

        Minecraft.getMinecraft().gameSettings.pauseOnLostFocus = false;
        Minecraft.getMinecraft().mouseHelper = new MinecraftTweak().customMouseHelper;

        ClientCommandHandler.instance.registerCommand(new StartCommand(permdat, scGlobal.parentFolder));
        ClientCommandHandler.instance.registerCommand(new Rowspersetspawn(scGlobal.parentFolder));
        ClientCommandHandler.instance.registerCommand(new FastbreakCommand());

        //tickChanger = new ClientTickChanger();
        //ClientCommandHandler.instance.registerCommand(new ClientTickCommand(tickChanger));

        new NetherWartEdit().setWartProperties();

    }

    @EventHandler

    public void postInit(FMLPostInitializationEvent postEvent) {
        this.proxy.postInit(postEvent);
        new Thread(new Title()).start();
        new Thread(new SendWebhook()).start();


    }

}
