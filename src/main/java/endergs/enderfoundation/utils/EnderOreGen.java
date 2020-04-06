package endergs.enderfoundation.utils;

import endergs.enderfoundation.block.EFBlocks;
import endergs.enderfoundation.core.EFContent;
import net.fabricmc.fabric.api.event.registry.RegistryEntryAddedCallback;
import net.minecraft.block.Blocks;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.RangeDecoratorConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;

import java.lang.annotation.Target;

public class EnderOreGen {


    public static void initBiomeFeatures() {
        //setupTrees();

        for (Biome biome : Registry.BIOME) {
            addToBiome(biome);
        }

        //Handles modded biomes
        RegistryEntryAddedCallback.event(Registry.BIOME).register((i, identifier, biome) -> addToBiome(biome));
    }



    private static void addOre(Biome biome, EFContent.Ores ore) {
        biome.addFeature(GenerationStep.Feature.UNDERGROUND_ORES, Feature.ORE.configure(
                new OreFeatureConfig(OreFeatureConfig.Target.NATURAL_STONE, ore.block.getDefaultState(), ore.veinSize)).createDecoratedFeature(Decorator.COUNT_RANGE.configure(
                new RangeDecoratorConfig(ore.veinsPerChunk, ore.minY, ore.minY, ore.maxY))));
    }

    private static void addToBiome(Biome biome) {
        if(biome.getCategory() != Biome.Category.NETHER && biome.getCategory() != Biome.Category.THEEND) {
            for (EFContent.Ores ore: EFContent.Ores.values())
            {
                addOre(biome, ore);
            }
        }
    }
}
