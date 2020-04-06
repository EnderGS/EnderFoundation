package endergs.enderfoundation.core;

import com.mojang.datafixers.util.Pair;
import jdk.internal.jline.internal.Nullable;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.ModelBakeSettings;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.render.model.UnbakedModel;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.function.Function;
public class EnderFoundationClient {

    public static final Item RESONANT_INGOT = new Item(new Item.Settings().group(ItemGroup.MISC));

    public void onInitializeClient() {
        //TODO: Initializer

        //Registry.register(Registry.ITEM, new Identifier("tutorial", "fabric_item"), RESONANT_INGOT);
        //Registry.register(Registry.ITEM, new Identifier("enderfoundation", "resonant_ingot"), RESONANT_INGOT);


        ModelLoadingRegistry.INSTANCE.registerAppender((manager, out) -> {
            out.accept(new ModelIdentifier(new Identifier(EnderFoundation.MOD_ID, "cell_base"), "inventory"));
            out.accept(new ModelIdentifier(new Identifier(EnderFoundation.MOD_ID, "cell_fluid"), "inventory"));
            out.accept(new ModelIdentifier(new Identifier(EnderFoundation.MOD_ID, "cell_background"), "inventory"));
            out.accept(new ModelIdentifier(new Identifier(EnderFoundation.MOD_ID, "cell_glass"), "inventory"));

            out.accept(new ModelIdentifier(new Identifier(EnderFoundation.MOD_ID, "bucket_base"), "inventory"));
            out.accept(new ModelIdentifier(new Identifier(EnderFoundation.MOD_ID, "bucket_fluid"), "inventory"));
            out.accept(new ModelIdentifier(new Identifier(EnderFoundation.MOD_ID, "bucket_background"), "inventory"));
        });

        ModelLoadingRegistry.INSTANCE.registerVariantProvider(resourceManager -> (modelIdentifier, modelProviderContext) -> {
            if (modelIdentifier.getNamespace().equals(EnderFoundation.MOD_ID)) {
                if (modelIdentifier.getPath().equals("cell")) {
                    return new UnbakedModel() {
                        @Override
                        public Collection<Identifier> getModelDependencies() {
                            return Collections.emptyList();
                        }

                        @Override
                        public Collection<SpriteIdentifier> getTextureDependencies(Function<Identifier, UnbakedModel> unbakedModelGetter, Set<Pair<String, String>> unresolvedTextureReferences) {
                            return Collections.emptyList();
                        }

                        @Override
                        public BakedModel bake(ModelLoader loader, Function<SpriteIdentifier, Sprite> textureGetter, ModelBakeSettings rotationContainer, Identifier modelId) {
                            return null;
                        }


                    };
                }
            }
            return null;
        });
    }

}