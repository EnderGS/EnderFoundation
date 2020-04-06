package endergs.enderfoundation.core;

import endergs.enderfoundation.config.Configuration;
import endergs.enderfoundation.config.EnderFoundationConfig;
import endergs.enderfoundation.crafting.recipe.RecipeUtils;
import endergs.enderfoundation.events.ModRegistry;
import endergs.enderfoundation.utils.EnderOreGen;
import endergs.enderfoundation.utils.RecipeGenerator;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.event.registry.RegistryEntryAddedCallback;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger.*;




public class EnderFoundation implements ModInitializer {

    public static Logger LOGGER =  LogManager.getLogger();

    public static final String MOD_ID = "enderfoundation";
    public static final String MOD_NAME = "EnderFoundation";
    public static EnderFoundation INSTANCE;

    public static ItemGroup ITEMGROUP = FabricItemGroupBuilder.build(
            new Identifier("enderfoundation", "item_group"),
            () -> new ItemStack(EFContent.Ingots.RESONANT));

    public static final Item RESONANT_SWORD = new Item(new Item.Settings().group(ITEMGROUP).maxDamage(10));

    @Override
    public void onInitialize() {
        log(Level.INFO, "Initializing");
        INSTANCE = this;
        new Configuration(EnderFoundationConfig.class, "enderfoundation");

        // Done to force the class to load

        //ModRecipes.GRINDER.getName();

       // ClientboundPackets.init();
        //ServerboundPackets.init();
        ModRegistry.setupContent();
        Registry.register(Registry.ITEM, new Identifier("enderfoundation", "resonant_sword"), RESONANT_SWORD);
        EnderOreGen.initBiomeFeatures();

        //RecipeGenerator.addSmelting(new ItemStack(EFContent.Ores.RESONANT.block), new ItemStack(EFContent.Ingots.RESONANT), 0.7F );

       // ModLoot.init();
       // FluidGeneratorRecipes.init();
        ////Force loads the block entities at the right time
       // TRBlockEntities.THERMAL_GEN.toString();
       // GuiType.AESU.getIdentifier();
       // TRDispenserBehavior.init();
       // PoweredCraftingHandler.setup();

       // Torus.genSizeMap(TechRebornConfig.fusionControlComputerMaxCoilSize);

        //DataAttachment.REGISTRY.register(IDSUManager.class, IDSUManager::new);

       // RedstoneConfiguration.fluidStack = DynamicCellItem.getCellWithFluid(Fluids.LAVA);
       // RedstoneConfiguration.powerStack = new ItemStack(TRContent.RED_CELL_BATTERY);

        LOGGER.info("EnderFoundation setup done!");

    }

    public static void log(Level level, String message){
        LOGGER.log(level, "["+MOD_NAME+"] " + message);
    }

}
