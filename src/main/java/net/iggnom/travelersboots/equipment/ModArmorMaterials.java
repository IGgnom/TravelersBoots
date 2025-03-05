package net.iggnom.travelersboots.equipment;

import net.iggnom.travelersboots.TravelersBoots;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.EnumMap;
import java.util.List;

public class ModArmorMaterials {
    public static Holder<ArmorMaterial> TRAVELERS_BOOTS = register();

    private static Holder<ArmorMaterial> register() {
        ResourceLocation location = ResourceLocation.fromNamespaceAndPath(TravelersBoots.MOD_ID, "travelers_boots");
        ArmorMaterial armorMaterial = new ArmorMaterial(
                Util.make(new EnumMap<>(ArmorItem.Type.class), attribute -> attribute.put(ArmorItem.Type.BOOTS, 3)),
                10,
                SoundEvents.ARMOR_EQUIP_LEATHER,
                () -> Ingredient.of(Items.DIAMOND),
                List.of(new ArmorMaterial.Layer(location)),
                2.0f, 0.0f);
        return Registry.registerForHolder(BuiltInRegistries.ARMOR_MATERIAL, location, armorMaterial);
    }
}
