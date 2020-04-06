package endergs.enderfoundation.events;

import endergs.enderfoundation.core.EFContent;
import endergs.enderfoundation.core.EnderFoundation;
import endergs.enderfoundation.core.EnderRegistry;
import endergs.enderfoundation.utils.InitUtils;
import endergs.enderfoundation.utils.InitUtils.*;
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

import java.util.Arrays;



public class ModRegistry {

    public static void setupContent() {
        registerBlocks();
        registerItems();
        registerRecipes();

    }

    private static void registerBlocks() {
        //Settings itemGroup = new Settings().group(EnderFoundation.ITEMGROUP);
        //Arrays.stream(EFContent.Ores.values()).forEach(value -> EnderRegistry.registerBlock(value.block, itemGroup));
        //Arrays.stream(EFContent.Blocks.values()).forEach(value -> EnderRegistry.registerBlock(value.block, itemGroup));
        Arrays.stream(EFContent.Ores.values()).forEach(value -> EnderRegistry.registerBlock(value.block));
        Arrays.stream(EFContent.Blocks.values()).forEach(value -> EnderRegistry.registerBlock(value.block));
        // Misc. blocks


        EnderFoundation.LOGGER.info("EnderFoundation Blocks Loaded");
    }

    private static void registerItems() {
        Arrays.stream(EFContent.Ingots.values()).forEach(value -> EnderRegistry.registerItem(value.item));
        Arrays.stream(EFContent.Nuggets.values()).forEach(value -> EnderRegistry.registerItem(value.item));




        EnderFoundation.LOGGER.info("EnderFoundation Items Loaded");
    }

    private static void registerRecipes() {

        Arrays.stream(EFContent.Blocks.values()).forEach(RecipeGenerator::addBlockRecipe);
        Arrays.stream(EFContent.Ingots.values()).forEach(RecipeGenerator::addIngotRecipe);
        Arrays.stream(EFContent.Nuggets.values()).forEach(RecipeGenerator::addNuggetRecipe);
    }

}
