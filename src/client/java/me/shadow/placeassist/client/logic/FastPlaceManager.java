package me.shadow.placeassist.client.logic;

import me.shadow.placeassist.PlaceAssist;
import me.shadow.placeassist.config.PlaceAssistConfig;
import me.shadow.placeassist.logic.ItemFilter;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;

public class FastPlaceManager {
    private int tickCounter = 0;

    public void onClientTick(Minecraft client) {
        PlaceAssistConfig config = PlaceAssist.getConfig();
        if (config == null || !config.enabled) {
            return;
        }
        if (client.player == null || client.gameMode == null) {
            return;
        }
        if (!client.options.keyUse.isDown()) {
            tickCounter = 0;
            return;
        }
        if (client.gameMode.isDestroying()) {
            return;
        }
        if (client.isPaused()) {
            return;
        }
        InteractionHand hand = InteractionHand.MAIN_HAND;
        if (!ItemFilter.shouldAllowFastPlace(client.player.getItemInHand(hand), config)) {
            return;
        }
        int interval = Math.max(1, Math.round(20.0f / config.placementRate));
        tickCounter++;
        if (tickCounter % interval != 0) {
            return;
        }
        PlacementHandler.triggerPlacement(client, hand);
    }
}
