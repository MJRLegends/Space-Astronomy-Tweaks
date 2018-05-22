package com.mjr.spaceAstronomyTweaks;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.UseHoeEvent;
import net.minecraftforge.event.world.BlockEvent;
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
				final MinecraftServer server = event.player.worldObj.getMinecraftServer();
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
							player.addChatMessage(new TextComponentString(TextFormatting.RED + "The Achievement Book has been reset! It has happened because the mod pack dev has updated the Achievement Book in the mod pack update!"));
							if (worldName.equalsIgnoreCase("world"))
								server.getCommandManager().executeCommand(new AdminExecute(player), "kick " + player.getName() + " Please reconnect");
							SpaceAstronomyTweaks.logger.warn("SPACE-ASTRONOMY-TWEAKS: " + number);
						}
					});
					SpaceAstronomyTweaks.logger.warn("SPACE-ASTRONOMY-TWEAKS: Simple Achievements has been reset on the following world/server: " + worldName
							+ " this was due to mod pack changes were made and the developer has enabled this option! (NOTE: THE RESET WILL ONLY HAPPEN ONCE PER WORLD, PER UPDATE!)");
					Config.addWorldToList(worldName);
				} else {
					if (worldName.equalsIgnoreCase("world"))
						player.addChatMessage(new TextComponentString(TextFormatting.RED + "The Achievement Book has been reset! It has happened because the mod pack dev has updated the Achievement Book in the mod pack update!"));
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

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onDamageTaken(LivingHurtEvent event) {
		Entity entity = event.getSource().getEntity();
		if (entity != null) {
			if (!(entity instanceof FakePlayer) && (entity instanceof EntityPlayer)) {
				if (Config.removeToolEffectiveness == false)
					return;
				EntityPlayer player = (EntityPlayer) entity;
				ItemStack stack = player.getHeldItemMainhand();
				if (stack == null)
					return;
				if (stack.getItem() == Items.WOODEN_SWORD || stack.getItem() == Items.STONE_SWORD || stack.getItem() == Items.IRON_SWORD || stack.getItem() == Items.GOLDEN_SWORD || stack.getItem() == Items.DIAMOND_SWORD) {
					event.setCanceled(true);
				}
				stack = player.getHeldItemOffhand();
				if (stack == null)
					return;
				if (stack.getItem() == Items.WOODEN_SWORD || stack.getItem() == Items.STONE_SWORD || stack.getItem() == Items.IRON_SWORD || stack.getItem() == Items.GOLDEN_SWORD || stack.getItem() == Items.DIAMOND_SWORD) {
					event.setCanceled(true);
				}
			}
		}
	}

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onItemUse(PlayerEvent.BreakSpeed event) {
		EntityPlayer player = event.getEntityPlayer();
		if (player != null) {
			if (Config.removeToolEffectiveness == false)
				return;
			ItemStack stack = player.getHeldItemMainhand();
			if (stack == null)
				return;
			if (stack.getItem() == Items.WOODEN_AXE || stack.getItem() == Items.STONE_AXE || stack.getItem() == Items.IRON_AXE || stack.getItem() == Items.GOLDEN_AXE || stack.getItem() == Items.DIAMOND_AXE || stack.getItem() == Items.WOODEN_PICKAXE
					|| stack.getItem() == Items.STONE_PICKAXE || stack.getItem() == Items.IRON_PICKAXE || stack.getItem() == Items.GOLDEN_PICKAXE || stack.getItem() == Items.DIAMOND_PICKAXE || stack.getItem() == Items.WOODEN_SHOVEL
					|| stack.getItem() == Items.STONE_SHOVEL || stack.getItem() == Items.IRON_SHOVEL || stack.getItem() == Items.GOLDEN_SHOVEL || stack.getItem() == Items.DIAMOND_SHOVEL) {
				event.setCanceled(true);
			}
			stack = player.getHeldItemOffhand();
			if (stack == null)
				return;
			if (stack.getItem() == Items.WOODEN_AXE || stack.getItem() == Items.STONE_AXE || stack.getItem() == Items.IRON_AXE || stack.getItem() == Items.GOLDEN_AXE || stack.getItem() == Items.DIAMOND_AXE || stack.getItem() == Items.WOODEN_PICKAXE
					|| stack.getItem() == Items.STONE_PICKAXE || stack.getItem() == Items.IRON_PICKAXE || stack.getItem() == Items.GOLDEN_PICKAXE || stack.getItem() == Items.DIAMOND_PICKAXE || stack.getItem() == Items.WOODEN_SHOVEL
					|| stack.getItem() == Items.STONE_SHOVEL || stack.getItem() == Items.IRON_SHOVEL || stack.getItem() == Items.GOLDEN_SHOVEL || stack.getItem() == Items.DIAMOND_SHOVEL) {
				event.setCanceled(true);
			}
		}
	}

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onHoeing(UseHoeEvent event) {
		EntityPlayer player = event.getEntityPlayer();
		if (player != null) {
			if (Config.removeToolEffectiveness == false)
				return;
			ItemStack stack = player.getHeldItemMainhand();
			if (stack == null)
				return;
			if (stack.getItem() == Items.WOODEN_HOE || stack.getItem() == Items.STONE_HOE || stack.getItem() == Items.IRON_HOE || stack.getItem() == Items.GOLDEN_HOE || stack.getItem() == Items.DIAMOND_HOE) {
				event.setCanceled(true);
			}
			stack = player.getHeldItemOffhand();
			if (stack == null)
				return;
			if (stack.getItem() == Items.WOODEN_HOE || stack.getItem() == Items.STONE_HOE || stack.getItem() == Items.IRON_HOE || stack.getItem() == Items.GOLDEN_HOE || stack.getItem() == Items.DIAMOND_HOE) {
				event.setCanceled(true);
			}
		}
	}

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onBlockPlacement(BlockEvent.PlaceEvent event) {
		EntityPlayer player = event.getPlayer();
		World world = event.getWorld();
		if (player != null && world != null) {
			if (world.isRemote == false) {
				if (Config.removeRF_CC_Compatibility) {
					Block placedBlock = event.getPlacedBlock().getBlock();
					if (event.getPlacedBlock() != null) {
						boolean isRSBlock = false;
						boolean isCCBlock = false;

						if (placedBlock.getRegistryName().getResourceDomain().equalsIgnoreCase("refinedstorage"))
							isRSBlock = true;
						if (placedBlock.getRegistryName().getResourceDomain().equalsIgnoreCase("colossalchests"))
							isCCBlock = true;

						if (isRSBlock || isCCBlock) {
							if (placedBlock.getRegistryName().getResourcePath().equalsIgnoreCase("external_storage") || placedBlock.getRegistryName().getResourcePath().equalsIgnoreCase("interface")) {
								BlockPos posOld = event.getPos();
								BlockPos pos = posOld;

								pos = posOld.west();
								IBlockState state = world.getBlockState(pos);
								pos = posOld.south();
								IBlockState state1 = world.getBlockState(pos);
								pos = posOld.north();
								IBlockState state2 = world.getBlockState(pos);
								pos = posOld.east();
								IBlockState state3 = world.getBlockState(pos);
								pos = posOld.up();
								IBlockState state4 = world.getBlockState(pos);
								pos = posOld.down();
								IBlockState state5 = world.getBlockState(pos);

								if (state != null && state1 != null && state2 != null && state3 != null && state4 != null && state5 != null) {
									boolean removeBlock = false;
									if (isCCBlock) {
										if (state.getBlock().getRegistryName().getResourceDomain().equalsIgnoreCase("refinedstorage") || state1.getBlock().getRegistryName().getResourceDomain().equalsIgnoreCase("refinedstorage")
												|| state2.getBlock().getRegistryName().getResourceDomain().equalsIgnoreCase("refinedstorage") || state3.getBlock().getRegistryName().getResourceDomain().equalsIgnoreCase("refinedstorage")
												|| state4.getBlock().getRegistryName().getResourceDomain().equalsIgnoreCase("refinedstorage") || state5.getBlock().getRegistryName().getResourceDomain().equalsIgnoreCase("refinedstorage"))
											if (state.getBlock().getRegistryName().getResourcePath().equalsIgnoreCase("external_storage") || state1.getBlock().getRegistryName().getResourcePath().equalsIgnoreCase("external_storage")
													|| state2.getBlock().getRegistryName().getResourcePath().equalsIgnoreCase("external_storage") || state3.getBlock().getRegistryName().getResourcePath().equalsIgnoreCase("external_storage")
													|| state4.getBlock().getRegistryName().getResourcePath().equalsIgnoreCase("external_storage") || state5.getBlock().getRegistryName().getResourcePath().equalsIgnoreCase("external_storage"))
												removeBlock = true;
									} else if (isRSBlock) {
										if (state.getBlock().getRegistryName().getResourceDomain().equalsIgnoreCase("colossalchests") || state1.getBlock().getRegistryName().getResourceDomain().equalsIgnoreCase("colossalchests")
												|| state2.getBlock().getRegistryName().getResourceDomain().equalsIgnoreCase("colossalchests") || state3.getBlock().getRegistryName().getResourceDomain().equalsIgnoreCase("colossalchests")
												|| state4.getBlock().getRegistryName().getResourceDomain().equalsIgnoreCase("colossalchests") || state5.getBlock().getRegistryName().getResourceDomain().equalsIgnoreCase("colossalchests"))
											if (state.getBlock().getRegistryName().getResourcePath().equalsIgnoreCase("interface") || state1.getBlock().getRegistryName().getResourcePath().equalsIgnoreCase("interface")
													|| state2.getBlock().getRegistryName().getResourcePath().equalsIgnoreCase("interface") || state3.getBlock().getRegistryName().getResourcePath().equalsIgnoreCase("interface")
													|| state4.getBlock().getRegistryName().getResourcePath().equalsIgnoreCase("interface") || state5.getBlock().getRegistryName().getResourcePath().equalsIgnoreCase("interface"))
												removeBlock = true;
									}
									if (removeBlock) {
										if (!(player instanceof FakePlayer))
											player.addChatMessage(new TextComponentString(TextFormatting.RED + "Sorry but this compatibility has been disabled due to issues!"));
										event.setCanceled(true);
									}
								}
							}
						}
					}
				}
				if (Config.removeMM_CC_Compatibility) {
					Block placedBlock = event.getPlacedBlock().getBlock();
					if (event.getPlacedBlock() != null) {
						boolean isRSBlock = false;
						boolean isCCBlock = false;

						if (placedBlock.getRegistryName().getResourceDomain().equalsIgnoreCase("mcmultipart"))
							isRSBlock = true;
						if (placedBlock.getRegistryName().getResourceDomain().equalsIgnoreCase("colossalchests"))
							isCCBlock = true;

						if (isRSBlock || isCCBlock) {
							if (placedBlock.getRegistryName().getResourcePath().equalsIgnoreCase("multipart") || placedBlock.getRegistryName().getResourcePath().equalsIgnoreCase("interface")) {
								BlockPos posOld = event.getPos();
								BlockPos pos = posOld;

								pos = posOld.west();
								IBlockState state = world.getBlockState(pos);
								pos = posOld.south();
								IBlockState state1 = world.getBlockState(pos);
								pos = posOld.north();
								IBlockState state2 = world.getBlockState(pos);
								pos = posOld.east();
								IBlockState state3 = world.getBlockState(pos);
								pos = posOld.up();
								IBlockState state4 = world.getBlockState(pos);
								pos = posOld.down();
								IBlockState state5 = world.getBlockState(pos);

								if (state != null && state1 != null && state2 != null && state3 != null && state4 != null && state5 != null) {
									boolean removeBlock = false;
									if (isCCBlock) {
										if (state.getBlock().getRegistryName().getResourceDomain().equalsIgnoreCase("mcmultipart") || state1.getBlock().getRegistryName().getResourceDomain().equalsIgnoreCase("mcmultipart")
												|| state2.getBlock().getRegistryName().getResourceDomain().equalsIgnoreCase("mcmultipart") || state3.getBlock().getRegistryName().getResourceDomain().equalsIgnoreCase("mcmultipart")
												|| state4.getBlock().getRegistryName().getResourceDomain().equalsIgnoreCase("mcmultipart") || state5.getBlock().getRegistryName().getResourceDomain().equalsIgnoreCase("mcmultipart"))
											if (state.getBlock().getRegistryName().getResourcePath().equalsIgnoreCase("multipart") || state1.getBlock().getRegistryName().getResourcePath().equalsIgnoreCase("multipart")
													|| state2.getBlock().getRegistryName().getResourcePath().equalsIgnoreCase("multipart") || state3.getBlock().getRegistryName().getResourcePath().equalsIgnoreCase("multipart")
													|| state4.getBlock().getRegistryName().getResourcePath().equalsIgnoreCase("multipart") || state5.getBlock().getRegistryName().getResourcePath().equalsIgnoreCase("multipart"))
												removeBlock = true;
									} else if (isRSBlock) {
										if (state.getBlock().getRegistryName().getResourceDomain().equalsIgnoreCase("colossalchests") || state1.getBlock().getRegistryName().getResourceDomain().equalsIgnoreCase("colossalchests")
												|| state2.getBlock().getRegistryName().getResourceDomain().equalsIgnoreCase("colossalchests") || state3.getBlock().getRegistryName().getResourceDomain().equalsIgnoreCase("colossalchests")
												|| state4.getBlock().getRegistryName().getResourceDomain().equalsIgnoreCase("colossalchests") || state5.getBlock().getRegistryName().getResourceDomain().equalsIgnoreCase("colossalchests"))
											if (state.getBlock().getRegistryName().getResourcePath().equalsIgnoreCase("interface") || state1.getBlock().getRegistryName().getResourcePath().equalsIgnoreCase("interface")
													|| state2.getBlock().getRegistryName().getResourcePath().equalsIgnoreCase("interface") || state3.getBlock().getRegistryName().getResourcePath().equalsIgnoreCase("interface")
													|| state4.getBlock().getRegistryName().getResourcePath().equalsIgnoreCase("interface") || state5.getBlock().getRegistryName().getResourcePath().equalsIgnoreCase("interface"))
												removeBlock = true;
									}
									if (removeBlock) {
										if (!(player instanceof FakePlayer))
											player.addChatMessage(new TextComponentString(TextFormatting.RED + "Sorry but this compatibility has been disabled due to issues!"));
										event.setCanceled(true);
									}
								}
							}
						}
					}
				}
				
			}
		}
	}
}
