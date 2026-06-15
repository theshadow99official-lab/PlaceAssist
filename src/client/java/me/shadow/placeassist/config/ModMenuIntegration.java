package me.shadow.placeassist.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shadow.placeassist.PlaceAssist;
import me.shadow.placeassist.util.Constants;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;

public class ModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> {
            try {
                PlaceAssistConfig config = PlaceAssist.getConfig();
                if (config == null) {
                    config = PlaceAssistConfig.load();
                }
                Screen screen = tryBuildClothConfigScreen(parent, config);
                if (screen != null) {
                    return screen;
                }
            } catch (Throwable t) {
                PlaceAssist.LOGGER.warn("Failed to build Cloth Config screen", t);
            }
            return null;
        };
    }

    private Screen tryBuildClothConfigScreen(Screen parent, PlaceAssistConfig config) {
        try {
            ConfigBuilder builder = ConfigBuilder.create()
                    .setParentScreen(parent)
                    .setTitle(Component.translatable("placeassist.config.title"))
                    .setSavingRunnable(config::save);
            var general = builder.getOrCreateCategory(Component.translatable("placeassist.config.category.general"));
            general.addEntry(builder.entryBuilder()
                    .startBooleanToggle(Component.translatable("placeassist.config.enabled"), config.enabled)
                    .setDefaultValue(true)
                    .setSaveConsumer(value -> config.enabled = value)
                    .build());
            general.addEntry(builder.entryBuilder()
                    .startBooleanToggle(Component.translatable("placeassist.config.enabledOnStartup"), config.enabledOnStartup)
                    .setDefaultValue(false)
                    .setSaveConsumer(value -> config.enabledOnStartup = value)
                    .build());
            var fastPlace = builder.getOrCreateCategory(Component.translatable("placeassist.config.category.fastplace"));
            fastPlace.addEntry(builder.entryBuilder()
                    .startIntSlider(Component.translatable("placeassist.config.placementRate"), config.placementRate, 1, 20)
                    .setDefaultValue(20)
                    .setSaveConsumer(value -> config.placementRate = value)
                    .build());
            var filters = builder.getOrCreateCategory(Component.translatable("placeassist.config.category.filters"));
            filters.addEntry(builder.entryBuilder()
                    .startBooleanToggle(Component.translatable("placeassist.config.smartFiltering"), config.smartFiltering)
                    .setDefaultValue(true)
                    .setSaveConsumer(value -> config.smartFiltering = value)
                    .build());
            filters.addEntry(builder.entryBuilder()
                    .startBooleanToggle(Component.translatable("placeassist.config.excludeFood"), config.excludeFood)
                    .setDefaultValue(true)
                    .setSaveConsumer(value -> config.excludeFood = value)
                    .build());
            filters.addEntry(builder.entryBuilder()
                    .startBooleanToggle(Component.translatable("placeassist.config.excludeShield"), config.excludeShield)
                    .setDefaultValue(true)
                    .setSaveConsumer(value -> config.excludeShield = value)
                    .build());
            filters.addEntry(builder.entryBuilder()
                    .startBooleanToggle(Component.translatable("placeassist.config.excludeBow"), config.excludeBow)
                    .setDefaultValue(true)
                    .setSaveConsumer(value -> config.excludeBow = value)
                    .build());
            filters.addEntry(builder.entryBuilder()
                    .startBooleanToggle(Component.translatable("placeassist.config.excludeCrossbow"), config.excludeCrossbow)
                    .setDefaultValue(true)
                    .setSaveConsumer(value -> config.excludeCrossbow = value)
                    .build());
            filters.addEntry(builder.entryBuilder()
                    .startBooleanToggle(Component.translatable("placeassist.config.excludeTrident"), config.excludeTrident)
                    .setDefaultValue(true)
                    .setSaveConsumer(value -> config.excludeTrident = value)
                    .build());
            filters.addEntry(builder.entryBuilder()
                    .startBooleanToggle(Component.translatable("placeassist.config.excludeUtilityItems"), config.excludeUtilityItems)
                    .setDefaultValue(true)
                    .setSaveConsumer(value -> config.excludeUtilityItems = value)
                    .build());
            return builder.build();
        } catch (Throwable t) {
            PlaceAssist.LOGGER.warn("Could not build Cloth Config screen", t);
            return null;
        }
    }
}
