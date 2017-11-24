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
	public static int quarryDim;
	public static boolean removeToolEffectiveness;
	public static boolean generateBOPEndOres;
	public static boolean removeRF_CC_Compatibility;

	public static void load() {
		Configuration config = new Configuration(new File("config/SpaceAstronomyTweaks.cfg"));
		config.load();

		Config.autoServerList = config.get(Config.mainoptions, "Enable auto add servers to the list", true, "Setting this to false will stop servers being added and removed from your server list!. DONT TOUCH! OR IT WILL BREAK THE PACK, TO BE CHANGED BY THE MOD PACK DEV ONLY!").getBoolean(true);
		Config.autoserverlistLink = config.get(Config.mainoptions, "Servers to the list url", "https://pastebin.com/raw/Y3FtGkf9", "Link the Auto server list management will use!. DONT TOUCH! OR IT WILL BREAK THE PACK, TO BE CHANGED BY THE MOD PACK DEV ONLY!").getString();
		Config.clearSimpleAchievements = config.get(Config.mainoptions, "Clear Simple Achievements on Every World", false, "DONT TOUCH! OR IT WILL BREAK THE PACK, TO BE CHANGED BY THE MOD PACK DEV ONLY!").getBoolean(false);
		Config.clearSimpleAchievementsWorlds = config.get("Worlds", Config.worlds, Config.clearSimpleAchievementsWorlds, "DONT TOUCH! OR IT WILL BREAK THE PACK, TO BE CHANGED BY THE MOD PACK DEV ONLY!").getStringList();	
		Config.quarryDim = config.get(Config.mainoptions, "ExtraUtils2_Quarry_Dim", -9999, "Set this to the same as the ExtraUtils2_Quarry_Dim in the ExtraUtil 2 config. DONT TOUCH! OR IT WILL BREAK THE PACK, TO BE CHANGED BY THE MOD PACK DEV ONLY!").getInt(-9999);
		Config.removeToolEffectiveness = config.get(Config.mainoptions, "Remove Effectiveness from Vanilla Tools", true, "DONT TOUCH! OR IT WILL BREAK THE PACK, TO BE CHANGED BY THE MOD PACK DEV ONLY!").getBoolean(true);
		Config.generateBOPEndOres = config.get(Config.mainoptions, "Enable fix for generating Biomes o Penty End Ores", true, "DONT TOUCH! OR IT WILL BREAK THE PACK, TO BE CHANGED BY THE MOD PACK DEV ONLY!").getBoolean(true);
		Config.removeRF_CC_Compatibility = config.get(Config.mainoptions, "Disable RefinedStorage External Storage with ColossalChests Interfaces Compatibility", true, "DONT TOUCH! OR IT WILL BREAK THE PACK, TO BE CHANGED BY THE MOD PACK DEV ONLY!").getBoolean(true);
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
