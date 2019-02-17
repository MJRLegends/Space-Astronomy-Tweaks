package com.mjr.spaceAstronomyTweaks;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.ServerList;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("spaceastronomytweaks")
public class SpaceAstronomyTweaks {
    private static final Logger logger = LogManager.getLogger();

	public SpaceAstronomyTweaks() {
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::initClient);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::initCommon);
		ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.clientSpec);
		ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, Config.serverSpec);
	}

	private void initClient(final FMLClientSetupEvent event) {
		// Server List Adding/Removing
		if (Config.CLIENT.autoServerList.get())
			serverlistChecker();
	}

	private void initCommon(final FMLCommonSetupEvent event) {
		MinecraftForge.EVENT_BUS.register(new MainEventHandler());
		// Option for Recommend Options file
		/*if (Config.CLIENT.defaultOptionsFile)
			optionsFileChecker(event);*/
	}

/*	private void optionsFileChecker(FMLClientSetupEvent event) {
		try {
			String path = "" + event.getModConfigurationDirectory();
			path = path.substring(0, path.length() - 6);

			logger.warn(Constants.modID + "Options File Dialog!");
			if (new File(path + "options.txt").exists() && new File(path + "/config/" + "options_Pack_Default.txt").exists()) {
				JFrame frame = new JFrame();
				frame.setSize(600, 600);
				frame.setFocusable(true);
				frame.setAlwaysOnTop(true);

				for (int i = 0; i < 3; i++)
					logger.warn(Constants.modID + "There is a window open at the moment, that needs a response! Please see the Dialog box for details! THIS IS NOT A BUG WITH YOUR PACK STARTING!!");

				int selectedOption = JOptionPane.showConfirmDialog(frame, "Would you like to use the Recommend Options file? \n (This is replace your Controls and give you default Video/Sound Settings!) \n\n Or press No to keep your current one!",
						"Message", JOptionPane.YES_NO_OPTION);

				if (selectedOption == 0) {
					logger.warn(Constants.modID + "Option from Dialog was YES");
					new File(path + "options.txt").renameTo(new File(path + "options.txt_OLD"));
					logger.warn(Constants.modID + "Renamed old/current Options file to 'options.txt_OLD' Its sorted in: " + path);
					new File(path + "/config/" + "options_Pack_Default.txt").renameTo(new File(path + "options.txt"));
					logger.warn(Constants.modID + "Recommend Options file copied!");
				} else {
					logger.warn(Constants.modID + "Option from Dialog was NO");
					new File(path + "/config/" + "options_Pack_Default.txt").delete();
					logger.warn(Constants.modID + "Recommend Options file removed!");
				}
			}
		} catch (Exception ex) {
			logger.fatal(Constants.modID + "Error: " + ex.getMessage());
		}
	}
*/
	private void serverlistChecker() {
		try {
			String url = Config.CLIENT.autoserverlistLink.get();
			logger.warn(Constants.modID + "Getting remote server list, from: " + url);
			String result = HTTPConnect.GetResponsefrom(url);
			System.out.println(result);
			String[] listFromURL = result.split(";");
			logger.warn(Constants.modID + "Remote list contains " + listFromURL.length + " servers");
			ServerList list = new ServerList(Minecraft.getInstance());
			list.loadServerList();
			for (int i = 0; i < listFromURL.length; i++) {
				if (i < 0) {
					listFromURL[i] = listFromURL[i].substring(4);
				}
				String name = listFromURL[i].substring(0, listFromURL[i].indexOf(','));
				String serverIP = listFromURL[i].substring(listFromURL[i].indexOf(name) + name.length() + 1);
				serverIP = serverIP.substring(0, serverIP.indexOf(','));
				String addToList = listFromURL[i].substring(listFromURL[i].indexOf(serverIP) + serverIP.length() + 1);
				int found = 0;
				for (int j = 0; j < list.countServers(); j++) {
					if (list.getServerData(j).serverIP.equalsIgnoreCase(serverIP)) {
						found++;
					}
				}
				if (found == 0 && addToList.equalsIgnoreCase("true")) {
					list.addServerData(new ServerData(name, serverIP, false));
					logger.warn(Constants.modID + "The server " + name + " has been added to the server list file remotely by the mod pack developer");
				} else if (found > 0 && addToList.equalsIgnoreCase("false")) {
					for (int j = 0; j < list.countServers(); j++) {
						if (list.getServerData(j).serverIP.equalsIgnoreCase(serverIP)) {
							list.removeServerData(j);
						}
					}
					logger.warn(Constants.modID + "The server " + name + " has been removed to the server list file remotely by the mod pack developer");
				}
			}
			list.saveServerList();
		} catch (Exception ex) {
			logger.fatal(Constants.modID + "Error: " + ex.getMessage());
		}
	}

	// public void onFingerprintViolation(FMLFingerprintViolationEvent event) {
	// logger.fatal(Constants.modID + " Invalid fingerprint detected! The file " + event.getSource().getName() + " may have been tampered with. This version will NOT be supported!");
	// }
}