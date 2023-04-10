package com.example.fcctut;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

// Purpose: read and save to local files
// can perform this operation for both strings and JSONObjects
public class FileManager {
    public static void writeToFile(Context ctx, String filename, String content) {
        try {
            FileOutputStream outputStream = ctx.openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(content.getBytes());

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void writeToFile(Context ctx, String filename, JSONObject json) {
        String content = json.toString();
//        filename = ctx.getFilesDir().getAbsolutePath() + "/" + filename;
//        filename = ctx.getFilesDir().getAbsolutePath();
        Log.d("testing Trip", filename);
        try {
            FileOutputStream outputStream = ctx.openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(content.getBytes());
            Log.d("testing Trip", "File saved successfully as " + filename);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String readFromFile(Context ctx, String filename) {
        FileInputStream fis = null;
        try {
            fis = ctx.openFileInput(filename);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        InputStreamReader inputStreamReader =
                new InputStreamReader(fis, StandardCharsets.UTF_8);
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(inputStreamReader)) {
            String line = reader.readLine();
            while (line != null) {
                stringBuilder.append(line).append('\n');
                line = reader.readLine();
            }
        } catch (IOException e) {
            // Error occurred when opening raw file for reading.
        } finally {
            String contents = stringBuilder.toString();
            return contents;
        }
    }

    public static JSONObject readJsonFromFile(Context ctx, String filename) {
        FileInputStream fis = null;
        try {
            fis = ctx.openFileInput(filename);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        InputStreamReader inputStreamReader =
                new InputStreamReader(fis, StandardCharsets.UTF_8);
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(inputStreamReader)) {
            String line = reader.readLine();
            while (line != null) {
                stringBuilder.append(line).append('\n');
                line = reader.readLine();
            }
        } catch (IOException e) {
            // Error occurred when opening raw file for reading.
            throw new RuntimeException(e);
        } finally {
            String contents = stringBuilder.toString();
            try {
                return new JSONObject(contents);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
