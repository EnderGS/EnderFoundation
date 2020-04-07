package endergs.enderfoundation.item;

import endergs.enderfoundation.core.EFContent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Lazy;

import java.util.function.Supplier;

public enum EFArmorMaterial implements ArmorMaterial {

    RESONANT("resonant", 5, new int[]{3,5,4,2}, 15, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 2.0F, EFContent.Ingots.RESONANT.item );


    private final String name;
    private final int durabilityMultiplier;
    private final int[] armorValues;
    private final int enchantability;
    private final SoundEvent equipSound;
    private final float toughness;
    private final Lazy<Ingredient> repairIngredient;
    private final Item repairMaterial;

    EFArmorMaterial(String name, int durabilityMultiplier, int[] armorValueArr, int enchantability, SoundEvent soundEvent, float toughness, Item repairMaterial) {
        this.name = name;
        this.durabilityMultiplier = durabilityMultiplier;
        this.armorValues = armorValueArr;
        this.enchantability = enchantability;
        this.equipSound = soundEvent;
        this.toughness = toughness;
        this.repairMaterial = repairMaterial;
        this.repairIngredient = new Lazy(() -> Ingredient.ofItems(repairMaterial)); // We'll need this to be a Lazy type for later.

    }


    @Override
    public int getDurability(EquipmentSlot slot) {
        return 0;
    }

    @Override
    public int getProtectionAmount(EquipmentSlot slot) {
        return 0;
    }

    @Override
    public int getEnchantability() {
        return enchantability;
    }

    @Override
    public SoundEvent getEquipSound() {
        return equipSound;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.ofItems(repairMaterial);
    }

    public Item getRepairMaterial() {
        return repairMaterial;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public float getToughness() {
        return toughness;
    }
}
