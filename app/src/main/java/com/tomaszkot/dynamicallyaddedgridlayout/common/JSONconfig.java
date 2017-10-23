package com.tomaszkot.dynamicallyaddedgridlayout.common;

import android.content.Context;
import android.util.Log;

import com.tomaszkot.dynamicallyaddedgridlayout.ListItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class JSONconfig {
    private FileOutputStream outputStream;
    private String filename = "circles";   // JSON file name
    private String readingString;
    private int readingColor;
    private Context context;

    public JSONconfig(Context context) {
        this.context = context;
        try {
            checkJSONfile();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     *  Checking if the JSON file already exists if not we create a new
     */
    private void checkJSONfile() throws IOException, JSONException {
        if (!fileExistance(filename)) {
            createNewFile();
        }
    }
    // --------------------------------------------------------------------------------------------- Checking if the JSON file exists
    private boolean fileExistance(String fname){
        File file = context.getFileStreamPath(fname);
        return file.exists();
    }

    // --------------------------------------------------------------------------------------------- Loading file
    private JSONObject readFile() throws IOException, JSONException {
        String receiveString;
        InputStream inputStream = context.openFileInput(filename);
        JSONObject jsonObj = null;

        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            StringBuilder stringBuilder = new StringBuilder();
            while ((receiveString = bufferedReader.readLine()) != null) {
                stringBuilder.append(receiveString);
            }
            inputStream.close();
            String ret = stringBuilder.toString();
            jsonObj = new JSONObject(ret);
        }
        return jsonObj;
    }

     // -------------------------------------------------------------------------------------------- Reading and adding data
     private JSONArray getArrayJson(String rodzaj){
        JSONArray arrayGrupy = null;
        try {
            JSONObject myJobj = readFile();
            arrayGrupy = myJobj.getJSONArray(rodzaj);
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arrayGrupy;
    }

    public ListItem getItemFromJSON(int numerWtabl){
        try {
            JSONObject myJobj = readFile();
            JSONArray arrayGrupy = myJobj.getJSONArray("Items");
            JSONObject obj = arrayGrupy.getJSONObject(numerWtabl);
            readingString = obj.getString("name");
            readingColor = obj.getInt("color");
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new ListItem(readingString, readingColor);
    }

    public int getJSONlistLenght(){
        return getArrayJson("Items").length();
    }

    public void addItemtoJSON(String appName, int color){
        try {
            JSONObject myJobj = readFile();
            JSONArray arrayGrupy = myJobj.getJSONArray("Items");

            JSONObject newGrupa = new JSONObject();
            newGrupa.put("name", appName);
            newGrupa.put("color", color);
            arrayGrupy.put(newGrupa);

            readingString =  myJobj.toString() ;
            outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(readingString.getBytes());
            outputStream.close();
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // --------------------------------------------------------------------------------------------- Removing data
    public void removeItemFormJSON(int position){

        try {
            JSONObject itemObject = readFile();
            JSONArray arrayGrupy = itemObject.getJSONArray("Items");
            JSONArray newArray = rebuildData(arrayGrupy, position);
            itemObject.put("Items",newArray);

            readingString =  itemObject.toString() ;
            outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(readingString.getBytes());
            outputStream.close();
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private static JSONArray rebuildData(JSONArray item, int pos) {
        JSONArray array = new JSONArray();
        try {
            for (int i = 0; i < item.length(); i++) {
                if (i != pos)
                    array.put(item.get(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return array;
    }

    // --------------------------------------------------------------------------------------------- Create new json file
    private void createNewFile() {
        try {
            JSONArray saveItems = new JSONArray();
            JSONObject allObcects = new JSONObject();
            allObcects.put("Items", saveItems);

            String allObjectsString = allObcects.toString();
            FileOutputStream outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(allObjectsString.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}