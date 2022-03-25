package com.scriptcentral.wartscript.utils;


import java.io.IOException;

import com.scriptcentral.wartscript.security.SecurityHandler;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import com.google.gson.Gson;
import com.scriptcentral.wartscript.PermData;
import com.scriptcentral.wartscript.TOGGLES;
import com.scriptcentral.wartscript.inventory.MutantWart;

import net.minecraft.client.Minecraft;

public class DiscordWebhook {
    public static PermData permdat;
    public MutantWart mutantWart = new MutantWart();

    public void sendDebugData(String message) {
        postDataForDebug data = new postDataForDebug();
        data.content = message;
        data.username = Minecraft.getMinecraft().getSession().getUsername();



        Gson gson = new Gson();

        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost("https://discord.com/api/webhooks/893975579295100928/kH1EXPVdI2kvwU88RNM3ZOSGedeVI_5EDOleqWb0eoonKsFeiDsFDJGvDROdaj0BBjWt");
        //before you get suspicious, this just sends me a message when somebody opens the script, purely out of interest
        //check the code, it literally only sends the username, time, and the log
        //helps for debugging
        StringEntity postingString;
        try {
            postingString = new StringEntity(gson.toJson(data));
            System.out.println(gson.toJson(data));
            post.setEntity(postingString);
            post.setHeader("Content-type", "application/json");
            HttpResponse response = httpClient.execute(post);
            System.out.println(response.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    class postDataForDebug {
        String content;
        String username;
    }

    public void sendWebhook(int reconnects, Long startTime, TOGGLES toggles) {

        if (permdat.webhook != null) {
            System.out.println(permdat.webhook);
            if (permdat.webhook.startsWith("https://discord.com/api/webhooks")) {

                postData data = new postData();
                if (new SecurityHandler().hasCrop) {
                    data.username = "Script Central Farming Script";
                } else {
                    data.username = "Script Central Wart Script";
                }
                data.embeds[0].thumbnail.url = "https://crafatar.com/avatars/"
                        + Minecraft.getMinecraft().getSession().getPlayerID();
                data.embeds[0].title = Minecraft.getMinecraft().getSession().getUsername() + (toggles.getWartToggle()
                        ? " || Running for " + new Utils().getTimeDifference(startTime, System.nanoTime())
                        : " || Bot not running");



                if (new SecurityHandler().hasCrop) {
                    int carCount = new MutantWart().carCount;
                    int potCount = new MutantWart().potCount;
                    int wheatCount = new MutantWart().wheatCount;
                    int hbCount = new MutantWart().hbCount;
                    int wartCount = new MutantWart().getWartCount();
                    if (new TOGGLES().getWartToggle()) {
                        data.embeds[0].color = 2804760;
                    } else {
                        data.embeds[0].color = 12718353;
                    }
                    data.embeds[0].fields[0].name = "Farmed " + new Utils().getCropString(carCount, potCount, wheatCount, wartCount, hbCount).name + ":";
                    data.embeds[0].fields[0].value = "```"+ Integer
                            .toString(new Utils().getCropString(carCount, potCount, wheatCount, wartCount, hbCount).value) + "```";
                    data.embeds[0].fields[1].name = "Profit";
                    data.embeds[0].fields[1].value = "```" + new Utils().getPrice(Float.parseFloat(
                            new Utils().getItemPrice(new Utils().getCropString(carCount, potCount, wheatCount, wartCount, hbCount).lookupName))
                            * new Utils().getCropString(carCount, potCount, wheatCount, wartCount, hbCount).value) + "```";
                } else {
                    if (new TOGGLES().getWartToggle()) {
                        data.embeds[0].color = 2804760;
                    } else {
                        data.embeds[0].color = 12718353;
                    }
                    data.embeds[0].fields[0].name = "Farmed Mutant Wart:";
                    data.embeds[0].fields[0].value = "```" + Integer.toString(mutantWart.getWartCount()) + "```";
                    data.embeds[0].fields[1].name = "Profit";


                    data.embeds[0].fields[1].value = "```" + new Utils().getPrice(
                            Float.parseFloat(new Utils().getItemPrice("MUTANT_NETHER_STALK")) * mutantWart.getWartCount()) + "```";
                    data.embeds[0].footer.text = "Script Central | " + new Utils().getTime();
                }
                // profit value
                data.embeds[0].fields[2].name = "Reconnects:";

                // TODO: this

                if (startTime != null) {
                    data.embeds[0].fields[2].value = "```Overall: " + reconnects + " || Hourly: "
                            + (new Utils().nanoToDuration(startTime, System.nanoTime()).hours == 0 ? reconnects
                            : (reconnects / new Utils().nanoToDuration(startTime, System.nanoTime()).hours)) + "```";
                } else {
                    data.embeds[0].fields[2].value = "```Overall: 0 || Hourly: 0```";
                }

                data.embeds[0].fields[3].name = new Utils().getActualHoeName() + " count: ";
                data.embeds[0].fields[3].value = "```" + Long.toString(new Utils().getHoeCount()) + "```";

                data.embeds[0].fields[4].name = "Purse: ";
                data.embeds[0].fields[4].value = "```" + new Utils().getPurse() + "```";


                        data.avatar_url = "https://colourlex.com/wp-content/uploads/2017/04/Spinel-black-painted-swatch-47400-opt.jpg";
                Gson gson = new Gson();

                HttpClient httpClient = HttpClientBuilder.create().build();
                HttpPost post = new HttpPost(permdat.webhook);
                StringEntity postingString;
                try {
                    postingString = new StringEntity(gson.toJson(data));

                    System.out.println(gson.toJson(data));
                    post.setEntity(postingString);
                    post.setHeader("Content-type", "application/json");
                    HttpResponse response = httpClient.execute(post);
                    System.out.println(response.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }

    }

    public void sendMaptchaMessage(String url) {

        if (url != null) {
            if (url.startsWith("https://discord.com/api/webhooks")) {

                maptchaData data = new maptchaData();
                data.username = "Script Central Maptcha Alert";

                data.embeds[0].thumbnail.url = "https://static.wikia.nocookie.net/minecraft/images/3/30/MapOld.png/revision/latest?cb=20190928063128";
                data.embeds[0].title = Minecraft.getMinecraft().getSession().getUsername() + " needs you to input a maptcha!";
                data.embeds[0].description = "Please return to game to input the maptcha. Automatic Solver is in the making.";






                data.avatar_url = "https://colourlex.com/wp-content/uploads/2017/04/Spinel-black-painted-swatch-47400-opt.jpg";
                Gson gson = new Gson();

                HttpClient httpClient = HttpClientBuilder.create().build();
                //HttpPost post = new HttpPost(permdat.webhook);
                HttpPost post = new HttpPost(url);
                StringEntity postingString;
                try {
                    postingString = new StringEntity(gson.toJson(data));

                    post.setEntity(postingString);
                    post.setHeader("Content-type", "application/json");
                    HttpResponse response = httpClient.execute(post);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }

    }

///////////////////////////////////////////////


    class maptchaData {
        String username;
        maptchaEmbed[] embeds = {new maptchaEmbed() };
        String avatar_url;
    }
    class maptchaEmbed {
        String title;
        String description;
        int color = 12718353;
        maptchaThumbnail thumbnail = new maptchaThumbnail();
    }

    class maptchaThumbnail {
        String url;
    }

    /////////////////////////////

    class postData {
        String username;
        embed[] embeds = { new embed() };
        String avatar_url;
    }

    class embed {
        String title;
        int color;
        fields[] fields = { new fields(), new fields(), new fields(), new fields(), new fields()};
        thumbnail thumbnail = new thumbnail();
        footer footer = new footer();
    }


    class footer {
        String text = "Script Central";
    }

    class fields {
        String name;
        String value;
    }

    class thumbnail {
        String url;
    }

}
