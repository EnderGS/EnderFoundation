package endergs.enderfoundation.core;


import net.minecraft.block.Block;
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

       /* public static void registerBlock(Block block, Item.Settings builder, Identifier name) {

            BlockItem itemBlock = new BlockItem(block, builder);
            Registry.register(Registry.ITEM, name, itemBlock);
        }

        public static void registerBlock(net.minecraft.block.Block block, Class<? extends BlockItem> itemclass, Identifier name) {
            Registry.register(Registry.BLOCK, name, block);
            try {
                BlockItem itemBlock = itemclass.getConstructor(Block.class).newInstance(block);
                Registry.register(Registry.ITEM, name, itemBlock);
            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }



        public static void registerBlock(Block block, Class<? extends BlockItem> itemclass){
            Validate.isTrue(objIdentMap.containsKey(block));
            //registerBlock(block, itemclass, objIdentMap.get(block));
        }

        public static void registerBlock(Block block, BlockItem itemBlock, Identifier name) {
            Registry.register(Registry.BLOCK, name, block);
           //Registry.register(Registry.ITEM, name, itemBlock);
        }*/

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
