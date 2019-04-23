package com.mytest.testcsapp;

import android.content.res.Resources;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MessageEntry {

    private static final String TAG = MessageEntry.class.getSimpleName();

    private String message;
    private String url;
    private String date;
    private String name;
    private boolean isMe;
    private boolean isAd;


    public MessageEntry(String message, String url, String date, String name, boolean isMe, boolean isAd) {
        this.message = message;
        this.url = url;
        this.date = date;
        this.name = name;
        this.isMe = isMe;
        this.isAd = isAd;
    }

    public String getDate() {
        return date;
    }

    public String getMessage() {
        return message;
    }

    public String getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }

    public boolean isMe() {
        return isMe;
    }

    public boolean isAd() {
        return isAd;
    }

    public static List<MessageEntry> initMessageEntryList(Resources resources) {
        InputStream inputStream = resources.openRawResource(R.raw.message);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];

        try {
            Reader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            int pointer;
            while ((pointer = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, pointer);
            }
        } catch (IOException ioe) {
            Log.e(TAG, "Error writing/reading from the JSON file.", ioe);
        } finally {
            try {
                inputStream.close();
            } catch (IOException ioe) {
                Log.e(TAG, "Error closing the input stream.", ioe);
            }
        }

        String jsonMessageString = writer.toString();
        Gson gson = new Gson();
        Type messageListType = new TypeToken<ArrayList<MessageEntry>>(){}.getType();
        return gson.fromJson(jsonMessageString, messageListType);
    }
}
