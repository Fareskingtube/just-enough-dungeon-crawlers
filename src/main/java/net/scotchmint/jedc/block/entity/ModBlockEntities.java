package net.scotchmint.jedc.block.entity;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.scotchmint.jedc.JustEnoughDungeonCrawlers;
import net.scotchmint.jedc.block.ModBlocks;
import net.scotchmint.jedc.block.entity.custom.AscenderBlockEntity;

public class ModBlockEntities {
    public static final BlockEntityType<AscenderBlockEntity> REVIVAL_ALTAR_BE = Registry.register(Registries.BLOCK_ENTITY_TYPE,
            Identifier.of(JustEnoughDungeonCrawlers.MOD_ID, "revival_be"),
            BlockEntityType.Builder.create(AscenderBlockEntity::new, ModBlocks.ASCENDER_BLOCK).build(null));

    public static void registerBlockEntities() {
        JustEnoughDungeonCrawlers.LOGGER.info("Registering Block Entities for " + JustEnoughDungeonCrawlers.MOD_ID);
    }
}