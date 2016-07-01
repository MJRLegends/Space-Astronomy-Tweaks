package com.mjr.spaceAstronomyTweaks;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.ServerList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
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
			logger.warn("SPACE-ASTRONOMY-TWEAKS: Getting remote server list");
			String result = HTTPConnect.GetResponsefrom("http://pastebin.com/raw/Y3FtGkf9");
			String[] listFromURL = result.split(";");
			logger.warn("SPACE-ASTRONOMY-TWEAKS: Remote list contains " + listFromURL.length + " servers");
			for (int i = 0; i < listFromURL.length; i++) {
				if (i < 0) {
					listFromURL[i] = listFromURL[i].substring(4);
				}
				String name = listFromURL[i].substring(0, listFromURL[i].indexOf(','));
				String serverIP = listFromURL[i].substring(listFromURL[i].indexOf(name) + name.length() + 1);
				serverIP = serverIP.substring(0, serverIP.indexOf(','));
				String addToList = listFromURL[i].substring(listFromURL[i].indexOf(serverIP) + serverIP.length() + 1);

				ServerList list = new ServerList(Minecraft.getMinecraft());
				list.loadServerList();
				int found = 0;
				for (int j = 0; j < list.countServers(); j++) {
					if (list.getServerData(j).serverIP.equalsIgnoreCase(serverIP)) {
						found++;
					}
				}
				if (found == 0 && addToList.equalsIgnoreCase("true")) {
					list.addServerData(new ServerData(name, serverIP));
					list.saveServerList();
					logger.warn("SPACE-ASTRONOMY-TWEAKS: The server " + name + " has been added to the server list file remotely by the mod pack developer");
				} else if (found > 0 && addToList.equalsIgnoreCase("false")) {
					for (int j = 0; j < list.countServers(); j++) {
						if (list.getServerData(j).serverIP.equalsIgnoreCase(serverIP)) {
							list.removeServerData(j);
						}
					}
					logger.warn("SPACE-ASTRONOMY-TWEAKS: The server " + name + " has been removed to the server list file remotely by the mod pack developer");
					list.saveServerList();
				}
			}
		} catch (Exception ex) {
			logger.fatal("SPACE-ASTRONOMY-TWEAKS: Error: " + ex.getMessage());
		}
	}
}