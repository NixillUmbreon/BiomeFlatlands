package com.gmail.nixillumbreon.BiomeFlatlands;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.generator.BlockPopulator;

public class ChunkPop extends BlockPopulator {

	@Override
	public void populate(World world, Random random, Chunk source) {
		// Set random seed
		random = new WorldRandom(world, source);
		int wx = source.getX() * 16;
		int wz = source.getZ() * 16;
		
		// Create ore
		
		int oreLocation = random.nextInt(256);
		Block locBlock = world.getBlockAt((oreLocation >> 4) + wx, 1, (oreLocation & 0xF) + wz);
		Block locBlockX = world.getBlockAt((oreLocation >> 4) + 1 + wx, 1, (oreLocation & 0xF) + wz);
		Block locBlockZ = world.getBlockAt((oreLocation >> 4) + wx, 1, (oreLocation & 0xF) + 1 + wz);
		Block locBlockXZ = world.getBlockAt((oreLocation >> 4) + 1 + wx, 1, (oreLocation & 0xF) + 1 + wz);
		int oreType = random.nextInt(8);
		int oreBlockType = 1;
		int oreDirtBlockType = 3;
		switch (oreType) {
		case 0:	oreBlockType = Material.DIRT.getId(); break;
		case 1: oreBlockType = Material.GRAVEL.getId(); break;
		case 2: oreBlockType = Material.COAL_ORE.getId(); break;
		case 3: oreBlockType = Material.IRON_ORE.getId(); break;
		case 4: oreBlockType = Material.LAPIS_ORE.getId(); break;
		case 5: oreBlockType = Material.GOLD_ORE.getId(); break;
		case 6: oreBlockType = Material.REDSTONE_ORE.getId(); break;
		case 7: oreBlockType = Material.DIAMOND_ORE.getId(); oreDirtBlockType = Material.CLAY.getId(); break;
		}
		switch (locBlock.getType()) {
		case DIRT: locBlock.setTypeId(oreDirtBlockType); break;
		case STONE: locBlock.setTypeId(oreBlockType); break;
		}
		switch (locBlockX.getType()) {
		case DIRT: locBlockX.setTypeId(oreDirtBlockType); break;
		case STONE: locBlockX.setTypeId(oreBlockType); break;
		}
		switch (locBlockZ.getType()) {
		case DIRT: locBlockZ.setTypeId(oreDirtBlockType); break;
		case STONE: locBlockZ.setTypeId(oreBlockType); break;
		}
		switch (locBlockXZ.getType()) {
		case DIRT: locBlockXZ.setTypeId(oreDirtBlockType); break;
		case STONE: locBlockXZ.setTypeId(oreBlockType); break;
		}
		
		/* Create Trees and Grasses
		 * This attempts 20 times to place something. If a specific biome has n tries, then trying to place 
		 * something in that biome will automatically fail if it's the (n+1)th try or later.
		 * 
		 * Beach: Nothing
		 * Desert: 1% dead shrub, 33% 1-tall cactus, 50% 2-tall cactus, 16% 3-tall cactus; 20 tries
		 *     A cactus adjacent to anything but air will become a dead shrub.  
		 * Desert hill: Same as Desert
		 * Extreme hills: 90% nothing, 9% small tree, 1% big tree; 5 tries
		 * Forest: 60% small oak tree, 30% birch tree, 10% big tree; 10 tries
		 * Forest hills: Same as forest, but one block higher
		 * Frozen ocean: Nothing
		 * Frozen river: 95% nothing, 5% clay; 10 tries
		 * Hell: 95% nothing, 3% gravel, 2% glowstone; 20 tries
		 * Ice desert: Nothing!
		 * Ice mountains: Same as Extreme Hills
		 * Ice plains: Same as Extreme Hills
		 * Jungle: 50% jungle bush, 30% small jungle tree, 18% large jungle tree, 2% fern; 15 tries
		 * Jungle hills: Same as Jungle
		 * Mushroom island: 30% red mushroom, 30% brown mushroom, 20% huge red mushroom, 20% huge brown mushroom
		 *     5 tries
		 * Mushroom shore: Same as mushroom
		 * Ocean: 24% nothing, 25% 1-tall sugarcane, 40% 2-tall sugarcane, 20% 3-tall sugarcane,
		 *     1% 4-tall sugarcane; 20 tries
		 *     Sugarcane get placed on an adjacent land block, if any adjacent block is land, otherwise it's not created.
		 * Plains: 90% grass, 6% yellow flower, 4% red flower; 10 tries
		 * Rainforest: Same as Forest
		 * River: Same as Ocean
		 * Savanna: Same as Plains
		 * Seasonal forest: Same as Forest
		 * Shrubland: Same as Plains
		 * Sky: Nothing
		 * Small Mountains: Same as Extreme Hills. Planting block is changed to Dirt.
		 * Swampland: 10 tries
		 *     If adjacent to any water, including diagonally, place a lily pad.
		 *     Otherwise, create a swamp tree.
		 * Taiga: 50% redwood, 50% tall; 5 tries
		 * Taiga hill: Same as Taiga
		 * Tundra: Same as Ice Plains
		 */
		for (int trial = 0; trial < 20; trial++) {
			int loc = random.nextInt(256);
			int x = (loc >> 4);
			int wX = wx+x;
			int z = (loc & 0xF);
			int wZ = wz+z;
			int index = random.nextInt(100);
			Biome b = source.getBlock(x, 0, z).getBiome();
			Block bl;
			if ((b == Biome.DESERT || b == Biome.DESERT_HILLS) && (trial < 7)) {
			  int yAt;
				if (b == Biome.DESERT_HILLS) yAt = 5;
				else yAt = 4;
				bl = world.getBlockAt(wX, yAt, wZ);
				if (bl.getType() == Material.AIR) {
					if (world.getBlockAt(wX+1, yAt, wZ).getType() == Material.AIR
						&& world.getBlockAt(wX, yAt, wZ+1).getType() == Material.AIR
						&& world.getBlockAt(wX-1, yAt, wZ).getType() == Material.AIR
						&& world.getBlockAt(wX, yAt, wZ-1).getType() == Material.AIR) {
						if (index == 0) {
							bl.setType(Material.DEAD_BUSH);
						} else {
							bl.setType(Material.CACTUS);
							if (index > 33) {
								bl.getRelative(BlockFace.UP).setType(Material.CACTUS);
								if (index > 83) {
									bl.getRelative(BlockFace.UP, 2).setType(Material.CACTUS);
								}
							}
						}
					} else {
						bl.setType(Material.DEAD_BUSH);
					}
				}
			} else { if ((b == Biome.EXTREME_HILLS || b == Biome.ICE_MOUNTAINS || b == Biome.ICE_PLAINS || b == Biome.SMALL_MOUNTAINS) && (trial < 5)) {
				if (b == Biome.EXTREME_HILLS || b == Biome.ICE_MOUNTAINS) bl = world.getBlockAt(wX, 5, wZ);
				else bl = world.getBlockAt(wX, 4, wZ);
				boolean tsuccess = false;
				if (bl.getType() == Material.SNOW) bl.setType(Material.AIR);
				if (bl.getType() == Material.AIR) {
					bl.getRelative(BlockFace.DOWN).setType(Material.DIRT);
					if (index < 9) {
						tsuccess = world.generateTree(bl.getLocation(), TreeType.TREE);
					} else { if (index == 9) {
						tsuccess = world.generateTree(bl.getLocation(), TreeType.BIG_TREE);
					} }
					if (!tsuccess) {
						if (b == Biome.SMALL_MOUNTAINS) {
							bl.getRelative(BlockFace.DOWN).setType(Material.STONE);
						} else {
							bl.getRelative(BlockFace.DOWN).setType(Material.GRASS);
							if (b != Biome.EXTREME_HILLS) { bl.setType(Material.SNOW); }
						}
					}
				}
			} else { if ((b == Biome.FOREST || b == Biome.FOREST_HILLS || b == Biome.RAINFOREST || b == Biome.SEASONAL_FOREST) && (trial < 10)) {
				if (b == Biome.FOREST_HILLS) bl = world.getBlockAt(wX, 5, wZ);
				else bl = world.getBlockAt(wX, 4, wZ);
				if (bl.getType() == Material.AIR){
					if (index < 60) {
						world.generateTree(bl.getLocation(), TreeType.TREE);
					} else { if (index < 90) {
						world.generateTree(bl.getLocation(), TreeType.BIRCH);
					} else {
						world.generateTree(bl.getLocation(), TreeType.BIG_TREE);
					} }
				}
			} else { if (b == Biome.HELL) {
				bl = world.getBlockAt(wX, 4, wZ);
				if (index < 2) {
					bl.setType(Material.GLOWSTONE);
				} else { if (index < 5) {
					bl.setType(Material.GRAVEL);
				} }
			} else { if ((b == Biome.JUNGLE || b == Biome.JUNGLE_HILLS) && (trial < 15)) {
				if (b == Biome.JUNGLE_HILLS) bl = world.getBlockAt(wX, 5, wZ);
				else bl = world.getBlockAt(wX, 4, wZ);
				if (bl.getType() == Material.AIR) {
					if (index < 50) {
						world.generateTree(bl.getLocation(), TreeType.JUNGLE_BUSH);
					} else { if (index < 80) {
						world.generateTree(bl.getLocation(), TreeType.SMALL_JUNGLE);
					} else { if (index < 98) {
						world.generateTree(bl.getLocation(), TreeType.JUNGLE);
					} else {
						bl.setTypeIdAndData(31, (byte) 2, false);
					} } }
				}
			} else { if ((b == Biome.MUSHROOM_ISLAND || b == Biome.MUSHROOM_SHORE) && (trial < 5)) {
				if (b == Biome.MUSHROOM_ISLAND) bl = world.getBlockAt(wX, 5, wZ);
				else bl = world.getBlockAt(wX, 4, wZ);
				if (bl.getType() == Material.AIR) {
					if (index < 30) {
						bl.setType(Material.RED_MUSHROOM);
					} else { if (index < 60) {
						bl.setType(Material.BROWN_MUSHROOM);
					} else { if (index < 80) {
						world.generateTree(bl.getLocation(), TreeType.RED_MUSHROOM);
					} else {
						world.generateTree(bl.getLocation(), TreeType.BROWN_MUSHROOM);
					} } }
				}
			} else { if (b == Biome.OCEAN || b == Biome.RIVER) {
				bl = world.getBlockAt(wX-1, 3, wZ);
				if ((bl.getType() != Material.SAND && bl.getType() != Material.GRASS) || bl.getRelative(BlockFace.UP).getType() != Material.AIR) bl = world.getBlockAt(wX, 3, wZ+1);
				if ((bl.getType() != Material.SAND && bl.getType() != Material.GRASS) || bl.getRelative(BlockFace.UP).getType() != Material.AIR) bl = world.getBlockAt(wX-1, 3, wZ);
				if ((bl.getType() != Material.SAND && bl.getType() != Material.GRASS) || bl.getRelative(BlockFace.UP).getType() != Material.AIR) bl = world.getBlockAt(wX, 3, wZ-1);
				if ((bl.getType() == Material.SAND || bl.getType() == Material.GRASS) && bl.getRelative(BlockFace.UP).getType() == Material.AIR) {
					bl = bl.getRelative(BlockFace.UP, 1);
					if (index > 23) {
						bl.setType(Material.SUGAR_CANE_BLOCK);
						bl = bl.getRelative(BlockFace.UP);
						if (index > 48) {
							bl.setType(Material.SUGAR_CANE_BLOCK);
							bl = bl.getRelative(BlockFace.UP);
							if (index > 88) {
								bl.setType(Material.SUGAR_CANE_BLOCK);
								bl = bl.getRelative(BlockFace.UP);
								if (index == 99) {
									bl.setType(Material.SUGAR_CANE_BLOCK);
								}
							}
						}
					}
				}
			} else { if ((b == Biome.PLAINS || b == Biome.SAVANNA || b == Biome.SHRUBLAND) && (trial < 10)) {
				bl = world.getBlockAt(wX, 4, wZ);
				if (bl.getType() == Material.AIR) {
					if (index < 90) {
						bl.setType(Material.LONG_GRASS);
						bl.setData((byte) 1);
					} else { if (index < 96) {
						bl.setType(Material.YELLOW_FLOWER);
					} else {
						bl.setType(Material.RED_ROSE);
					} }
				}
			} else { if ((b == Biome.TAIGA || b == Biome.TAIGA_HILLS) && (trial < 5)) {
				if (b == Biome.TAIGA_HILLS) bl = world.getBlockAt(wX, 5, wZ);
				else bl = world.getBlockAt(wX, 4, wZ);
				if (bl.getType() == Material.SNOW) {
				  bl.setType(Material.AIR);
				  boolean tsuccess;
					if (index < 50) {
						tsuccess = world.generateTree(bl.getLocation(), TreeType.REDWOOD);
					} else {
						tsuccess = world.generateTree(bl.getLocation(), TreeType.TALL_REDWOOD);
					}
					if (!tsuccess) {
					  bl.setType(Material.SNOW);
					}
				}
			} else { if ((b == Biome.SWAMPLAND) && (trial < 10)) {
				bl = world.getBlockAt(wX, 3, wZ);
				Block bln = bl.getRelative(BlockFace.NORTH);
				Block ble = bl.getRelative(BlockFace.EAST);
				Block blw = bl.getRelative(BlockFace.WEST);
				Block bls = bl.getRelative(BlockFace.SOUTH);
				Block blu = bl.getRelative(BlockFace.UP);
				boolean w = false;
				if (bln.getType() == Material.WATER || bln.getType() == Material.STATIONARY_WATER) {
					bln.getRelative(BlockFace.UP).setType(Material.WATER_LILY);
					w = true;
				}
				if (ble.getType() == Material.WATER || ble.getType() == Material.STATIONARY_WATER) {
					ble.getRelative(BlockFace.UP).setType(Material.WATER_LILY);
					w = true;
				}
				if (blw.getType() == Material.WATER || blw.getType() == Material.STATIONARY_WATER) {
					blw.getRelative(BlockFace.UP).setType(Material.WATER_LILY);
					w = true;
				}
				if (bls.getType() == Material.WATER || bls.getType() == Material.STATIONARY_WATER) {
					bls.getRelative(BlockFace.UP).setType(Material.WATER_LILY);
					w = true;
				}
				if (!w && blu.getType() == Material.AIR) {
					world.generateTree(blu.getLocation(), TreeType.SWAMP);
				}
			} } } } } } } } } }
		}
		
		// Generate one Liquid in a Mossy Cobblestone cup
		int liquidX = random.nextInt(16) + wx;
		int liquidZ = random.nextInt(16) + wz;
		Block bl = world.getBlockAt(liquidX, 1, liquidZ);
		if (bl.getBiome() != Biome.OCEAN && bl.getBiome() != Biome.FROZEN_OCEAN && bl.getBiome() != Biome.BEACH
		  && bl.getBiome() != Biome.RIVER && bl.getBiome() != Biome.FROZEN_RIVER) {
			if (random.nextBoolean()) {
				if (random.nextBoolean()) bl.setType(Material.STATIONARY_LAVA);
				else bl.setType(Material.STATIONARY_WATER);
			} else {
				if (random.nextBoolean()) bl.setType(Material.STATIONARY_WATER);
				else bl.setType(Material.MOSSY_COBBLESTONE);
			}
		} else bl.setType(Material.MOSSY_COBBLESTONE);
		bl.getRelative(BlockFace.NORTH).setType(Material.MOSSY_COBBLESTONE);
		bl.getRelative(BlockFace.EAST).setType(Material.MOSSY_COBBLESTONE);
		bl.getRelative(BlockFace.WEST).setType(Material.MOSSY_COBBLESTONE);
		bl.getRelative(BlockFace.SOUTH).setType(Material.MOSSY_COBBLESTONE);
	}
}