package endergs.enderfoundation.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.JsonOps;
import endergs.enderfoundation.crafting.recipe.EnderRecipe;
import endergs.enderfoundation.crafting.recipe.EnderRecipeType;
import endergs.enderfoundation.mixin.common.AccessorRecipeManager;
import endergs.enderfoundation.utils.serialization.SerializationUtil;
import net.minecraft.datafixer.NbtOps;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.DefaultedList;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;



import java.util.ArrayList;
import java.util.List;

public class RecipeUtils {
    @SuppressWarnings("unchecked")
    public static <T extends EnderRecipe> List<T> getRecipes(World world, EnderRecipeType<?> type){
        AccessorRecipeManager accessorRecipeManager = (AccessorRecipeManager) world.getRecipeManager();
        //noinspection unchecked
        return new ArrayList<>(accessorRecipeManager.getAll(type).values());
    }

    public static DefaultedList<ItemStack> deserializeItems(JsonElement jsonObject){
        if(jsonObject.isJsonArray()){
            return SerializationUtil.stream(jsonObject.getAsJsonArray()).map(entry -> deserializeItem(entry.getAsJsonObject())).collect(DefaultedListCollector.toList());
        } else {
            return DefaultedList.copyOf(deserializeItem(jsonObject.getAsJsonObject()));
        }
    }

    private static ItemStack deserializeItem(JsonObject jsonObject){
        Identifier resourceLocation = new Identifier(JsonHelper.getString(jsonObject, "item"));
        Item item = Registry.ITEM.get(resourceLocation);
        if(item == Items.AIR){
            throw new IllegalStateException(resourceLocation + " did not exist");
        }
        int count = 1;
        if(jsonObject.has("count")){
            count = JsonHelper.getInt(jsonObject, "count");
        }
        ItemStack stack = new ItemStack(item, count);
        if(jsonObject.has("nbt")){
            CompoundTag tag = (CompoundTag) Dynamic.convert(JsonOps.INSTANCE, NbtOps.INSTANCE, jsonObject.get("nbt"));
            stack.setTag(tag);
        }
        return stack;
    }

}
