package com.mjr.spaceAstronomyTweaks;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.fml.common.IWorldGenerator;
import net.minecraftforge.oredict.OreDictionary;

public class WorldGenerationEnd implements IWorldGenerator {

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
		switch (world.provider.getDimensionType().getId()) {
		case 1:
			generateSurface(world, random, chunkX * 16, chunkZ * 16);
		}

	}

	private void generateSurface(World world, Random rand, int chunkX, int chunkZ) {
		for (int i = 0; i < 13; i++) {
			int randPosX = chunkX + rand.nextInt(16);
			int randPosY = rand.nextInt(64);
			int randPosZ = chunkZ + rand.nextInt(16);
			try {
				List<ItemStack> array = OreDictionary.getOres("oreAmethyst");
				if (array == null)
					return;
				ItemStack ore = array.get(0);
				if (ore == null)
					return;
				Block oreBlock = Block.getBlockFromItem(ore.getItem());
				if (oreBlock == null)
					return;
				(new WorldGenMinableCustom(oreBlock.getDefaultState(), 3)).generate(world, rand, new BlockPos(randPosX, randPosY, randPosZ));
				List<ItemStack> array2 = OreDictionary.getOres("oreEnderEssence");
				if (array2 == null)
					return;
				ItemStack ore2 = array2.get(0);
				if (ore2 == null)
					return;
				Block oreBlock2 = Block.getBlockFromItem(ore2.getItem());
				if (oreBlock2 == null)
					return;
				(new WorldGenMinableCustom(oreBlock2.getDefaultState(), 3)).generate(world, rand, new BlockPos(randPosX, randPosY, randPosZ));
			} catch (Exception ex) {
				System.out.println(ex.getMessage());
				return;
			}
		}
	}
}