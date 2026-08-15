package net.scotchmint.jedc.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.scotchmint.jedc.JustEnoughDungeonCrawlers;
import net.scotchmint.jedc.block.entity.custom.AscenderBlockEntity;
import net.scotchmint.jedc.dimension.DimensionalManager;
import net.scotchmint.jedc.items.ModItems;
import org.jetbrains.annotations.Nullable;

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
                    return ItemActionResult.SUCCESS;
                }
            }

            return ItemActionResult.FAIL;
        }
        return ItemActionResult.FAIL;
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