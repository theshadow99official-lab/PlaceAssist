package me.shadow.placeassist;

import me.shadow.placeassist.config.PlaceAssistConfig;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlaceAssist implements ModInitializer {
    public static final String MOD_ID = "placeassist";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    private static PlaceAssistConfig config;

    @Override
    public void onInitialize() {
        config = PlaceAssistConfig.load();
        LOGGER.info("PlaceAssist initialized");
    }

    public static PlaceAssistConfig getConfig() {
        return config;
    }

    public static void setConfig(PlaceAssistConfig newConfig) {
        config = newConfig;
    }
}
