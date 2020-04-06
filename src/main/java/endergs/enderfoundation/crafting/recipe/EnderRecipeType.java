package endergs.enderfoundation.crafting.recipe;

import com.google.gson.JsonObject;
import endergs.enderfoundation.core.EnderFoundation;
import endergs.enderfoundation.crafting.ConditionManager;
import endergs.enderfoundation.utils.serialization.SerializationUtil;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.PacketByteBuf;


import java.lang.reflect.InvocationTargetException;

public class EnderRecipeType<R extends EnderRecipe> implements RecipeType, RecipeSerializer {

    private final Class<R> clazz;

    private final Identifier typeId;

    public EnderRecipeType(Class<R> clazz, Identifier typeId) {
        this.clazz = clazz;
        this.typeId = typeId;
    }

    @Override
    public R read(Identifier recipeId, JsonObject json) {
        Identifier type = new Identifier(JsonHelper.getString(json, "type"));
        if (!type.equals(typeId)) {
            throw new RuntimeException("RebornRecipe type not supported!");
        }

        R recipe = newRecipe(recipeId);

        try{
            if(!ConditionManager.shouldLoadRecipe(json)) {
                recipe.makeDummy();
                return recipe;
            }

            recipe.deserialize(json);
        } catch (Throwable t){
            t.printStackTrace();
            EnderFoundation.LOGGER.error("Failed to read recipe: " + recipeId);
        }
        return recipe;

    }

    public JsonObject toJson(R recipe) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("type", typeId.toString());

        recipe.serialize(jsonObject);

        return jsonObject;
    }

    public R fromJson(Identifier recipeType, JsonObject json) {
        return read(recipeType, json);
    }

    R newRecipe(Identifier recipeId) {
        try {
            return clazz.getConstructor(EnderRecipeType.class, Identifier.class).newInstance(this, recipeId);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException("Failed to create new recipe class for " + recipeId + " using " + clazz.getName());
        }
    }

    @Override
    public R read(Identifier recipeId, PacketByteBuf buffer) {
        String input = buffer.readString(buffer.readInt());
        R r = read(recipeId, SerializationUtil.GSON_FLAT.fromJson(input, JsonObject.class));
        r.deserialize(buffer);
        return r;
    }

    @Override
    public void write(PacketByteBuf buffer, Recipe recipe) {
        JsonObject jsonObject = toJson((R) recipe);
        String output = SerializationUtil.GSON_FLAT.toJson(jsonObject);
        buffer.writeInt(output.length());
        buffer.writeString(output);
        ((R) recipe).serialize(buffer);
    }

    public Identifier getName() {
        return typeId;
    }

   // public List<R> getRecipes(World world) {
   //     return RecipeUtils.getRecipes(world, this);
   // }

    public Class<R> getRecipeClass() {
        return clazz;
    }

}
