package endergs.enderfoundation.core;

import endergs.enderfoundation.utils.InitUtils;
import jdk.internal.jline.internal.Nullable;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.OreBlock;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;

import java.util.Locale;

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



    public enum Ores  {
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

        public Ingots ingotForm(Blocks value) {

            return Ingots.valueOf(value.toString());
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

        public Blocks blockForm(Ingots value) {

            return Blocks.valueOf(value.toString());
        }
        public Nuggets nuggetForm(Ingots value) {
            return Nuggets.valueOf(value.toString());
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

        public Ingots ingotForm(Nuggets value) {

            return Ingots.valueOf(value.toString());
        }
    }


    //public static EntityType<EntityNukePrimed> ENTITY_NUKE;
}
