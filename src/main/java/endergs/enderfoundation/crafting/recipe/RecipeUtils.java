package endergs.enderfoundation.crafting.recipe;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.JsonOps;
import com.sun.jna.StringArray;
import endergs.enderfoundation.core.EnderFoundation;
import endergs.enderfoundation.utils.DefaultedListCollector;
import endergs.enderfoundation.utils.serialization.SerializationUtil;
import net.minecraft.datafixer.NbtOps;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.DefaultedList;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RecipeUtils {
   // @Nonnull
   // public static ItemStack getEmptyCell(int stackSize) {
   //     return DynamicCellItem.getEmptyCell(stackSize);
   // }

    /**
     *
     * Used to get the matching output of a recipe type that only has 1 input
     *
     */
    public static <T extends Recipe<?>> ItemStack getMatchingRecipes(World world, RecipeType<T> type, ItemStack input){
        return getRecipes(world, type).stream()
                .filter(recipe -> recipe.getPreviewInputs().size() == 1 && recipe.getPreviewInputs().get(0).test(input))
                .map(Recipe::getOutput)
                .findFirst()
                .orElse(ItemStack.EMPTY);
    }

    public static <T extends Recipe<?>> List<Recipe<?>> getRecipes(World world, RecipeType<T> type){
        return world.getRecipeManager().values().stream().filter(iRecipe -> iRecipe.getType() == type).collect(Collectors.toList());
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

    public static class CraftingKey {
        private char character;
        private ItemStack itemStack;

        public CraftingKey(char character, ItemStack itemStack) {
            this.character = character;
            this.itemStack = itemStack;
        }

        public char getChar() {
            return character;
        }

        public ItemStack getItemStack() {
            return itemStack;
        }

        public String getItemName() {
            return EnderFoundation.MOD_ID + ":"+ itemStack.getItem().getName().asString();
        }
    }

}
