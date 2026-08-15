package net.scotchmint.jedc.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.scotchmint.jedc.JustEnoughDungeonCrawlers;

import java.util.Optional;

public class ModItemGroup {
    public static final net.minecraft.item.ItemGroup JEDC_GROUP = register("hardcore_revived_group",
            FabricItemGroup.builder()
                    .displayName(Text.translatable("itemGroup." + JustEnoughDungeonCrawlers.MOD_ID + ".jedc_group"))
                    .icon(Items.QUARTZ_BLOCK.asItem()::getDefaultStack)
                    .entries((displayContext, entries) -> Registries.ITEM.getIds()
                            .stream()
                            .filter(key -> key.getNamespace().equals(JustEnoughDungeonCrawlers.MOD_ID))
                            .map(Registries.ITEM::getOrEmpty)
                            .map(Optional::orElseThrow)
                            .forEach(entries::add))
                    .build());

    public static <T extends net.minecraft.item.ItemGroup> T register(String name, T itemGroup) {
        return Registry.register(Registries.ITEM_GROUP, Identifier.of(JustEnoughDungeonCrawlers.MOD_ID, name), itemGroup);
    }

    public static void registerItemGroups() {
        JustEnoughDungeonCrawlers.LOGGER.info("Registering Mod Item Groups for " + JustEnoughDungeonCrawlers.MOD_ID);
    }
}
