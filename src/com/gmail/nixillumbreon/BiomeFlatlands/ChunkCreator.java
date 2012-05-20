package com.gmail.nixillumbreon.BiomeFlatlands;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;

public class ChunkCreator extends ChunkGenerator {
	ChunkGen generator;
	ChunkPop blockPop;
	
	public ChunkCreator() {
	}

	@Override
	public List<BlockPopulator> getDefaultPopulators(World w) {
		blockPop = new ChunkPop();
		return Arrays.asList((BlockPopulator) blockPop);
	}

	@Override
	public byte[][] generateBlockSections(World world, Random random, int x, int z, ChunkGenerator.BiomeGrid biomes) {
		if (generator == null) generator = new ChunkGen(world);
		ByteChunk bc = new ByteChunk(world, x, z);
		generator.generate(bc, random, x, z, biomes);
		return bc.blocks;
	}
}
