package com.mjr.spaceAstronomyTweaks;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.ServerList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@Mod(modid = Constants.modID, name = Constants.modName, version = Constants.modVersion)
public class SpaceAstronomyTweaks {

	@Instance(Constants.modID)
	public static SpaceAstronomyTweaks instance;

	@EventHandler
	@SideOnly(Side.CLIENT)
	public void preInit(FMLPreInitializationEvent event) {
		Logger logger = LogManager.getLogger();
		try {
			ServerList list = new ServerList(Minecraft.getMinecraft());
			list.loadServerList();
			int found = 0;
			for (int i = 0; i < list.countServers(); i++) {
				if (list.getServerData(i).serverIP.equalsIgnoreCase("space.mjrlegends.com")) {
					found++;
				}
			}
			if (found == 0) {
				list.addServerData(new ServerData("Official Server", "space.mjrlegends.com"));
				list.saveServerList();
				logger.info("SPACE-ASTRONOMY-TWEAKS: The official server has been added to the server list file");
			} else {
				logger.info("SPACE-ASTRONOMY-TWEAKS: The official server was already found in the server list file");
			}
		} catch (Exception ex) {
			logger.fatal("SPACE-ASTRONOMY-TWEAKS: Unable to add the official server to the server list file, Error: " + ex.getMessage());
		}

	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
	}
}