package com.scriptcentral.wartscript.gui.patchnotes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

public class GetNotes {
    public static String id;
    public static String text;
    public final String ADDRESS = "https://raw.githubusercontent.com/daisyrenton/yowhatspppoppin/master/patchnotes.txt";

    public String requestNotes() {
        try {
            URL url = new URL(ADDRESS);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("User-Agent", "curl/7.55.1");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");

            conn.connect();
            int responsecode = conn.getResponseCode();
            String res = IOUtils.toString(new InputStreamReader(conn.getInputStream()));

            return res;
        } catch (IOException err) {
            err.printStackTrace();

            return "";
        }
    }

    public void doPN() {
        this.text = requestNotes();
        setID();
        format();
        System.out.println(this.id);
        System.out.println(this.text);
    }

    public void setID() {
        String[] textArr = this.text.split("\\r?\\n|\\r");
        this.id = textArr[textArr.length-1].substring(4);
        String[] idlesstext = Arrays.copyOf(textArr, textArr.length-1);
        this.text = String.join("\n", idlesstext);
    }

    public String insert(String text, String insertion, int index) {
        StringBuilder builder = new StringBuilder(text);

        builder.insert(index, insertion);

        return builder.toString();
    }

    public void format() {
        //italics
        while (StringUtils.countMatches(this.text, "<i>") > 1) {
            int pos1 = this.text.indexOf("<i>");
            this.text = this.text.substring(0, pos1) + this.text.substring(pos1 + 3, this.text.length());
            this.text = insert(this.text, "§o", pos1);
            this.text = this.text.substring(0, pos1) + this.text.substring(pos1 + 1, this.text.length());

            int pos2 = this.text.indexOf("<i>");
            this.text = this.text.substring(0, pos2) + this.text.substring(pos2 + 3, this.text.length());

            this.text = insert(this.text, "§r", pos2);
            this.text = this.text.substring(0, pos2) + this.text.substring(pos2 + 1, this.text.length());

        }

    }
}
