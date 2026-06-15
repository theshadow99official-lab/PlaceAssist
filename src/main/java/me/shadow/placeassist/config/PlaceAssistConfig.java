package me.shadow.placeassist.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.shadow.placeassist.util.Constants;
import me.shadow.placeassist.PlaceAssist;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class PlaceAssistConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public boolean enabled = true;
    public boolean enabledOnStartup = false;
    public int placementRate = 20;
    public boolean smartFiltering = true;
    public boolean excludeFood = true;
    public boolean excludeShield = true;
    public boolean excludeBow = true;
    public boolean excludeCrossbow = true;
    public boolean excludeTrident = true;
    public boolean excludeUtilityItems = true;

    public static PlaceAssistConfig load() {
        File file = new File(Constants.CONFIG_PATH);
        if (!file.exists()) {
            PlaceAssist.LOGGER.info("No config file found, creating default config");
            PlaceAssistConfig config = new PlaceAssistConfig();
            config.save();
            return config;
        }
        try (FileReader reader = new FileReader(file)) {
            PlaceAssistConfig config = GSON.fromJson(reader, PlaceAssistConfig.class);
            if (config == null) {
                PlaceAssist.LOGGER.warn("Config file is empty, using defaults");
                return new PlaceAssistConfig();
            }
            PlaceAssist.LOGGER.info("Config loaded successfully");
            return config;
        } catch (IOException e) {
            PlaceAssist.LOGGER.error("Failed to load config, using defaults", e);
            return new PlaceAssistConfig();
        }
    }

    public void save() {
        File file = new File(Constants.CONFIG_PATH);
        File dir = file.getParentFile();
        if (dir != null && !dir.exists()) {
            dir.mkdirs();
        }
        try (FileWriter writer = new FileWriter(file)) {
            GSON.toJson(this, writer);
            PlaceAssist.LOGGER.info("Config saved successfully");
        } catch (IOException e) {
            PlaceAssist.LOGGER.error("Failed to save config", e);
        }
    }
}
