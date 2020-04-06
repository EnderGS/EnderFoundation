package endergs.enderfoundation.utils;

import com.google.common.collect.ImmutableMap;
import com.google.gson.*;
import com.google.gson.internal.$Gson$Types;
import endergs.enderfoundation.config.EnderFoundationConfig;
import endergs.enderfoundation.core.EFContent;
import endergs.enderfoundation.core.EnderFoundation;
import endergs.enderfoundation.crafting.recipe.RecipeUtils;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.registry.Registry;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class RecipeGenerator {

    // You can include this in your mod/a pack/whatever you want, as long as that work follows the Mojang EULA.
// The original source is viewable at https://gist.github.com/williewillus/a1a899ce5b0f0ba099078d46ae3dae6e

// This is a janky JSON generator, for porting from below 1.12 to 1.12.
// Simply replace calls to GameRegistry.addShapeless/ShapedRecipe with these methods, which will dump it to a json in RECIPE_DIR
// Also works with OD, replace GameRegistry.addRecipe(new ShapedOreRecipe/ShapelessOreRecipe with the same calls
// After you are done, call generateConstants()
// Note that in many cases, you can combine multiple old recipes into one, since you can now specify multiple possibilities for an ingredient without using the OD. See vanilla for examples.

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static File RECIPE_DIR = null;
    private static final Set<String> USED_OD_NAMES = new TreeSet<>();

    private static void setupDir() {
        if (RECIPE_DIR == null) {
            //RECIPE_DIR = EnderFoundationConfig.recipeFolde.resolve("../recipes/").toFile();
            RECIPE_DIR = new File(EnderFoundationConfig.recipeFolder);
        }

        if (!RECIPE_DIR.exists()) {
            RECIPE_DIR.mkdir();
        }
    }



    // EXPERIMENTAL: JSONs generated will definitely not work in 1.12.2 and below, and may not even work when 1.13 comes out
    // When Forge 1.13 is fully released, I will fix this to be correct
    // Usage: Replace calls to GameRegistry.addSmelting with this
    public static void addSmelting(ItemStack in, ItemStack result, float xp) { addSmelting(in, result, xp, 200); }

    // noinspection DuplicateCode


    private static void addSmelting(ItemStack in, ItemStack result, float xp, int cookTime) {
        setupDir();

        Map<String, Object> item = new LinkedHashMap<>();
        Map<String, Object> json = new LinkedHashMap<>();
        item.put("item", EnderFoundation.MOD_ID +":" + in.getItem().toString());
        json.put("type", "minecraft:smelting");
        json.put("ingredient", serializeItem(in)); //serializeObject(in)
        json.put("result", serializeItem(result)); // vanilla jsons just have a string?
        json.put("experience", xp);
        json.put("cookingtime", cookTime);

        // janky I know but it works
        String suffix = "";//result.getItem()
                //.getHasSubtypes() ? "_" + result.getItemDamage() : "";
        File f = new File(RECIPE_DIR +"\\smelting", result.getItem().toString()+".json");
                //.getRegistryName().getResourcePath() + suffix + ".json");

        while (f.exists()) {
            suffix += "_alt";
            f = new File(RECIPE_DIR + "\\smelting", result.getItem().toString() + suffix + ".json");
        }

        try (FileWriter w = new FileWriter(f)) {

            GSON.toJson(json, w);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addShapedRecipe(ItemStack result, Object... components) {
        setupDir();
        Map<String, Object> json = new LinkedHashMap<>();
            json.put("type", "minecraft:crafting_shaped"); //isOreDict ? "forge:ore_shaped" :
        List<String> pattern = new ArrayList<>();
        int i = 0;

        for (String s: (ArrayList<String>) components[0]
             ) {
            pattern.add(s);
        }
        json.put("pattern", pattern);

        boolean isOreDict = false;
        Map<String, Map<String, Object>> key = new LinkedHashMap<>();
        Character curKey = null;
        ArrayList<RecipeUtils.CraftingKey> h = (ArrayList) components[1];
        for (; i < h.size(); i++) {
                if ((Character) h.get(i).getChar() instanceof Character) {
                    if (curKey != null)
                        throw new IllegalArgumentException("Provided two char keys in a row");
                    curKey = h.get(i).getChar();
                }
                    if (curKey == null)
                        throw new IllegalArgumentException("Providing object without a char key");


                    //if (h.get(i).getItemName() instanceof String)
                       // isOreDict = true;
                    key.put(Character.toString(curKey), serializeItem(h.get(i).getItemStack()));
                    curKey = null;
        }
        json.put("key", key);
        json.put("result", serializeItem(result));

        createJson(json, result.getItem().toString());

    }

    private static void addShapelessRecipe(ItemStack result, Object... components)
    {
        setupDir();

        // addShapelessRecipe(result, components);

        Map<String, Object> json = new LinkedHashMap<>();

        boolean isOreDict = false;
        json.put("type", isOreDict ? "forge:ore_shapeless" : "minecraft:crafting_shapeless");
        List<Map<String, Object>> ingredients = new ArrayList<>();
        for (Object o : components) {
            //if (o instanceof String)
                //isOreDict = true;
            ingredients.add(serializeItem(o));
        }
        json.put("ingredients", ingredients);

        json.put("result", serializeItem(result));

        createJson(json, result.getItem().toString());
    }
    public static void addBlockRecipe(EFContent.Blocks block) {
        Object[] recipe = new Object[2];
        ArrayList<String> pattern = new ArrayList<>();
        pattern.add("###");
        pattern.add("###");
        pattern.add("###");

        ArrayList<RecipeUtils.CraftingKey> key = new ArrayList<>();
        key.add(new RecipeUtils.CraftingKey('#', new ItemStack(block.ingotForm(block))));
        recipe[0] = pattern;
        recipe[1] = key;

        addShapedRecipe(new ItemStack(block), recipe);
    }
    public static void addIngotRecipe(EFContent.Ingots ingot) {
        Object[] recipe = new Object[2];
        ArrayList<String> pattern = new ArrayList<>();
        pattern.add("###");
        pattern.add("###");
        pattern.add("###");

        ArrayList<RecipeUtils.CraftingKey> key = new ArrayList<>();
        key.add(new RecipeUtils.CraftingKey('#', new ItemStack(ingot.nuggetForm(ingot))));
        recipe[0] = pattern;
        recipe[1] = key;
        addShapedRecipe(new ItemStack(ingot), recipe);

        Object[] recipe2 = new Object[1];
        recipe2[0] = ingot.blockForm(ingot).getStack();
        addShapelessRecipe(ingot.getStack(9), recipe2);
    }
    public static void addNuggetRecipe(EFContent.Nuggets nugget) {
        Object[] recipe = new Object[1];
        recipe[0] = nugget.ingotForm(nugget).getStack();
        addShapelessRecipe(nugget.getStack(9), recipe);
    }

    private static void createJson(Map<String, Object> json, String type) {
        //String type = result.getItem().toString();
        String dir ="";

        //TODO come up with better way to do this
        if(type.contains("block"))
            dir = "\\block";
        if(type.contains("ingot"))
            dir = "\\ingot";
        if(type.contains("nugget"))
            dir = "\\nugget";
        dir = RECIPE_DIR +dir;
        //File directory = new File((dir));
        File f = new File(dir, type+ ".json");
        //resonant_ingot + from_res

        String prefix ="";
        while (f.exists()) {
            prefix+="_alt";
            f = new File(dir, type+prefix+".json");
        }


        try (FileWriter w = new FileWriter(f)) {
            GSON.toJson(json, w);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static Map<String, Object> serializeItem(Object thing) {
        if (thing instanceof Item) {
            return serializeItem(new ItemStack((Item) thing));
        }
        if (thing instanceof Block) {
            return serializeItem(new ItemStack((Block) thing));
        }
        if (thing instanceof ItemStack) {
            ItemStack stack = (ItemStack) thing;
            Map<String, Object> ret = new HashMap<>();
            ret.put("item", EnderFoundation.MOD_ID + ":" + stack.getItem().toString());

            if (stack.getCount() > 1) {
                ret.put("count", stack.getCount());
            }

            if (stack.hasTag()) {
                ret.put("type", "minecraft:item_nbt");
                ret.put("nbt", stack.getTag().toString());
            }

            return ret;
        }
        if (thing instanceof String) {
            Map<String, Object> ret = new HashMap<>();
            USED_OD_NAMES.add((String) thing);
            ret.put("item", "#" + ((String) thing).toUpperCase(Locale.ROOT));
            return ret;
        }

        throw new IllegalArgumentException("Not a block, item, stack, or od name");
    }

    // Call this after you are done generating
    private static void generateConstants() {
        List<Map<String, Object>> json = new ArrayList<>();
        for (String s : USED_OD_NAMES) {
            Map<String, Object> entry = new HashMap<>();
            entry.put("name", s.toUpperCase(Locale.ROOT));
            entry.put("ingredient", ImmutableMap.of("type", "forge:ore_dict", "ore", s));
            json.add(entry);
        }

        try (FileWriter w = new FileWriter(new File(RECIPE_DIR, "_constants.json"))) {
            GSON.toJson(json, w);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
