package com.mjr.spaceAstronomyTweaks;

import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
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
}
