package me.shadow.placeassist.client.logic;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public final class PlacementHandler {

    private PlacementHandler() {
    }

    public static void triggerPlacement(Minecraft client, InteractionHand hand) {
        if (client.player == null || client.gameMode == null) {
            return;
        }
        LocalPlayer player = client.player;
        ItemStack stack = player.getItemInHand(hand);
        if (stack.isEmpty()) {
            return;
        }
        HitResult hit = client.hitResult;
        if (hit != null && hit.getType() == HitResult.Type.BLOCK) {
            BlockHitResult blockHit = (BlockHitResult) hit;
            client.gameMode.useItemOn(player, hand, blockHit);
        } else if (hit != null && hit.getType() == HitResult.Type.ENTITY) {
            EntityHitResult entityHit = (EntityHitResult) hit;
            client.gameMode.interact(player, entityHit.getEntity(), entityHit, hand);
        } else {
            client.gameMode.useItem(player, hand);
        }
        player.swing(hand);
    }
}
