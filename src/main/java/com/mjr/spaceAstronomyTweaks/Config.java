package com.mjr.spaceAstronomyTweaks;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public class Config {
	// Sections/Groups
	private static String mainoptions = "main options";

	// Config options
	public static boolean autoServerList;
	public static boolean defaultOptionsFile = false;

	public static String autoserverlistLink;

	public static void load() {
		Configuration config = new Configuration(new File("config/SpaceAstronomyTweaks.cfg"));
		config.load();
		
		autoServerList = config.get(mainoptions, "Enable auto add servers to the list", true, "Setting this to false will stop servers being added and removed from your server list!").getBoolean(true);
		autoserverlistLink = config.get(mainoptions, "Servers to the list url", "https://pastebin.com/raw/Y3FtGkf9", "Link the Auto server list management will use!").getString();

		//defaultOptionsFile = config.get(mainoptions, "Use default bosses for all planets", false, "Will disable all custom bosses and will replace them with Creeper Bosses!").getBoolean(false);

		config.save();
	}
}
