package com.mjr.spaceAstronomyTweaks;

import java.io.File;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

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
	public static Logger logger = LogManager.getLogger();

	@EventHandler
	@SideOnly(Side.CLIENT)
	public void preInit(FMLPreInitializationEvent event) {
		Config.load();
		// Option for Recommend Options file
		if(Config.defaultOptionsFile)
			optionsFileChecker(event);

		// Server List Adding/Removing
		if(Config.autoServerList)
			serverlistChecker();
	}

	private void optionsFileChecker(FMLPreInitializationEvent event) {
		try {
			String path = "" + event.getModConfigurationDirectory();
			path = path.substring(0, path.length() - 6);

			logger.warn("SPACE-ASTRONOMY-TWEAKS: Options File Dialog!");
			if (new File(path + "options.txt").exists() && new File(path + "/config/" + "options_Pack_Default.txt").exists()) {
				JFrame frame = new JFrame();
				frame.setSize(600, 600);
				frame.setFocusable(true);
				frame.setAlwaysOnTop(true);

				for (int i = 0; i < 3; i++)
					logger.warn("SPACE-ASTRONOMY-TWEAKS: There is a window open at the moment, that needs a response! Please see the Dialog box for details! THIS IS NOT A BUG WITH YOUR PACK STARTING!!");

				int selectedOption = JOptionPane
						.showConfirmDialog(
								frame,
								"Would you like to use the Recommend Options file? \n (This is replace your Controls and give you default Video/Sound Settings!) \n\n Or press No to keep your current one!",
								"Message", JOptionPane.YES_NO_OPTION);

				if (selectedOption == 0) {
					logger.warn("SPACE-ASTRONOMY-TWEAKS: Option from Dialog was YES");
					new File(path + "options.txt").renameTo(new File(path
							+ "options.txt_OLD"));
					logger.warn("SPACE-ASTRONOMY-TWEAKS: Renamed old/current Options file to 'options.txt_OLD' Its sorted in: " + path);
					new File(path + "/config/" + "options_Pack_Default.txt")
							.renameTo(new File(path + "options.txt"));
					logger.warn("SPACE-ASTRONOMY-TWEAKS: Recommend Options file copied!");
				}
				else{
					logger.warn("SPACE-ASTRONOMY-TWEAKS: Option from Dialog was NO");
					new File(path + "/config/" + "options_Pack_Default.txt").delete();
					logger.warn("SPACE-ASTRONOMY-TWEAKS: Recommend Options file removed!");
				}
			}
		} catch (Exception ex) {
			logger.fatal("SPACE-ASTRONOMY-TWEAKS: Error: " + ex.getMessage());
		}
	}

	private void serverlistChecker() {
		try {
			logger.warn("SPACE-ASTRONOMY-TWEAKS: Getting remote server list, from: " + Config.autoserverlistLink);
			String result = HTTPConnect
					.GetResponsefrom(Config.autoserverlistLink);
			System.out.println(result);
			String[] listFromURL = result.split(";");
			logger.warn("SPACE-ASTRONOMY-TWEAKS: Remote list contains "
					+ listFromURL.length + " servers");
			for (int i = 0; i < listFromURL.length; i++) {
				if (i < 0) {
					listFromURL[i] = listFromURL[i].substring(4);
				}
				String name = listFromURL[i].substring(0,
						listFromURL[i].indexOf(','));
				String serverIP = listFromURL[i].substring(listFromURL[i]
						.indexOf(name) + name.length() + 1);
				serverIP = serverIP.substring(0, serverIP.indexOf(','));
				String addToList = listFromURL[i].substring(listFromURL[i]
						.indexOf(serverIP) + serverIP.length() + 1);

				ServerList list = new ServerList(Minecraft.getMinecraft());
				list.loadServerList();
				int found = 0;
				for (int j = 0; j < list.countServers(); j++) {
					if (list.getServerData(j).serverIP
							.equalsIgnoreCase(serverIP)) {
						found++;
					}
				}
				if (found == 0 && addToList.equalsIgnoreCase("true")) {
					list.addServerData(new ServerData(name, serverIP));
					list.saveServerList();
					logger.warn("SPACE-ASTRONOMY-TWEAKS: The server "
							+ name
							+ " has been added to the server list file remotely by the mod pack developer");
				} else if (found > 0 && addToList.equalsIgnoreCase("false")) {
					for (int j = 0; j < list.countServers(); j++) {
						if (list.getServerData(j).serverIP
								.equalsIgnoreCase(serverIP)) {
							list.removeServerData(j);
						}
					}
					logger.warn("SPACE-ASTRONOMY-TWEAKS: The server "
							+ name
							+ " has been removed to the server list file remotely by the mod pack developer");
					list.saveServerList();
				}
			}
		} catch (Exception ex) {
			logger.fatal("SPACE-ASTRONOMY-TWEAKS: Error: " + ex.getMessage());
		}
	}
}