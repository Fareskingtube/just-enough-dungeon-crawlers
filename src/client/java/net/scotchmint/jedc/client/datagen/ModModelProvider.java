package net.scotchmint.jedc.client.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import net.scotchmint.jedc.block.ModBlocks;
import net.scotchmint.jedc.items.ModItems;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.DUNGEON_WALL);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.ASCENDER_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.KEY_DOOR);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ModItems.DIMENSIONAL_KEY, Models.GENERATED);
        itemModelGenerator.register(ModItems.DUNGEON_ESSENCE, Models.GENERATED);
        itemModelGenerator.register(ModItems.DUNGEON_ESSENCE_SWORD, Models.GENERATED);
    }
}
