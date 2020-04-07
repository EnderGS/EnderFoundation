package endergs.enderfoundation.core;

import endergs.enderfoundation.fluid.EFBaseFluid;
import endergs.enderfoundation.fluid.EFFluidBlock;
import endergs.enderfoundation.fluid.WasteFluid;
import endergs.enderfoundation.item.EFArmorMaterial;
import endergs.enderfoundation.utils.InitUtils;
import jdk.internal.jline.internal.Nullable;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.block.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.fluid.BaseFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.*;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Locale;

import static endergs.enderfoundation.fluid.EFFluidRenderManager.setupFluidRendering;

public class EFContent {



    // Armor
    // Tools



    // Other
   // public static DynamicCellItem CELL;

    //Quantum Suit
   // public static QuantumSuitItem QUANTUM_HELMET;
    //public static QuantumSuitItem QUANTUM_CHESTPLATE;
   // public static QuantumSuitItem QUANTUM_LEGGINGS;
    //public static QuantumSuitItem QUANTUM_BOOTS;

    // Gem armor & tools
    @Nullable
    public static Item BRONZE_SWORD;
    @Nullable
    public static Item BRONZE_PICKAXE;
    @Nullable
    public static Item BRONZE_SPADE;
    @Nullable
    public static Item BRONZE_AXE;
    @Nullable
    public static Item BRONZE_HOE;
    @Nullable
    public static Item BRONZE_HELMET;
    @Nullable
    public static Item BRONZE_CHESTPLATE;
    @Nullable
    public static Item BRONZE_LEGGINGS;
    @Nullable
    public static Item BRONZE_BOOTS;

    public enum Armor implements ItemConvertible {
         RESONANT(EFArmorMaterial.RESONANT);

         private ArmorItem[] set;
         private EFArmorMaterial armorMaterial;


        Armor(ArmorMaterial armorMaterial) {
            this.armorMaterial = (EFArmorMaterial) armorMaterial;
            set = new ArmorItem[4];
            set[0] = new ArmorItem(armorMaterial, EquipmentSlot.HEAD, (new Item.Settings().group(EnderFoundation.ITEMGROUP)));
            set[1] = new ArmorItem(armorMaterial, EquipmentSlot.CHEST, new Item.Settings().group(EnderFoundation.ITEMGROUP));
            set[2] = new ArmorItem(armorMaterial, EquipmentSlot.LEGS, new Item.Settings().group(EnderFoundation.ITEMGROUP));
            set[3] = new ArmorItem(armorMaterial, EquipmentSlot.FEET, new Item.Settings().group(EnderFoundation.ITEMGROUP));
            for (int i=0; i< set.length; i++ ) {
                InitUtils.setup(set[i], this.toString().toLowerCase(Locale.ROOT) + "_" + set[i].getSlotType().toString().toLowerCase(Locale.ROOT)+"_armor");
            }

        }


        public Item[] getArmor() {

             return set;
        }

        public Item armorMaterial() {

            return armorMaterial.getRepairMaterial();
        }

        public enum ArmorPieces {
            HEAD(EquipmentSlot.HEAD),
            CHEST(EquipmentSlot.CHEST),
            LEGS(EquipmentSlot.LEGS),
            FEET(EquipmentSlot.FEET);

            private EquipmentSlot equipmentSlot;

            ArmorPieces(EquipmentSlot equipmentSlot) {
                this.equipmentSlot = equipmentSlot;
            }
        }

        @Override
        public Item asItem() {
            return null;
        }

       // public ItemStack getStack() {
       //     return new ItemStack(block.asItem());
       // }

       // public ItemStack getStack(int amount) {
       //     return new ItemStack(block.asItem(), amount);
       // }
    }

    public enum Ores implements ItemConvertible {
        RESONANT(6, 10, 10, 60);

        public final String name;
        public final Block block;
        public final int veinSize;
        public final int veinsPerChunk;
        public final int minY;
        public final int maxY;

        Ores(int veinSize, int veinsPerChunk, int minY, int maxY) {
            name = this.toString().toLowerCase(Locale.ROOT);
            block = new OreBlock(FabricBlockSettings.of(Material.STONE).strength(2f, 2f).build());
            this.veinSize = veinSize;
            this.veinsPerChunk = veinsPerChunk;
            this.minY = minY;
            this.maxY = maxY;
            InitUtils.setup(block, name + "_ore");
        }

        public ItemStack getStack() {
            return new ItemStack(block.asItem());
        }

        public ItemStack getStack(int amount) {
            return new ItemStack(block.asItem(), amount);
        }

        public Item asItem() {
            return block.asItem();
        }

    }

    public enum Blocks implements ItemConvertible {
        RESONANT;

        public final String name;
        public final Block block;

       Blocks() {
            name = this.toString().toLowerCase(Locale.ROOT);
           block = new Block(FabricBlockSettings.of(Material.STONE).strength(2f, 2f).build());
           InitUtils.setup(block, name + "_block");
       }

        public ItemStack getStack() {
            return new ItemStack(block.asItem());
        }

        public ItemStack getStack(int amount) {
            return new ItemStack(block.asItem(), amount);
        }

        @Override
        public Item asItem() {
            return block.asItem();
        }

        public Ingots ingotForm() {

            return Ingots.valueOf(block.toString());
        }
    }

    public enum Ingots implements ItemConvertible {
       RESONANT;

        public final String name;
        public final Item item;

        Ingots() {
            name = this.toString().toLowerCase(Locale.ROOT);
            item = new Item(new Item.Settings().group(EnderFoundation.ITEMGROUP));
            InitUtils.setup(item, name + "_ingot");
        }

        public ItemStack getStack() {
            return new ItemStack(item);
        }

        public ItemStack getStack(int amount) {
            return new ItemStack(item, amount);
        }

        @Override
        public Item asItem() {
            return item;
        }

        public Blocks blockForm() {

            return Blocks.valueOf(item.toString());
        }

        public Dusts dustForm() { return Dusts.valueOf(item.toString()); }
        public Nuggets nuggetForm() {
            return Nuggets.valueOf(item.toString());
        }
        public Ores oreForm() {
            return Ores.valueOf(item.toString());
        }
    }
    public enum Dusts implements ItemConvertible {
        RESONANT;

        public final String name;
        public final Item item;

        Dusts() {
            name = this.toString().toLowerCase(Locale.ROOT);
            item = new Item(new Item.Settings().group(EnderFoundation.ITEMGROUP));
            InitUtils.setup(item, name + "_ingot");
        }

        public ItemStack getStack() {
            return new ItemStack(item);
        }

        public ItemStack getStack(int amount) {
            return new ItemStack(item, amount);
        }

        @Override
        public Item asItem() {
            return item;
        }

        public Ingots ingotForm() {
            return Ingots.valueOf(item.toString());
        }

    }


    public enum Nuggets implements ItemConvertible {
        RESONANT;

        public final String name;
        public final Item item;

        Nuggets() {
            name = this.toString().toLowerCase(Locale.ROOT);
            item = new Item(new Item.Settings().group(EnderFoundation.ITEMGROUP));
            InitUtils.setup(item, name + "_nugget");
        }

        public ItemStack getStack() {
            return new ItemStack(item);
        }

        public ItemStack getStack(int amount) {
            return new ItemStack(item, amount);
        }

        @Override
        public Item asItem() {
            return item;
        }

        public Ingots ingotForm() {

            return Ingots.valueOf(item.toString());
        }
    }

    //public static EntityType<EntityNukePrimed> ENTITY_NUKE;
}
