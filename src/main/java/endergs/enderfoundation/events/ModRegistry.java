package endergs.enderfoundation.events;

import endergs.enderfoundation.core.EFContent;
import endergs.enderfoundation.core.EnderFoundation;
import endergs.enderfoundation.core.EnderRegistry;
import endergs.enderfoundation.fluid.WasteFluid;
import endergs.enderfoundation.utils.InitUtils;
import endergs.enderfoundation.utils.InitUtils.*;
import endergs.enderfoundation.utils.ModelGenerator;
import endergs.enderfoundation.utils.RecipeGenerator;
import net.fabricmc.fabric.api.event.registry.RegistryEntryAddedCallback;
import net.fabricmc.fabric.api.loot.v1.FabricLootPoolBuilder;
import net.fabricmc.fabric.api.loot.v1.event.LootTableLoadingCallback;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.OreBlock;
import net.minecraft.item.Item;
import net.minecraft.item.Item.Settings;
import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterials;
import net.minecraft.loot.ConstantLootTableRange;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.apache.commons.lang3.Validate;

import java.util.ArrayList;
import java.util.Arrays;



public class ModRegistry {

    public static void setupContent() {

        registerBlocks();
        registerItems();

        registerFluids();

        registerRecipes();
        //registerModels();

    }

    private static void registerBlocks() {
        Arrays.stream(EFContent.Ores.values()).forEach(value -> EnderRegistry.registerBlock(value.block));
        Arrays.stream(EFContent.Blocks.values()).forEach(value -> EnderRegistry.registerBlock(value.block));
        // Misc. blocks

        EnderFoundation.LOGGER.info("EnderFoundation Blocks Loaded");
    }

    private static void registerItems() {
        Arrays.stream(EFContent.Ingots.values()).forEach(value -> EnderRegistry.registerItem(value.item));
        Arrays.stream(EFContent.Nuggets.values()).forEach(value -> EnderRegistry.registerItem(value.item));

        Arrays.stream(EFContent.Armor.values()).forEach(value -> Arrays.stream(value.getArmor()).forEach(EnderRegistry::registerItem) );
        EnderFoundation.LOGGER.info("EnderFoundation Items Loaded");
    }

    private static void registerFluids() {
        WasteFluid.registerWaste();
    }

    private static void registerModels() {
        Arrays.stream(EFContent.Armor.values()).forEach(value -> Arrays.stream(value.getArmor()).forEach(ModelGenerator::createItemModel));
        Arrays.stream(EFContent.Ingots.values()).forEach(value -> ModelGenerator.createItemModel(value.item));
        Arrays.stream(EFContent.Nuggets.values()).forEach(value -> ModelGenerator.createItemModel(value.item));

        Arrays.stream(EFContent.Ores.values()).forEach(value -> ModelGenerator.createBlockModel(value.block));
        Arrays.stream(EFContent.Blocks.values()).forEach(value -> ModelGenerator.createBlockModel(value.block));

    }


    private static void registerRecipes() {

        //Arrays.stream(EFContent.Blocks.values()).forEach(RecipeGenerator::addBlockRecipe);
        //Arrays.stream(EFContent.Ingots.values()).forEach(RecipeGenerator::addIngotRecipe);
        //Arrays.stream(EFContent.Nuggets.values()).forEach(RecipeGenerator::addNuggetRecipe);
        Arrays.stream(EFContent.Armor.values()).forEach(RecipeGenerator::addArmorRecipe);



    }

}
