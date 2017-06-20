package com.mjr.spaceAstronomyTweaks;

import java.io.File;
import java.util.Arrays;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class Config {
	// Sections/Groups
	private static String mainoptions = "main options";
	private static String worlds = "worlds";

	// Config options
	public static boolean autoServerList;
	public static boolean defaultOptionsFile = false;
	public static boolean clearSimpleAchievements;
	public static String autoserverlistLink;
	public static String[] clearSimpleAchievementsWorlds = {};

	public static void load() {
		Configuration config = new Configuration(new File("config/SpaceAstronomyTweaks.cfg"));
		config.load();

		Config.autoServerList = config.get(Config.mainoptions, "Enable auto add servers to the list", true, "Setting this to false will stop servers being added and removed from your server list!").getBoolean(true);
		Config.autoserverlistLink = config.get(Config.mainoptions, "Servers to the list url", "https://pastebin.com/raw/Y3FtGkf9", "Link the Auto server list management will use!").getString();
		Config.clearSimpleAchievements = config.get(Config.mainoptions, "Clear Simple Achievements on Every World", false, "DONT TOUCH! OR IT WILL BREAK THE PACK").getBoolean(false);
		Config.clearSimpleAchievementsWorlds = config.get("Worlds", Config.worlds, Config.clearSimpleAchievementsWorlds, "").getStringList();	
		// defaultOptionsFile = config.get(mainoptions, "Use default bosses for all planets", false, "Will disable all custom bosses and will replace them with Creeper Bosses!").getBoolean(false);

		config.save();
	}

	public static void addWorldToList(String name) {
		Configuration config = new Configuration(new File("config/SpaceAstronomyTweaks.cfg"));
		config.load();

		String[] oldIDs = Config.clearSimpleAchievementsWorlds;
		clearSimpleAchievementsWorlds = new String[Config.clearSimpleAchievementsWorlds.length + 1];
		System.arraycopy(oldIDs, 0, Config.clearSimpleAchievementsWorlds, 0, oldIDs.length);

		Config.clearSimpleAchievementsWorlds[Config.clearSimpleAchievementsWorlds.length - 1] = name;
		String[] values = new String[Config.clearSimpleAchievementsWorlds.length];
		Arrays.sort(Config.clearSimpleAchievementsWorlds);

		for (int i = 0; i < values.length; i++) {
			values[i] = String.valueOf(Config.clearSimpleAchievementsWorlds[i]);
		}

		Property temp = config.get("Worlds", Config.worlds, new String[] {}, "").setValues(values);
		Config.clearSimpleAchievementsWorlds = temp.getStringList();
		config.save();
	}
}
