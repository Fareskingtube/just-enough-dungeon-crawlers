package net.scotchmint.jedc.block.entity.custom;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.scotchmint.jedc.JustEnoughDungeonCrawlers;
import net.scotchmint.jedc.block.entity.ModBlockEntities;

public class AscenderBlockEntity extends BlockEntity {
    public AscenderBlockEntity( BlockPos pos, BlockState state) {
        super(ModBlockEntities.REVIVAL_ALTAR_BE ,pos, state);
    }
}
