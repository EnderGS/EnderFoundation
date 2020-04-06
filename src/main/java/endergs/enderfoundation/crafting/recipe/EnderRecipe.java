package endergs.enderfoundation.crafting.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.JsonOps;
import endergs.enderfoundation.crafting.CustomOutputRecipe;
import endergs.enderfoundation.crafting.ingredient.DummyIngredient;
import endergs.enderfoundation.crafting.ingredient.EnderIngredient;
import endergs.enderfoundation.crafting.ingredient.IngredientManager;
import endergs.enderfoundation.crafting.recipe.EnderRecipeType;
import endergs.enderfoundation.utils.DefaultedListCollector;
import endergs.enderfoundation.utils.serialization.SerializationUtil;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.datafixer.NbtOps;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.util.DefaultedList;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.PacketByteBuf;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.apache.commons.lang3.Validate;





import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static endergs.enderfoundation.crafting.recipe.RecipeUtils.deserializeItems;

public class EnderRecipe implements Recipe<Inventory>, CustomOutputRecipe {

    private final EnderRecipeType<?> type;
    private final Identifier name;

    private DefaultedList<EnderIngredient> ingredients = DefaultedList.of();
    private DefaultedList<ItemStack> outputs = DefaultedList.of();
    protected int power;
    protected int time;

    protected boolean dummy = false;

    public EnderRecipe(EnderRecipeType<?> type, Identifier name) {
        this.type = type;
        this.name = name;
    }

    public EnderRecipe(EnderRecipeType<?> type, Identifier name, DefaultedList<EnderIngredient> ingredients, DefaultedList<ItemStack> outputs, int power, int time) {
        this(type, name);
        this.ingredients = ingredients;
        this.outputs = outputs;
        this.power = power;
        this.time = time;
    }

    public void deserialize(JsonObject jsonObject){
        if(jsonObject.has("dummy")){
            makeDummy();
            return;
        }

        //Crash if the recipe has all ready been deserialized
        Validate.isTrue(ingredients.isEmpty());

        power = JsonHelper.getInt(jsonObject, "power");
        time = JsonHelper.getInt(jsonObject, "time");

        ingredients = SerializationUtil.stream(JsonHelper.getArray(jsonObject, "ingredients"))
                .map(IngredientManager::deserialize)
                .collect(DefaultedListCollector.toList());

        JsonArray resultsJson = JsonHelper.getArray(jsonObject, "results");
        outputs = deserializeItems(resultsJson);
    }

    public void serialize(JsonObject jsonObject) {
        if(isDummy()){
            jsonObject.addProperty("dummy", true);
            return;
        }
        jsonObject.addProperty("power", power);
        jsonObject.addProperty("time", time);

        JsonArray ingredientsArray = new JsonArray();
        getEnderIngredients().stream().map(EnderIngredient::witeToJson).forEach(ingredientsArray::add);
        jsonObject.add("ingredients", ingredientsArray);

        JsonArray resultsArray = new JsonArray();
        for(ItemStack stack : outputs){
            JsonObject stackObject = new JsonObject();
            stackObject.addProperty("item", Registry.ITEM.getId(stack.getItem()).toString());
            if(stack.getCount() > 1){
                stackObject.addProperty("count", stack.getCount());
            }
            if(stack.hasTag()){
                stackObject.add("nbt", Dynamic.convert(NbtOps.INSTANCE, JsonOps.INSTANCE, stack.getTag()));
            }
            resultsArray.add(stackObject);
        }
        jsonObject.add("results", resultsArray);
    }

    public void serialize(PacketByteBuf byteBuf) {

    }

    public void deserialize(PacketByteBuf byteBuf) {

    }

    @Override
    public Identifier getId() {
        return name;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return type;
    }

    @Override
    public net.minecraft.recipe.RecipeType<?> getType() {
        return type;
    }

    public EnderRecipeType<?> getRebornRecipeType(){
        return type;
    }

    // use the RebornIngredient version to ensure stack sizes are checked
    //@Deprecated
   // @Override
   // public DefaultedList<Ingredient> getPreviewInputs() {
   //     return ingredients.stream().map(RebornIngredient::getPreview).collect(DefaultedListCollector.toList());
   // }

    public DefaultedList<EnderIngredient> getEnderIngredients() {
        return ingredients;
    }

    public List<ItemStack> getOutputs() {
        return Collections.unmodifiableList(outputs);
    }

    public int getPower() {
        return power;
    }

    public int getTime() {
        return time;
    }

    /**
     * @param blockEntity the blockEntity that is doing the crafting
     * @return if true the recipe will craft, if false it will not
     */
   /* public boolean canCraft(BlockEntity blockEntity) {
        if(isDummy()) {
            return false;
        }
        if(blockEntity instanceof IRecipeCrafterProvider){
            if(!((IRecipeCrafterProvider) blockEntity).canCraft(this)){
                return false;
            }
        }
        return true;
    }*/

    /**
     * @param blockEntity the blockEntity that is doing the crafting
     * @return return true if fluid was taken and should craft
     */
    public boolean onCraft(BlockEntity blockEntity){
        return true;
    }

    //Done as our recipes do not support these functions, hopefully nothing blidly calls them

    @Deprecated
    @Override
    public boolean matches(Inventory inv, World worldIn) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    @Override
    public ItemStack craft(Inventory inv) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    @Override
    public boolean fits(int width, int height) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    @Override
    public ItemStack getOutput() {
        throw new UnsupportedOperationException();
    }

    @Override
    public DefaultedList<ItemStack> getRemainingStacks(Inventory p_179532_1_) {
        throw new UnsupportedOperationException();
    }

    //Done to try and stop the table from loading it
    @Override
    public boolean isIgnoredInRecipeBook() {
        return true;
    }

    private boolean isDummy() {
        return dummy;
    }

    void makeDummy() {
        this.ingredients.add(new DummyIngredient());
        this.dummy = true;
    }

    @Override
    public Collection<Item> getOutputItems() {
        List<Item> items = new ArrayList<>();
        for (ItemStack stack : outputs) {
            items.add(stack.getItem());
        }
        return items;
    }
}
