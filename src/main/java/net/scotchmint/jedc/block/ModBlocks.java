package net.scotchmint.jedc.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.scotchmint.jedc.JustEnoughDungeonCrawlers;
import net.scotchmint.jedc.block.custom.AscenderBlock;

public class ModBlocks {
    public static final Block DUNGEON_WALL = registerBlock("dungeon_wall", new Block(AbstractBlock.Settings.create()
            .pistonBehavior(PistonBehavior.IGNORE)
            .strength(-1.0F, 3600000.0F)));

    public static final Block ASCENDER_BLOCK = registerBlock("ascender_block", new AscenderBlock(AbstractBlock.Settings.create()
            .pistonBehavior(PistonBehavior.IGNORE)
            .strength(-1.0F, 3600000.0F)));

    private static void registerBlockItem(String name, Block block) {
        Registry.register(Registries.ITEM, Identifier.of(JustEnoughDungeonCrawlers.MOD_ID, name), new BlockItem(block, new Item.Settings()));
    }

    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, Identifier.of(JustEnoughDungeonCrawlers.MOD_ID, name), block);
    }

    public static void registerModBlocks() {
        JustEnoughDungeonCrawlers.LOGGER.info("Registering Mod Blocks for " + JustEnoughDungeonCrawlers.MOD_ID);
    }
}

