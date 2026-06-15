package me.shadow.placeassist.logic;

import me.shadow.placeassist.config.PlaceAssistConfig;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.InstrumentItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.SpyglassItem;
import net.minecraft.world.item.TridentItem;
import net.minecraft.world.item.Items;

public final class ItemFilter {

    private ItemFilter() {
    }

    public static boolean shouldAllowFastPlace(ItemStack stack, PlaceAssistConfig config) {
        if (stack.isEmpty()) {
            return false;
        }
        if (!config.smartFiltering) {
            return true;
        }
        if (config.excludeFood && isFood(stack)) {
            return false;
        }
        if (config.excludeShield && stack.getItem() instanceof ShieldItem) {
            return false;
        }
        if (config.excludeBow && stack.getItem() instanceof BowItem) {
            return false;
        }
        if (config.excludeCrossbow && stack.getItem() instanceof CrossbowItem) {
            return false;
        }
        if (config.excludeTrident && stack.getItem() instanceof TridentItem) {
            return false;
        }
        if (config.excludeUtilityItems && isUtilityItem(stack)) {
            return false;
        }
        return true;
    }

    private static boolean isFood(ItemStack stack) {
        return stack.has(DataComponents.FOOD);
    }

    private static boolean isUtilityItem(ItemStack stack) {
        return stack.getItem() instanceof SpyglassItem
                || stack.getItem() instanceof InstrumentItem
                || stack.is(Items.FIREWORK_ROCKET);
    }
}
