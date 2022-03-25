package com.scriptcentral.wartscript.utils;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.scriptcentral.wartscript.PermData;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.BlockPos;

public class Utils {

    public String getTime() {
        return DateTimeFormatter.ofPattern("MMMM dd").format(LocalDateTime.now()) + " at "
                + DateTimeFormatter.ofPattern("KK:mm").format(LocalDateTime.now()) + " "
                + DateTimeFormatter.ofPattern("a").format(ZonedDateTime.now()) + " "
                + DateTimeFormatter.ofPattern("zz").format(ZonedDateTime.now());
    }





    public String getPrice(float f) {

        String formattedPrice = Float.toString(f);
        if (f > 1000) {
            formattedPrice = (new DecimalFormat("#.#").format(f / 1000)) + "K";
        }
        if (f > 1000000) {
            formattedPrice = (new DecimalFormat("#.##").format(f / 1000000)) + "M";
        }

        if (f > 1000000000) {
            formattedPrice = (new DecimalFormat("#.##").format(f / 1000000000)) + "B";
        }

        return formattedPrice;

    }

    public String getTimeDifference(Long start, Long end) {
//		Long millis = duration.toMillis();
//
//		Long hours = TimeUnit.MILLISECONDS.toHours(millis);
//		Long minutes = (TimeUnit.MILLISECONDS.toMinutes(millis)
//				- TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)));
//
//		Long seconds = (TimeUnit.MILLISECONDS.toSeconds(millis)
//				- TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));

        time timeClass = nanoToDuration(start, end);
        int hours = timeClass.hours;
        int seconds = timeClass.seconds;
        int minutes = timeClass.minutes;
        String time = "";
        if (hours < 10) {
            time += "0" + hours;
        } else {
            time += hours;
        }
        time += ":";
        if (minutes < 10) {
            time += "0" + minutes;
        } else {
            time += minutes;
        }

        time += ":";

        if (seconds < 10) {
            time += "0" + seconds;
        } else {
            time += seconds;
        }

        return time;
    }

    public time nanoToDuration(Long nano1, Long nano2) {
        Long nanoDuration = nano2 - nano1;

        // hours - 3600000000000 nano
        Long hours = (nanoDuration - (nanoDuration % 3600000000000L)) / 3600000000000L;
        nanoDuration = nanoDuration % 3600000000000L;
        Long minutes = (nanoDuration - (nanoDuration % 60000000000L)) / 60000000000L;
        nanoDuration = nanoDuration % 60000000000L;
        Long seconds = (nanoDuration - (nanoDuration % 1000000000L)) / 1000000000L;
        nanoDuration = nanoDuration % 1000000000L;
        return new time(Long.valueOf(hours).intValue(), Long.valueOf(minutes).intValue(),
                Long.valueOf(seconds).intValue());
    }

