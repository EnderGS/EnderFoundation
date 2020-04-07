package endergs.enderfoundation.core;


import net.minecraft.block.Block;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.apache.commons.lang3.Validate;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class EnderRegistry {
        //public static LootManager.InnerPool lp = new LootManager.InnerPool();

        //Yeah, this is horrible
        private static final HashMap<Object, Identifier> objIdentMap = new HashMap<>();


       public static void registerBlock(Block block) {
            Validate.isTrue(objIdentMap.containsKey(block));
            //registerBlock(block, itemGroup, objIdentMap.get(block));
            Registry.register(Registry.BLOCK, objIdentMap.get(block), block);

            //Register Block Item
           //Registry.register(Registry.BLOCK, objIdentMap.get(block), block);
           try {
               BlockItem itemBlock = new BlockItem(block, new Item.Settings().group(EnderFoundation.ITEMGROUP));
               Registry.register(Registry.ITEM, objIdentMap.get(block), itemBlock);
           }catch (Exception e){

               }

       }
        public static void registerItem(Item item){
            Validate.isTrue(objIdentMap.containsKey(item));
            Registry.register(Registry.ITEM, objIdentMap.get(item), item);
        }

        public static void registerFluid(Fluid fluid) {
            Validate.isTrue(objIdentMap.containsKey(fluid));
            Registry.register(Registry.FLUID, objIdentMap.get(fluid), fluid);
        }


        public static void registerIdent(Object object, Identifier identifier){
            objIdentMap.put(object, identifier);
        }

        //eg: RebornRegistry.addLoot(Items.NETHER_STAR, 0.95, LootTableList.CHESTS_VILLAGE_BLACKSMITH);
        //eg: RebornRegistry.addLoot(Items.DIAMOND, 1.95, LootTableList.ENTITIES_COW);

        public static void addLoot(Item item, double chance, Identifier list) {
            //	lp.addItem(LootManager.createLootEntry(item, chance, list));
        }

        public static void addLoot(Item item, int minSize, int maxSize, double chance, Identifier list) {
            //	lp.addItem(LootManager.createLootEntry(item, minSize, maxSize, chance, list));
        }

}
