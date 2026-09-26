package net.iggnom.travelersboots.equipment;

import net.iggnom.travelersboots.TravelersBoots;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.neoforged.neoforge.common.Tags;

import java.util.Map;

public class ModArmorMaterials {
    public static final ResourceKey<? extends Registry<EquipmentAsset>> ROOT_ID = ResourceKey.createRegistryKey(Identifier.withDefaultNamespace("equipment_asset"));

    public static final ResourceKey<EquipmentAsset> TRAVELERS_BOOTS_KEY = ResourceKey.create(ROOT_ID, Identifier.fromNamespaceAndPath(TravelersBoots.MOD_ID, "travelers_boots"));

    public static final ArmorMaterial TRAVELERS_BOOTS = new ArmorMaterial(
            429,
            Map.of(ArmorType.BOOTS, 3),
            10,
            SoundEvents.ARMOR_EQUIP_LEATHER,
            2f,
            0.0f,
            Tags.Items.GEMS_DIAMOND,
            TRAVELERS_BOOTS_KEY
    );
}