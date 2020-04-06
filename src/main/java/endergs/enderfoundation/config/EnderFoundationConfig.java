package endergs.enderfoundation.config;

import java.io.File;
import java.nio.file.Path;

public class EnderFoundationConfig {

//All moved into one class as its a lot easier to find the annotations when you know where they all are


    // Items
    @Config(config = "items", category = "general", key = "enableGemTools", comment = "Enable Gem armor and tools")
    public static boolean enableGemArmorAndTools = true;

    @Config(config = "items", category = "power", key = "nanoSaberCharge", comment = "Energy Capacity for Nano Saber")
    public static int nanosaberCharge = 1_000_000;

    @Config(config ="data", category = "recipes", key = "recipeFolder", comment = "Location of the Recipe folder in the src")
    public static String recipeFolder = "C:\\Users\\Admin\\Documents\\Mods\\EnderFoundation\\src\\main\\resources\\data\\enderfoundation\\recipes";

}
