package me.shadow.placeassist.client;

import me.shadow.placeassist.PlaceAssist;
import me.shadow.placeassist.client.logic.FastPlaceManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

public class PlaceAssistClient implements ClientModInitializer {
    private static FastPlaceManager fastPlaceManager;

    @Override
    public void onInitializeClient() {
        fastPlaceManager = new FastPlaceManager();
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player != null) {
                fastPlaceManager.onClientTick(client);
            }
        });
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (PlaceAssist.getConfig() != null) {
                PlaceAssist.getConfig().save();
            }
        }));
        PlaceAssist.LOGGER.info("PlaceAssist client initialized");
    }
}
