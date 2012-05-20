package com.gmail.nixillumbreon.BiomeFlatlands;

import java.util.logging.Logger;

import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

public class BiomeFlatlands extends JavaPlugin {
	Logger log;
	@Override
	public ChunkGenerator getDefaultWorldGenerator(String name, String style) {
		return getChunkCreator(name, style);
	}
	
	public void onEnable() {
		log = getLogger();
		log.info("Chunk Generator Test");
	}

	private ChunkGenerator getChunkCreator(String name, String style) {
		return new ChunkCreator();
	}
}
