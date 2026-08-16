package net.scotchmint.jedc.block.custom;

import com.mojang.serialization.MapCodec;
import net.fabricmc.fabric.api.lookup.v1.block.BlockApiCache;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.StructureTemplateManager;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.structure.pool.StructurePoolBasedGenerator;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.scotchmint.jedc.JustEnoughDungeonCrawlers;
import net.scotchmint.jedc.block.entity.custom.AscenderBlockEntity;
import net.scotchmint.jedc.dimension.DimensionalManager;
import net.scotchmint.jedc.items.ModItems;
import org.apache.logging.log4j.core.jmx.Server;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

public class AscenderBlock extends BlockWithEntity {
    public static final MapCodec<AscenderBlock> CODEC = createCodec(AscenderBlock::new);

    public AscenderBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {

        if (stack.getItem() == ModItems.DIMENSIONAL_KEY) {
            BlockEntity be = world.getBlockEntity(pos);
            if (be instanceof AscenderBlockEntity ascender) {
                if (world instanceof ServerWorld serverWorld) {
                    int currentLevel = ascender.getCurrentTier(serverWorld);
                    PlayerInventory inventory = player.getInventory();
                    JustEnoughDungeonCrawlers.LOGGER.info("test");
                    RegistryKey<World> DimKey = DimensionalManager.getOrCreateVoidTier(world.getServer(), (ServerPlayerEntity) player, currentLevel + 1);
                    for (int i = 0; i < inventory.size(); i++) {
                        ItemStack iStack = inventory.getStack(i);

                        if (!iStack.isEmpty() && iStack.isOf(ModItems.DIMENSIONAL_KEY)) {
                            inventory.setStack(i, ItemStack.EMPTY);
                        }

                        player.currentScreenHandler.sendContentUpdates();
                    }

                    MinecraftServer server = world.getServer();
                    ServerWorld newWorld = server.getWorld(DimKey);
                    generateJigsawStructure(newWorld, pos);
                    ascendToDungeon((ServerPlayerEntity) player, newWorld, pos);
                    return ItemActionResult.SUCCESS;
                }
            }

            return ItemActionResult.FAIL;
        }
        return ItemActionResult.FAIL;
    }

    public boolean generateJigsawStructure(ServerWorld world, BlockPos pos) {
        world.getChunk(pos.getX() >> 4, pos.getZ() >> 4);

        RegistryEntry<StructurePool> pool = world.getRegistryManager()
                .get(RegistryKeys.TEMPLATE_POOL)
                .getEntry(RegistryKey.of(RegistryKeys.TEMPLATE_POOL,
                        Identifier.of("jedc", "anchor")))
                .orElseThrow();

        Identifier targetJigsawId = Identifier.of("jedc", "connector");

        boolean success = StructurePoolBasedGenerator.generate(
                world,
                pool,
                targetJigsawId,
                20,
                pos,
                false
        );

        if (!success) {
            JustEnoughDungeonCrawlers.LOGGER.error("Jigsaw generation failed for pool jedc:anchor at {}", pos);
        }

        return success;
    }

    public void ascendToDungeon(ServerPlayerEntity player, ServerWorld targetWorld, BlockPos genStart) {
        boolean generated = generateJigsawStructure(targetWorld, genStart);

        if (generated) {
            player.teleport(
                    targetWorld,
                    genStart.getX() + 0.5,
                    genStart.getY() + 1, // +1 so they don't spawn inside the floor of the anchor piece
                    genStart.getZ() + 0.5,
                    Set.of(),
                    player.getYaw(),
                    player.getPitch()
            );
        } else {
            JustEnoughDungeonCrawlers.LOGGER.error("Skipping teleport, structure generation failed");
        }
    }

    @Override
    protected BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new AscenderBlockEntity(pos, state);
    }
}