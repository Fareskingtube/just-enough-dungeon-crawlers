package net.scotchmint.jedc.block.entity.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.scotchmint.jedc.JustEnoughDungeonCrawlers;
import net.scotchmint.jedc.block.entity.ModBlockEntities;

public class AscenderBlockEntity extends BlockEntity {
//    private int currentLevel = 1;


    public int getCurrentTier(ServerWorld world) {
        String path = world.getRegistryKey().getValue().getPath(); // e.g. "void_tier_3"
        if (path.startsWith("dungeon_tier_")) {
            try {
                return Integer.parseInt(path.substring("dungeon_tier_".length()));
            } catch (NumberFormatException e) {
                JustEnoughDungeonCrawlers.LOGGER.error("Malformed void tier dimension id: {}", path);
            }
        }
        return 0; // overworld or unrecognized dimension = base tier
    }


    public void setCurrentLevel(int currentLevel) {
//        this.currentLevel = currentLevel;
        markDirty();
        if (world != null && !world.isClient) {
            world.updateListeners(pos, getCachedState(), getCachedState(), Block.NOTIFY_ALL);
        }
    }

    public AscenderBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.REVIVAL_ALTAR_BE, pos, state);
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);

//        nbt.putInt("currentLevel", this.currentLevel);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);

//        this.currentLevel = nbt.getInt("currentLevel");
    }
}
