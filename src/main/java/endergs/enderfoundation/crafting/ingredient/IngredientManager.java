package endergs.enderfoundation.crafting.ingredient;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import jdk.internal.jline.internal.Nullable;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;


import java.util.HashMap;
import java.util.function.Function;

public class IngredientManager {

    public static final Identifier STACK_RECIPE_TYPE = new Identifier("reborncore", "stack");
    public static final Identifier FLUID_RECIPE_TYPE = new Identifier("reborncore", "fluid");
    public static final Identifier TAG_RECIPE_TYPE = new Identifier("reborncore", "tag");
    public static final Identifier WRAPPED_RECIPE_TYPE = new Identifier("reborncore", "wrapped");

    private static final HashMap<Identifier, Function<JsonObject, EnderIngredient>> recipeTypes = new HashMap<>();

    public static void setup(){
        recipeTypes.put(STACK_RECIPE_TYPE, StackIngredient::deserialize);
        //recipeTypes.put(FLUID_RECIPE_TYPE, FluidIngredient::deserialize);
        //recipeTypes.put(TAG_RECIPE_TYPE, TagIngredient::deserialize);
        //recipeTypes.put(WRAPPED_RECIPE_TYPE, WrappedIngredient::deserialize);
    }

    public static EnderIngredient deserialize(@Nullable JsonElement jsonElement) {
        if(jsonElement == null || !jsonElement.isJsonObject()) {
            throw new JsonParseException("ingredient must be a json object");
        }

        JsonObject json = jsonElement.getAsJsonObject();

        Identifier recipeTypeIdent = STACK_RECIPE_TYPE;
        String memberName = json.getAsString();
        //TODO find a better way to do this.
        switch (memberName) {
            case "fluid":
             recipeTypeIdent= FLUID_RECIPE_TYPE;
             break;
            case "tag":
                recipeTypeIdent = TAG_RECIPE_TYPE;
                break;
            case "wrapped":
                recipeTypeIdent = WRAPPED_RECIPE_TYPE;
                break;
            case "type":
                recipeTypeIdent = new Identifier(JsonHelper.getString(json, "type"));
                break;

        }


        Function<JsonObject, EnderIngredient> recipeTypeFunction = recipeTypes.get(recipeTypeIdent);
        if(recipeTypeFunction == null) {
            throw new JsonParseException("No recipe type found for " + recipeTypeIdent.toString());
        }
        return recipeTypeFunction.apply(json);
    }

}
