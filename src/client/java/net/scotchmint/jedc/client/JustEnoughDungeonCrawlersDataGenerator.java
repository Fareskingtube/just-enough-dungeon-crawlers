package net.scotchmint.jedc.client;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.scotchmint.jedc.client.datagen.ModBlockTagProvider;
import net.scotchmint.jedc.client.datagen.ModLootTableProvider;
import net.scotchmint.jedc.client.datagen.ModModelProvider;
import net.scotchmint.jedc.client.datagen.ModRecipeProvider;

public class JustEnoughDungeonCrawlersDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

		pack.addProvider(ModLootTableProvider::new);
		pack.addProvider(ModModelProvider::new);
		pack.addProvider(ModRecipeProvider::new);
		pack.addProvider(ModBlockTagProvider::new);
	}
}
