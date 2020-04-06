package endergs.enderfoundation.events;

import endergs.enderfoundation.core.EFContent;
import endergs.enderfoundation.core.EnderFoundation;
import endergs.enderfoundation.crafting.recipe.RecipeUtils;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.server.network.ServerPlayerEntity;



import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class EnderRecipeHandler {


    public static void unlockTRRecipes(ServerPlayerEntity playerMP) {
        List<Recipe<?>> recipeList = new ArrayList<>();
        for (Recipe<?> recipe : RecipeUtils.getRecipes(playerMP.world, RecipeType.CRAFTING)) {
            if (isRecipeValid(recipe)) {
                recipeList.add(recipe);
            }
        }
        playerMP.unlockRecipes(recipeList);
    }

    private static boolean isRecipeValid(Recipe<?> recipe) {
        if (recipe.getId() == null) {
            return false;
        }
        if (!recipe.getId().getNamespace().equals(EnderFoundation.MOD_ID)) {
            return false;
        }
        return !recipe.getPreviewInputs().stream().anyMatch((Predicate<Ingredient>) ingredient -> ingredient.test(EFContent.Ingots.RESONANT.getStack()));
    }

}
