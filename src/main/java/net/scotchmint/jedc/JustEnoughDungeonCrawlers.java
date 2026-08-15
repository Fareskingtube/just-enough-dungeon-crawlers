package net.scotchmint.jedc;

import net.fabricmc.api.ModInitializer;

import net.minecraft.util.Identifier;

import net.scotchmint.jedc.block.ModBlocks;
import net.scotchmint.jedc.block.entity.ModBlockEntities;
import net.scotchmint.jedc.item.ModItemGroup;
import net.scotchmint.jedc.items.ModItems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JustEnoughDungeonCrawlers implements ModInitializer {
	public static final String MOD_ID = "jedc";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItems.registerModItems();
		ModBlocks.registerModBlocks();
		ModBlockEntities.registerBlockEntities();
		ModItemGroup.registerItemGroups();
	}

	public static Identifier id(String path) {
		return Identifier.of(MOD_ID, path);
	}


}
