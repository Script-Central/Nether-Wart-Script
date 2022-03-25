package com.scriptcentral.wartscript;

        import java.io.BufferedWriter;
        import java.io.File;
        import java.io.FileInputStream;
        import java.io.FileNotFoundException;
        import java.io.FileWriter;
        import java.io.IOException;
        import java.io.InputStream;
        import java.io.InputStreamReader;
        import java.util.concurrent.TimeUnit;

        import com.scriptcentral.wartscript.utils.Utils;
        import org.apache.commons.io.IOUtils;

        import com.google.gson.Gson;
        import com.google.gson.GsonBuilder;
        import com.google.gson.JsonObject;
        import com.google.gson.JsonSyntaxException;

public class PermData {
    public static String patchNotesId;
    public static boolean leftOrRight;
    public static boolean tempLeftOrRight;
    public static boolean isFlying;
    public static String webhook = "";
    public static boolean autostart;
    public static DIR dir;
    public static TEMPDIR tempDir;
    public static int webhooktimer = 30;
    public static boolean movementType;
    public static int rowsPerSetSpawn = 1;
    public enum DIR {
        WEST(90), NORTH(180), EAST(-90), SOUTH(0);

        public int numVal;

        DIR(int numVal) {
            this.numVal = numVal;
        }

    }

    public enum TEMPDIR {
        WEST(90), NORTH(180), EAST(-90), SOUTH(0);

        public int numVal;

        TEMPDIR(int numVal) {
            this.numVal = numVal;
        }

    }

    public void writeID(File folder, String id) {
        try {




            File configFile = new File(folder.getAbsolutePath() + "/script_config.json");
            BufferedWriter writeFile;
            writeFile = new BufferedWriter(new FileWriter(configFile.getAbsoluteFile()));

            writeFile.write("{\r\n" + "   \"lor\":" + this.leftOrRight + ",\r\n" + "   \"flying\":" + this.isFlying + ",\r\n"
                    + "   \"dir\":" + dir.numVal + ",\r\n" + "   \"webhooktimer\":" + this.webhooktimer + ",\r\n" + "\"autostart\":"
                    + this.autostart + ",\r\n" + "\"webhook\":\"" + this.webhook + "\",\r\n" + "\"movementtype\":\"" + movementType + "\",\r\n" + "\"rowsperspawn\":\"" + this.rowsPerSetSpawn + "\",\r\n" + "\"patchnotesid\":\"" + id + "\"\r\n" + "}");
            writeFile.close();

            this.patchNotesId = id;
        } catch(IOException e) {
            System.out.println(e);
        }
    }

