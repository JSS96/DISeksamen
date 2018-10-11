package utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public final class Config {

    private static String DATABASE_HOST;
    private static Integer DATABASE_PORT;
    private static String DATABASE_USERNAME;
    private static String DATABASE_PASSWORD;
    private static String DATABASE_NAME;
    private static boolean ENCRYPTION;

    public void initializeConfig() throws IOException {

        // Init variables to parse JSON
        JsonObject json = new JsonObject();
        JsonParser parser = new JsonParser();

        // Read File and store input
        InputStream input = this.getClass().getResourceAsStream("/config.json");
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));

        // Go through the lines one by one
        StringBuffer stringBuffer = new StringBuffer();
        String str = "";

        while((str = reader.readLine()) != null) {
            stringBuffer.append(str);
        }

        // Konverterer json til variabler ved at typecaste til JsonObject
        json = (JsonObject) parser.parse(stringBuffer.toString());

        // Hiv teksten ud og sæt klassens variable til senere brug
        DATABASE_HOST = json.get("DATABASE_HOST").toString().replace("\"", "");
        DATABASE_PORT = Integer.parseInt(json.get("DATABASE_PORT").toString().replace("\"", ""));
        DATABASE_USERNAME = json.get("DATABASE_USERNAME").toString().replace("\"", "");
        DATABASE_PASSWORD = json.get("DATABASE_PASSWORD").toString().replace("\"", "");
        DATABASE_NAME = json.get("DATABASE_NAME").toString().replace("\"", "");
        ENCRYPTION = json.get("ENCRYPTION").getAsBoolean();
    }

    public static String getDatabaseHost() {
        return DATABASE_HOST;
    }

    public static Integer getDatabasePort() {
        return DATABASE_PORT;
    }

    public static String getDatabaseUsername() {
        return DATABASE_USERNAME;
    }

    public static String getDatabasePassword() {
        return DATABASE_PASSWORD;
    }

    public static String getDatabaseName() {
        return DATABASE_NAME;
    }

    public static Boolean getEncryption() {
        return ENCRYPTION;
    }
}