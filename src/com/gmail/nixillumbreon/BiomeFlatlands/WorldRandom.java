package com.gmail.nixillumbreon.BiomeFlatlands;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.World;

public class WorldRandom extends Random {
	private void setWorldRandomSeed(long s, int x, int z) {
		setSeed(x * s - z);
		long rx1 = nextLong();
		long rx2 = nextLong();
		long rx3 = nextLong();
		long rx4 = nextLong();
		setSeed(z * s - x);
		long rz1 = nextLong();
		long rz2 = nextLong();
		long rz3 = nextLong();
		long rz4 = nextLong();
		
		setSeed(rx4 + rz4
				+ (rx2 - rz3)
				+ (rx1 - rz2)
				+ (rz1 - rx3));
	}
	
	public WorldRandom(long s, int x, int z) {
		setWorldRandomSeed(s, x, z);
	}
	
	public WorldRandom(World w, int x, int z) {
		setWorldRandomSeed(w.getSeed(), x, z);
	}
	
	public WorldRandom(long s, Chunk c) {
		setWorldRandomSeed(s, c.getX(), c.getZ());
	}
	
	public WorldRandom(World w, Chunk c) {
		setWorldRandomSeed(w.getSeed(), c.getX(), c.getZ());
	}
}
