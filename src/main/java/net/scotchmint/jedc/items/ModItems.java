package net.scotchmint.jedc.items;

import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.scotchmint.jedc.JustEnoughDungeonCrawlers;

public class ModItems {
    public static final Item DIMENSIONAL_KEY = registerItem("dimensional_key", new Item(new Item.Settings()
            .maxCount(1)
            .fireproof()));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(JustEnoughDungeonCrawlers.MOD_ID, name), item);
    }

    public static void registerModItems() {
        JustEnoughDungeonCrawlers.LOGGER.info("Registering Mod Items for " + JustEnoughDungeonCrawlers.MOD_ID);
    }
}
