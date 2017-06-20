package com.mjr.spaceAstronomyTweaks;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;

public class MainEventHandler {
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onPlayerJoin(PlayerLoggedInEvent event) {
		if (event.player instanceof EntityPlayer) {
			if (Config.clearSimpleAchievements) {
				SpaceAstronomyTweaks.logger.warn("SPACE-ASTRONOMY-TWEAKS: Starting Clearing of Simple Achievements on world");
				final EntityPlayer player = event.player;
				boolean run = true;
				final MinecraftServer server = event.player.worldObj.getMinecraftServer();
				String worldName = server.getFolderName();
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
							SpaceAstronomyTweaks.logger.warn("SPACE-ASTRONOMY-TWEAKS: " + number);
						}
					});
					SpaceAstronomyTweaks.logger.warn("SPACE-ASTRONOMY-TWEAKS: Simple Achievements has been reset on the following world: " + worldName
							+ " this was due to mod pack changes were made and the developer has enabled this option! (NOTE: THE RESET WILL ONLY HAPPEN ONCE PER WORLD, PER UPDATE!)");
					Config.addWorldToList(worldName);
				}
			}
		}
	}
}