    public String getItemPrice(String ItemName) {

        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet("https://api.hypixel.net/skyblock/bazaar");
        StringEntity postingString;
        try {
            HttpEntity response = httpClient.execute(get).getEntity();
            Gson gson = new GsonBuilder().create();
            JsonObject res = gson.fromJson(EntityUtils.toString(response, "UTF-8"), JsonObject.class);

            return res.getAsJsonObject("products").getAsJsonObject(ItemName).getAsJsonArray("sell_summary").get(0)
                    .getAsJsonObject().get("pricePerUnit").getAsString();

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

    public static void burn(long leftOverTime) {
        // long deadline = System.nanoTime()+TimeUnit.MILLISECONDS.toNanos(millis);
        long deadline = System.nanoTime() + leftOverTime;

        while (System.nanoTime() < deadline) {
        }

    }

    public static void intervalBurn(int time, int intervalMS) {
        long deadline = System.nanoTime() + time * 1000000;
        while (System.nanoTime() < deadline) {
                try {
                    Thread.sleep(intervalMS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

        }
    }

    public NBTTagCompound getHoeNBT() {
        ItemStack item = Minecraft.getMinecraft().thePlayer.getHeldItem();

            return item.getTagCompound();

    }


    public Long getHoeCount() {
        try {
            NBTTagCompound nbt = getHoeNBT();

            Long hoeCount = nbt.getCompoundTag("ExtraAttributes").getLong("mined_crops");
            return hoeCount;
        } catch (Exception e) {
            return 0L;
        }
    }

    public static String stripColor(final String input) {
        return Pattern.compile("(?i)§[0-9A-FK-OR]").matcher(input).replaceAll("");
    }
    public static String keepScoreboardCharacters(String text) {
        return Pattern.compile("[^a-z A-Z:0-9/'.]").matcher(text).replaceAll("");
    }

    public String getPurse() {
        try {
            Collection<Score> scoreboardLines = Minecraft.getMinecraft().theWorld.getScoreboard().getSortedScores(Minecraft.getMinecraft().thePlayer.getWorldScoreboard().getObjectiveInDisplaySlot(1));
            List<Score> list = scoreboardLines.stream().filter(p_apply_1_ -> p_apply_1_.getPlayerName() != null && !p_apply_1_.getPlayerName().startsWith("#")).collect(Collectors.toList());
            if (list.size() > 15) {
                scoreboardLines = Lists.newArrayList(Iterables.skip(list, scoreboardLines.size() - 15));
            } else {
                scoreboardLines = list;
            }

            for (Score line : scoreboardLines) {
                ScorePlayerTeam scorePlayerTeam = Minecraft.getMinecraft().theWorld.getScoreboard().getPlayersTeam(line.getPlayerName());
                String strippedLine = new Utils().keepScoreboardCharacters(new Utils().stripColor(ScorePlayerTeam.formatPlayerName(scorePlayerTeam, line.getPlayerName()))).trim();
                if (strippedLine.startsWith("Purse: ")) {
                    return getPrice(Float.parseFloat(strippedLine.substring(8))/10F);
                }
            }
            return "N/A";
        } catch (Exception e) {
            System.out.println(e);
            return "N/A";
        }
    }




    public String getActualHoeName() {
        String name = getHoeName();
        if (name.equals("THEORETICAL_HOE_WARTS_3")) {
            return "Tier 3 Wart Hoe";
        } else if (name.equals("THEORETICAL_HOE_WARTS_2")) {
            return "Tier 2 Wart Hoe";
        } else if (name.equals("THEORETICAL_HOE_WARTS_1")) {
            return "Tier 1 Wart Hoe";
        } else if (name.equals("THEORETICAL_HOE_CARROT_1")) {
            return "Tier 1 Carrot Hoe";
        } else if (name.equals("THEORETICAL_HOE_CARROT_2")) {
            return "Tier 2 Carrot Hoe";
        } else if (name.equals("THEORETICAL_HOE_CARROT_3")) {
            return "Tier 3 Carrot Hoe";
        } else if (name.equals("THEORETICAL_HOE_POTATO_1")) {
            return "Tier 1 Potato Hoe";
        } else if (name.equals("THEORETICAL_HOE_POTATO_2")) {
            return "Tier 2 Potato Hoe";
        } else if (name.equals("THEORETICAL_HOE_POTATO_3")) {
            return "Tier 3 Potato Hoe";
        } else if (name.equals("THEORETICAL_HOE_WHEAT_1")) {
            return "Tier 1 Wheat Hoe";
        } else if (name.equals("THEORETICAL_HOE_WHEAT_2")) {
            return "Tier 2 Wheat Hoe";
        } else if (name.equals("THEORETICAL_HOE_WHEAT_3")) {
            return "Tier 3 Wheat Hoe";
        } else {
            return "Hoe";
        }
    }

    public String getHoeName() {
        try {
            NBTTagCompound nbt = getHoeNBT();

            String hoeName = nbt.getCompoundTag("ExtraAttributes").getString("id");
            return hoeName;
        } catch (Exception e) {
            return "N/A";
        }
    }


    public boolean xInRangeY(float x, float y, float range) {
        float diff = x - y;
        if (diff >= -range && diff <= range) {
            return true;
        }
        return false;
    }

    public boolean blockXInRangePlayer(Block block, double range) {
        EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
        BlockPos from = new BlockPos(player.posX - range, player.posY - range, player.posZ - range);
        BlockPos to = new BlockPos(player.posX + range, player.posY + range, player.posZ + range);

        Iterable<BlockPos> blocks = BlockPos.getAllInBox(from, to);
        boolean isInRange = false;
        List<BlockPos> blocksCol = new ArrayList<BlockPos>();
        blocks.forEach(blocksCol::add);
        for (int i = 0; i < blocksCol.size(); i++) {
            if (Minecraft.getMinecraft().theWorld.getBlockState(blocksCol.get(i)).getBlock().equals(block)) {
                return true;
            }
        }

        return false;

    }


    public CropStringFormat getCropString(int car, int pot, int wheat, int wart, int hb) {
        CropStringFormat csf = new CropStringFormat();
        if (car > pot && car > wheat && car > wart && car > hb) {
            csf.name = "Enchanted Carrots";
            csf.value = car;
            csf.lookupName = "ENCHANTED_CARROT";
        } else if (pot > car && pot > wheat && pot > wart && pot > hb) {
            csf.name = "Enchanted Baked Potatoes";
            csf.value = pot;
            csf.lookupName = "ENCHANTED_BAKED_POTATO";
        } else if (wheat > car && wheat > pot && wheat > wart && wheat > hb) {
            csf.name = "Tightly-Tied Hay Bales";
            csf.value = wheat;
            csf.lookupName = "TIGHTLY_TIED_HAY_BALE";
        } else if (hb > car && hb > pot && hb > wart && hb > wheat) {
            csf.name = "Enchanted Hay Bale";
            csf.value = hb;
            csf.lookupName = "ENCHANTED_HAY_BLOCK";
        } else {
            csf.name = "Mutant Nether Wart";
            csf.value = wart;
            csf.lookupName = "MUTANT_NETHER_STALK";
        }
        return csf;
    }

    public class CropStringFormat {
        public int value;
        public String lookupName;
        public String name;
    }



    public boolean isFlying() {

        return (Minecraft.getMinecraft().thePlayer.motionY == (double)0 && Minecraft.getMinecraft().thePlayer.isAirBorne);
    }

    public boolean checkForPortal() {
        BlockPos pos = new BlockPos(MathHelper.floor_double(Minecraft.getMinecraft().thePlayer.posX), MathHelper.floor_double(Minecraft.getMinecraft().thePlayer.posY), MathHelper.floor_double(Minecraft.getMinecraft().thePlayer.posZ));
        return (Minecraft.getMinecraft().theWorld.getBlockState(pos.add(0, 0, 0)).getBlock().equals(Blocks.end_portal_frame) ||
                Minecraft.getMinecraft().theWorld.getBlockState(pos.add(1, 0, 0)).getBlock().equals(Blocks.end_portal_frame) ||
                Minecraft.getMinecraft().theWorld.getBlockState(pos.add(0, 0, 1)).getBlock().equals(Blocks.end_portal_frame) ||
                Minecraft.getMinecraft().theWorld.getBlockState(pos.add(-1, 0, 0)).getBlock().equals(Blocks.end_portal_frame) ||
                Minecraft.getMinecraft().theWorld.getBlockState(pos.add(0, 0, -1)).getBlock().equals(Blocks.end_portal_frame)
        );
    }

    public void setTempDirToDir() {
        PermData permdat = new PermData();
        if (permdat.dir.numVal == 90) {
            permdat.tempDir = permdat.tempDir.WEST;
        }
        else if (permdat.dir.numVal == 180) {
            permdat.tempDir = permdat.tempDir.NORTH;
        }
        else if (permdat.dir.numVal == -90) {
            permdat.tempDir = permdat.tempDir.EAST;
        }
        else if (permdat.dir.numVal == 0) {
            permdat.tempDir = permdat.tempDir.SOUTH;
        }
    }

    public class time {
        public int hours;
        public int minutes;
        public int seconds;

        public time(int hours, int minutes, int seconds) {
            this.hours = hours;
            this.minutes = minutes;
            this.seconds = seconds;
        }
    }

}
