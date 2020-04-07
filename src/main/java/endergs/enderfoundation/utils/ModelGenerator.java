package endergs.enderfoundation.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import endergs.enderfoundation.config.EnderFoundationConfig;
import endergs.enderfoundation.core.EFContent;
import endergs.enderfoundation.core.EnderFoundation;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;


public class ModelGenerator {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static File MODEL_DIR = null;
    private static final Set<String> USED_OD_NAMES = new TreeSet<>();


    //TODO rework for multi textured items
    public static void createItemModel(Item item) {
        Map<String, Object> json = new LinkedHashMap<>();
        Map<String, Object> layers = new LinkedHashMap<>();

        //REDO
        //String parent = "";
        //if()

        layers.put("layer0", EnderFoundation.MOD_ID+":item/" + item.toString());
        json.put("parent", "item/generated");
        json.put("textures", layers);

        createModelsJson(json, "item", item.toString());
    }

    public static void createBlockModel(Block block) {
        Map<String, Object> json = new LinkedHashMap<>();
        Map<String, Object> textures = new LinkedHashMap<>();

        Map<String, Object> json2 = new LinkedHashMap<>();

        //REDO
        String parent = "";
        //if()

            textures.put("all", EnderFoundation.MOD_ID+":block/" + block.asItem().toString());
        json.put("parent", "block/cube_all");
        json.put("textures", textures);

        json2.put("parent", EnderFoundation.MOD_ID+":block/" + block.asItem().toString());

        createModelsJson(json, "block", block.asItem().toString());
        createModelsJson(json2, "item", block.asItem().toString());
    }

    private static void createModelsJson(Map<String, Object> json, String type, String object) {

        //String type = result.getItem().toString();
        String dir = EnderFoundationConfig.modelsFolder;

        if(type.equals("item")) dir+= "\\item";
        if(type.equals("block")) dir+= "\\block";
        //File directory = new File((dir));
        File f = new File(dir);
        //resonant_ingot + from_res

        String prefix = "";

        f = new File(dir, object + prefix + ".json");

        while (f.exists()) {
            prefix += "_alt";
            f = new File(dir, object + prefix + ".json");
        }


        try (FileWriter w = new FileWriter(f)) {
            GSON.toJson(json, w);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
