package endergs.enderfoundation.utils;

import endergs.enderfoundation.core.EnderFoundation;
import endergs.enderfoundation.core.EnderRegistry;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Block.Settings;
import net.minecraft.block.Material;
import net.minecraft.block.MaterialColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.DefaultedList;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class InitUtils {
    public static <I extends Item> I setup(I item, String name) {
        EnderRegistry.registerIdent(item, new Identifier(EnderFoundation.MOD_ID, name));
        return item;
    }

    public static <B extends Block> B setup(B block, String name) {
        EnderRegistry.registerIdent(block, new Identifier(EnderFoundation.MOD_ID, name));
        return block;
    }

    public static SoundEvent setup(String name) {
        Identifier identifier = new Identifier(EnderFoundation.MOD_ID, name);
        return Registry.register(Registry.SOUND_EVENT, identifier, new SoundEvent(identifier));
    }


    public static Settings setupRubberBlockSettings(boolean noCollision, float hardness, float resistance) {

        FabricBlockSettings settings = FabricBlockSettings.of(Material.WOOD, MaterialColor.SPRUCE);
        settings.strength(hardness, resistance);
        settings.sounds(BlockSoundGroup.WOOD);
        if (noCollision) {
            settings.noCollision();
        }

        return settings.build();
    }

    public static Settings setupRubberBlockSettings(float hardness, float resistance) {
        return setupRubberBlockSettings(false, hardness, resistance);
    }
}
