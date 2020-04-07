package endergs.enderfoundation.fluid;

//import endergs.enderfoundation.core.EFContent;
import endergs.enderfoundation.utils.InitUtils;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;

import net.minecraft.item.Items;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;

public abstract class WasteFluid extends EFBaseFluid {
    public static EFBaseFluid still = new Still();
    public static EFBaseFluid flowing = new Flowing();
    public static BucketItem fluidBucket = new BucketItem(still, new Item.Settings().recipeRemainder(Items.BUCKET).maxCount(1));
    public static Block fluidBlock = new EFFluidBlock(still, FabricBlockSettings.copy(net.minecraft.block.Blocks.WATER).build());
    public static final String name = "waste";

    public static void registerWaste() {
        InitUtils.setup(still, flowing, fluidBucket, fluidBlock, name);
    }

    @Override
    public Fluid getFlowing() {
        return flowing;
    }

    @Override
    public Fluid getStill() {
        return still;
    }

    @Override
    public Item getBucketItem() {
        return fluidBucket;
    }

    @Override
    protected BlockState toBlockState(FluidState state) {
        return fluidBlock.getDefaultState().with(Properties.LEVEL_15, method_15741(state));
    }

    public static class Flowing extends WasteFluid
    {
        @Override
        protected void appendProperties(StateManager.Builder<Fluid, FluidState> builder)
        {
            super.appendProperties(builder);
            builder.add(LEVEL);
        }

        @Override
        public int getLevel(FluidState fluidState)
        {
            return fluidState.get(LEVEL);
        }

        @Override
        public boolean isStill(FluidState fluidState)
        {
            return false;
        }
    }

    public static class Still extends WasteFluid
    {
        @Override
        public int getLevel(FluidState fluidState)
        {
            return 8;
        }

        @Override
        public boolean isStill(FluidState fluidState)
        {
            return true;
        }
    }

}
