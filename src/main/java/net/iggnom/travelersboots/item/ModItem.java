package net.iggnom.travelersboots.item;

import net.iggnom.travelersboots.TravelersBoots;
import net.iggnom.travelersboots.equipment.ModArmorMaterials;
import net.iggnom.travelersboots.equipment.TravelersBootsItem;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.equipment.ArmorType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItem {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(TravelersBoots.MOD_ID);

    public static final DeferredItem<TravelersBootsItem> TRAVELERS_BOOTS_ITEM = ITEMS.registerItem(
            "travelers_boots",
            properties -> new TravelersBootsItem(
                    properties.humanoidArmor(
                            ModArmorMaterials.TRAVELERS_BOOTS,
                            ArmorType.BOOTS
                    ).rarity(Rarity.RARE)
            )
    );

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}