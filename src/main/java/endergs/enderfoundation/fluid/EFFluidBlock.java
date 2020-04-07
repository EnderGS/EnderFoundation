package endergs.enderfoundation.fluid;

import net.minecraft.block.Block;
import net.minecraft.block.FluidBlock;

public class EFFluidBlock extends FluidBlock {

    private final EFBaseFluid fluid;

    public EFFluidBlock(EFBaseFluid fluid, Block.Settings properties) {
        super(fluid, properties);
        this.fluid = fluid;
    }

    public EFBaseFluid getFluid() {
        return fluid;
    }
}
