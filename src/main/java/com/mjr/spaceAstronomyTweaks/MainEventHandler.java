package com.mjr.spaceAstronomyTweaks;

import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.UseHoeEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;

public class MainEventHandler {
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onPlayerJoin(PlayerLoggedInEvent event) {
		if (event.player != null) {
			if (Config.clearSimpleAchievements) {
				final EntityPlayer player = event.player;
				boolean run = true;
				final MinecraftServer server = event.player.world.getMinecraftServer();
				final String worldName = server.getFolderName();
				SpaceAstronomyTweaks.logger.warn("SPACE-ASTRONOMY-TWEAKS: Starting Clearing of Simple Achievements on world/server: " + worldName);
				for (int i = 0; i < Config.clearSimpleAchievementsWorlds.length; i++) {
					if (worldName.equalsIgnoreCase(Config.clearSimpleAchievementsWorlds[i])) {
						run = false;
						break;
					}
				}
				if (run) {
					server.addScheduledTask(new Runnable() {
						@Override
						public void run() {
							try {
								Thread.sleep(5000);
							} catch (Exception e) {
								SpaceAstronomyTweaks.logger.warn("SPACE-ASTRONOMY-TWEAKS: " + e.getMessage());
							}
							int number = server.getCommandManager().executeCommand(new AdminExecute(player), "flushAchievements");
							player.sendMessage(new TextComponentString(TextFormatting.RED + "The Achievement Book has been reset! It has happened because the mod pack dev has updated the Achievement Book in the mod pack update!"));
							if(worldName.equalsIgnoreCase("world"))
								server.getCommandManager().executeCommand(new AdminExecute(player), "kick " + player.getName() + " Please reconnect");
							SpaceAstronomyTweaks.logger.warn("SPACE-ASTRONOMY-TWEAKS: " + number);
						}
					});
					SpaceAstronomyTweaks.logger.warn("SPACE-ASTRONOMY-TWEAKS: Simple Achievements has been reset on the following world/server: " + worldName
							+ " this was due to mod pack changes were made and the developer has enabled this option! (NOTE: THE RESET WILL ONLY HAPPEN ONCE PER WORLD, PER UPDATE!)");
					Config.addWorldToList(worldName);
				}
				else{
					if(worldName.equalsIgnoreCase("world"))
						player.sendMessage(new TextComponentString(TextFormatting.RED + "The Achievement Book has been reset! It has happened because the mod pack dev has updated the Achievement Book in the mod pack update!"));
				}
			}
		}
	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onEntitySpawn(LivingSpawnEvent event) {
		if (event.getEntity().dimension == Config.quarryDim && event.getEntity() instanceof EntitySilverfish) {
			SpaceAstronomyTweaks.logger.warn("SPACE-ASTRONOMY-TWEAKS: Removed EntitySilverfish from dimension " + Config.quarryDim);
			event.getEntity().setDead();
		}
	}

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onDamageTaken(LivingHurtEvent event) {
		Entity entity = event.getSource().getEntity();
		if (!(entity instanceof FakePlayer) && (entity instanceof EntityPlayer)) {
			EntityPlayer player = (EntityPlayer) entity;

			ItemStack stack = player.getHeldItemMainhand();
			if (stack.getItem() instanceof ItemSword) {
				event.setCanceled(true);
			}
			
			stack = player.getHeldItemOffhand();
			if (stack.getItem() instanceof ItemSword) {
				event.setCanceled(true);
			}
		}
	}

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onItemUse(PlayerEvent.BreakSpeed event) {
		EntityPlayer player = event.getEntityPlayer();
		if (player != null) {
			ItemStack stack = player.getHeldItemMainhand();
			if (stack.getItem() instanceof ItemTool) {
				event.setCanceled(true);
			}
			stack = player.getHeldItemOffhand();
			if (stack.getItem() instanceof ItemTool) {
				event.setCanceled(true);
			}
		}
	}

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onHoeing(UseHoeEvent event) {
		EntityPlayer player = event.getEntityPlayer();
		if (player != null) {
			ItemStack stack = player.getHeldItemMainhand();
			if (stack.getItem() instanceof ItemHoe) {
				event.setCanceled(true);
			}
			stack = player.getHeldItemOffhand();
			if (stack.getItem() instanceof ItemHoe) {
				event.setCanceled(true);
			}
		}
	}

}
