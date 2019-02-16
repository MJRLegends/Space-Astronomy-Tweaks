package com.mjr.spaceAstronomyTweaks;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.UseHoeEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class MainEventHandler {

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onDamageTaken(LivingHurtEvent event) {
		Entity entity = event.getSource().getTrueSource();
		if (entity != null) {
			if (!(entity instanceof FakePlayer) && (entity instanceof EntityPlayer)) {
				if (Config.SERVER.removeToolEffectiveness.get() == false)
					return;
				EntityPlayer player = (EntityPlayer) entity;
				ItemStack stack = player.getHeldItemMainhand();
				if (stack == null)
					return;
				if (stack.getItem() == Items.WOODEN_SWORD || stack.getItem() == Items.STONE_SWORD || stack.getItem() == Items.IRON_SWORD || stack.getItem() == Items.GOLDEN_SWORD || stack.getItem() == Items.DIAMOND_SWORD) {
					event.setCanceled(true);
				}
				stack = player.getHeldItemOffhand();
				if (stack == null)
					return;
				if (stack.getItem() == Items.WOODEN_SWORD || stack.getItem() == Items.STONE_SWORD || stack.getItem() == Items.IRON_SWORD || stack.getItem() == Items.GOLDEN_SWORD || stack.getItem() == Items.DIAMOND_SWORD) {
					event.setCanceled(true);
				}
			}
		}
	}

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onItemUse(PlayerEvent.BreakSpeed event) {
		EntityPlayer player = event.getEntityPlayer();
		if (player != null) {
			if (Config.SERVER.removeToolEffectiveness.get() == false)
				return;
			ItemStack stack = player.getHeldItemMainhand();
			if (stack == null)
				return;
			if (stack.getItem() == Items.WOODEN_AXE || stack.getItem() == Items.STONE_AXE || stack.getItem() == Items.IRON_AXE || stack.getItem() == Items.GOLDEN_AXE || stack.getItem() == Items.DIAMOND_AXE || stack.getItem() == Items.WOODEN_PICKAXE
					|| stack.getItem() == Items.STONE_PICKAXE || stack.getItem() == Items.IRON_PICKAXE || stack.getItem() == Items.GOLDEN_PICKAXE || stack.getItem() == Items.DIAMOND_PICKAXE || stack.getItem() == Items.WOODEN_SHOVEL
					|| stack.getItem() == Items.STONE_SHOVEL || stack.getItem() == Items.IRON_SHOVEL || stack.getItem() == Items.GOLDEN_SHOVEL || stack.getItem() == Items.DIAMOND_SHOVEL) {
				event.setCanceled(true);
			}
			stack = player.getHeldItemOffhand();
			if (stack == null)
				return;
			if (stack.getItem() == Items.WOODEN_AXE || stack.getItem() == Items.STONE_AXE || stack.getItem() == Items.IRON_AXE || stack.getItem() == Items.GOLDEN_AXE || stack.getItem() == Items.DIAMOND_AXE || stack.getItem() == Items.WOODEN_PICKAXE
					|| stack.getItem() == Items.STONE_PICKAXE || stack.getItem() == Items.IRON_PICKAXE || stack.getItem() == Items.GOLDEN_PICKAXE || stack.getItem() == Items.DIAMOND_PICKAXE || stack.getItem() == Items.WOODEN_SHOVEL
					|| stack.getItem() == Items.STONE_SHOVEL || stack.getItem() == Items.IRON_SHOVEL || stack.getItem() == Items.GOLDEN_SHOVEL || stack.getItem() == Items.DIAMOND_SHOVEL) {
				event.setCanceled(true);
			}
		}
	}

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onHoeing(UseHoeEvent event) {
		EntityPlayer player = event.getEntityPlayer();
		if (player != null) {
			if (Config.SERVER.removeToolEffectiveness.get() == false)
				return;
			ItemStack stack = player.getHeldItemMainhand();
			if (stack == null)
				return;
			if (stack.getItem() == Items.WOODEN_HOE || stack.getItem() == Items.STONE_HOE || stack.getItem() == Items.IRON_HOE || stack.getItem() == Items.GOLDEN_HOE || stack.getItem() == Items.DIAMOND_HOE) {
				event.setCanceled(true);
			}
			stack = player.getHeldItemOffhand();
			if (stack == null)
				return;
			if (stack.getItem() == Items.WOODEN_HOE || stack.getItem() == Items.STONE_HOE || stack.getItem() == Items.IRON_HOE || stack.getItem() == Items.GOLDEN_HOE || stack.getItem() == Items.DIAMOND_HOE) {
				event.setCanceled(true);
			}
		}
	}
}
