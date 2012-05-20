package com.gmail.nixillumbreon.BiomeFlatlands;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator.BiomeGrid;

public class ChunkGen {
	World world;
	
	public ChunkGen(World w) {
		world = w;
	}
	
	public void generate(ByteChunk bc, Random random, int x, int z,
			BiomeGrid biomes) {
		bc.setLayer(0, Material.BEDROCK);
		bc.setLayer(1, Material.STONE);
		bc.setLayer(2, Material.DIRT);
		bc.setLayer(3, Material.GRASS);
		for (int ix = 0; ix < 16; ix++) {
			for (int iz = 0; iz < 16; iz++) {
				switch (biomes.getBiome(ix, iz)) {
				case BEACH:
					bc.setBlock(ix, 1, iz, Material.SAND);
					bc.setBlock(ix, 2, iz, Material.SAND);
					bc.setBlock(ix, 3, iz, Material.SAND);
					break;
					
				case DESERT_HILLS:
					bc.setBlock(ix, 4, iz, Material.SAND);
				case ICE_DESERT:
				case DESERT:
					bc.setBlock(ix, 3, iz, Material.SAND);
					bc.setBlock(ix, 2, iz, Material.SANDSTONE);
					break;
					
				case SMALL_MOUNTAINS:
					bc.setBlock(ix, 3, iz, Material.STONE);
					bc.setBlock(ix, 2, iz, Material.STONE);
					break;
					
				case FROZEN_OCEAN:
				case OCEAN:
					bc.setBlock(ix, 2, iz, Material.WATER);
					bc.setBlock(ix, 1, iz, Material.DIRT);
					break;
					
				case FROZEN_RIVER:
				case RIVER:
					bc.setBlock(ix, 2, iz, Material.SAND);
					break;
					
				case ICE_MOUNTAINS:
				case TAIGA_HILLS:
					bc.setBlock(ix, 5, iz, Material.SNOW);
				case EXTREME_HILLS:
				case FOREST_HILLS:
				case JUNGLE_HILLS:
					bc.setBlock(ix, 4, iz, Material.GRASS);
				case MUSHROOM_ISLAND:
					bc.setBlock(ix, 3, iz, Material.DIRT);
					break;
					
				case MUSHROOM_SHORE:
					bc.setBlock(ix, 3, iz, Material.MYCEL);
					break;
				}
				switch (biomes.getBiome(ix, iz)) {
				case ICE_DESERT:
				case ICE_PLAINS:
				case TAIGA:
				case TUNDRA:
					bc.setBlock(ix, 4, iz, Material.SNOW);
					break;
					
				case MUSHROOM_ISLAND:
					bc.setBlock(ix, 4, iz, Material.MYCEL);
					break;
					
				case FROZEN_OCEAN:
				case FROZEN_RIVER:
					bc.setBlock(ix, 3, iz, Material.ICE);
					break;
					
				case OCEAN:
				case RIVER:
					bc.setBlock(ix, 3, iz, Material.WATER);
					break;
				}
			}
		}
	}
}
