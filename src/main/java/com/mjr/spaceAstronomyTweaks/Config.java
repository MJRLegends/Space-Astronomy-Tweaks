package com.mjr.spaceAstronomyTweaks;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;

public class Config {

	public static class Server {
		public final BooleanValue removeToolEffectiveness;

		Server(ForgeConfigSpec.Builder builder) {
			builder.comment("Server configuration settings").push("server");
			removeToolEffectiveness = builder.comment("Remove Effectiveness from Vanilla Tools").translation("config.removeToolEffectiveness.enable").define("removeToolEffectiveness", true);

			builder.pop();
		}
	}

	public static class Client {
		public final BooleanValue autoServerList;
		public final boolean defaultOptionsFile = false;
		public final ConfigValue<String> autoserverlistLink;

		Client(ForgeConfigSpec.Builder builder) {
			builder.comment("Client configuration settings").push("client");
			autoServerList = builder.comment("Enable auto add servers to the list").translation("config.autoAdd.enable").define("enableAutoServerList", true);
			autoserverlistLink = builder.comment("Url for looking up servers to add/remove from the server list").translation("config.autoAdd.link").define("autoServerListLink", "https://pastebin.com/raw/Y3FtGkf9");

			// defaultOptionsFile = config.get(mainoptions, "Use default bosses for all planets", false, "Will disable all custom bosses and will replace them with Creeper Bosses!").getBoolean(false);

			builder.pop();
		}
	}

    static final ForgeConfigSpec clientSpec;
    public static final Client CLIENT;
    static {
        final Pair<Client, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Client::new);
        clientSpec = specPair.getRight();
        CLIENT = specPair.getLeft();
    }


    static final ForgeConfigSpec serverSpec;
    public static final Server SERVER;
    static {
        final Pair<Server, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Server::new);
        serverSpec = specPair.getRight();
        SERVER = specPair.getLeft();
    }
}