    public void readConfigs(File folder) {
        // try {
        // InputStream configs = mc.getResource(location).getInputStream();
        // } catch (IOException e) {

        // e.printStackTrace();
        // }
        File configFile = new File(folder.getAbsolutePath() + "/script_config.json");
        if (configFile.exists()) {
            try {
                InputStream fileContents = new FileInputStream(configFile);

                Gson gson = new GsonBuilder().create();
                try {
                    JsonObject res = gson.fromJson(IOUtils.toString(new InputStreamReader(fileContents)),
                            JsonObject.class);

                    System.out.println(configFile.getAbsoluteFile());
                    // actually use the json object finally
                    this.patchNotesId = res.get("patchnotesid").getAsString();
                    this.movementType = res.get("movementtype").getAsBoolean();
                    this.rowsPerSetSpawn = res.get("rowsperspawn").getAsInt();
                    int dir = res.get("dir").getAsInt();
                    System.out.println(dir);
                    if (dir == 90) {
                        this.dir = DIR.WEST;
                    } else if (dir == 180) {
                        this.dir = DIR.NORTH;
                    } else if (dir == -90) {
                        this.dir = DIR.EAST;
                    } else if (dir == 0) {
                        this.dir = DIR.SOUTH;
                    }
                    new Utils().setTempDirToDir();


                    System.out.println(this.dir);
                    this.webhooktimer = res.get("webhooktimer").getAsInt();
                    this.webhook = res.get("webhook").getAsString();

                    this.autostart = res.get("autostart").getAsBoolean();
                    leftOrRight = res.get("lor").getAsBoolean();
                    tempLeftOrRight = leftOrRight;
                    isFlying = res.get("flying").getAsBoolean();

                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        } else {
            try {
                configFile.createNewFile();
                BufferedWriter writeFile = new BufferedWriter(new FileWriter(configFile.getAbsoluteFile()));
                writeFile.write("{\r\n" + "   \"lor\":false,\r\n" + "   \"flying\":true,\r\n" + "   \"dir\":90,\r\n"
                        + "\"webhooktimer\":30,\r\n" + "\"autostart\":false,\r\n" + "\"webhook\":\"\",\r\n" + "   \"movementtype\":false,\r\n" + "   \"rowsperspawn\":1,\r\n" + "\"patchnotesid\":\"\"\r\n" + "}");
                System.out.println("written");
                writeFile.close();
                try {
                    TimeUnit.SECONDS.sleep(4);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                readConfigs(folder);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void writeConfigs(File folder, boolean lor, boolean flying, int dir, String webhookurl, boolean autoStart,
                             int webhookTimer, boolean movementType, int rows) {
        try {

            int currentDir = 0;
            if (this.dir == DIR.WEST) {
                currentDir = 90;
            } else if (this.dir == DIR.NORTH) {
                currentDir = 180;
            } else if (this.dir == DIR.EAST) {
                currentDir = -90;
            }

            int change = 0;
            if (this.leftOrRight != lor) {
                change = 1;
            } else if (this.movementType != movementType) {
                change = 6;
            } else if (this.isFlying != flying) {
                change = 2;
            } else if (currentDir != dir) {
                change = 3;
            } else if (autoStart != autostart) {
                change = 4;
            } else if (webhookurl != webhook || webhooktimer != webhookTimer) {
                change = 5;
            }
            this.movementType = movementType;
            this.leftOrRight = lor;
            this.tempLeftOrRight = lor;
            this.isFlying = flying;
            this.rowsPerSetSpawn = rows;
            this.webhook = webhookurl;
            this.autostart = autoStart;
            this.webhooktimer = webhookTimer;
            if (dir == 90) {
                this.dir = DIR.WEST;
            } else if (dir == 180) {
                this.dir = DIR.NORTH;
            } else if (dir == -90) {
                this.dir = DIR.EAST;
            } else if (dir == 0) {
                this.dir = DIR.SOUTH;
            }

            new Utils().setTempDirToDir();

            File configFile = new File(folder.getAbsolutePath() + "/script_config.json");
            BufferedWriter writeFile;
            writeFile = new BufferedWriter(new FileWriter(configFile.getAbsoluteFile()));

            writeFile.write("{\r\n" + "   \"lor\":" + lor + ",\r\n" + "   \"flying\":" + flying + ",\r\n"
                    + "   \"dir\":" + dir + ",\r\n" + "   \"webhooktimer\":" + webhookTimer + ",\r\n" + "\"autostart\":"
                    + autoStart + ",\r\n" + "\"webhook\":\"" + webhookurl + "\",\r\n" + "\"movementtype\":\"" + movementType + "\",\r\n" + "\"rowsperspawn\":\"" + rows + "\",\r\n" + "\"patchnotesid\":\"" + this.patchNotesId + "\"\r\n" + "}");

            System.out.println("written");
            if (change == 1) {
                new ScriptLogger().log("Starting movement changed to " + (lor ? "left" : "right"),
                        new ScriptLogger().type.SUCCESS);
            } else if (change == 0) {
            } else if (change == 2) {
                new ScriptLogger().log("Flying Changed to " + flying, new ScriptLogger().type.SUCCESS);
            } else if (change == 3) {
                new ScriptLogger().log("Direction changed to " + dir, new ScriptLogger().type.SUCCESS);
            } else if (change == 4) {
                new ScriptLogger().log("Auto start changed to " + autoStart, new ScriptLogger().type.SUCCESS);
            } else if (change == 6) {
                new ScriptLogger().log("Movement type changed to " + (movementType ? "Legacy" : "Standard"), new ScriptLogger().type.SUCCESS);

            }
            writeFile.close();

        } catch (IOException err2) {
            err2.printStackTrace();
        }
    }
}
