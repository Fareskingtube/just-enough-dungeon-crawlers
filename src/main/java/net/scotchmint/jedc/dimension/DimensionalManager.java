package net.scotchmint.jedc.dimension;

import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.FlatChunkGenerator;
import net.minecraft.world.gen.chunk.FlatChunkGeneratorConfig;
import net.scotchmint.jedc.JustEnoughDungeonCrawlers;
import qouteall.dimlib.api.DimensionAPI;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public class DimensionalManager {
    public static RegistryKey<World> getOrCreateVoidTier(MinecraftServer server, ServerPlayerEntity player, int tier) {
        Identifier dimId = Identifier.of(JustEnoughDungeonCrawlers.MOD_ID, "dungeon_tier_" + tier);
        RegistryKey<World> dimKey = RegistryKey.of(RegistryKeys.WORLD, dimId);

        DynamicRegistryManager registryManager = server.getRegistryManager();

        DimensionAPI.addDimensionIfNotExists(
                server,
                dimId,
                () -> {
                    Registry<DimensionType> dimTypeRegistry = registryManager.get(RegistryKeys.DIMENSION_TYPE);
                    RegistryEntry<DimensionType> voidType = dimTypeRegistry.getEntry(
                            RegistryKey.of(RegistryKeys.DIMENSION_TYPE, Identifier.of(JustEnoughDungeonCrawlers.MOD_ID, "void_dim"))
                    ).orElseThrow(() -> new IllegalStateException("void_dim dimension type not found"));

                    Registry<Biome> biomeRegistry = registryManager.get(RegistryKeys.BIOME);
                    RegistryEntry<Biome> voidBiome = biomeRegistry.getEntry(BiomeKeys.THE_VOID)
                            .orElseThrow(() -> new IllegalStateException("the_void biome not found"));

                    ChunkGenerator generator = new FlatChunkGenerator(
                            new FlatChunkGeneratorConfig(Optional.empty(), voidBiome, List.of())
                    );

                    return new DimensionOptions(voidType, generator);
                }
        );
        ServerWorld nextWorld = server.getWorld(dimKey);

        player.teleport(nextWorld,  0.5, 0.5, 0.5,
                Set.of(), player.getYaw(), player.getPitch());

        return dimKey;
    }
}
