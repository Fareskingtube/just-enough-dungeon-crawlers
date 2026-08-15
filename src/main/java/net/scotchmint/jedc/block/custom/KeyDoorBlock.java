package net.scotchmint.jedc.block.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.scotchmint.jedc.items.ModItems;

import java.util.LinkedList;
import java.util.Queue;

public class KeyDoorBlock extends Block {
    public KeyDoorBlock(Settings settings) {
        super(settings);
    }

    private void destroyConnectedBlocks(World world, BlockPos startPos) {
        if (world.isClient()) return;

        Queue<BlockPos> queue = new LinkedList<>();
        queue.add(startPos);
        Block targetBlock = this;

        while (!queue.isEmpty()) {
            BlockPos currentPos = queue.poll();

            // Verify current block matches the target before deleting
            if (world.getBlockState(currentPos).isOf(targetBlock)) {
                // Set to air (deleting the block)
                world.setBlockState(currentPos, net.minecraft.block.Blocks.AIR.getDefaultState(), 3);

                // Check all 6 adjacent sides
                BlockPos[] neighbors = {
                        currentPos.up(), currentPos.down(),
                        currentPos.north(), currentPos.south(),
                        currentPos.east(), currentPos.west()
                };

                for (BlockPos neighbor : neighbors) {
                    if (world.getBlockState(neighbor).isOf(targetBlock)) {
                        queue.add(neighbor);
                    }
                }
            }
        }
    }

    @Override
    protected ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (stack.getItem() == ModItems.DIMENSIONAL_KEY) {
            destroyConnectedBlocks(world, pos);
            stack.setCount(0);
            return ItemActionResult.SUCCESS;
        }
        return ItemActionResult.FAIL;
    }
}
