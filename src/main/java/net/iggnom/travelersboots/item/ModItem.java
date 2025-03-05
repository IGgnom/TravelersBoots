package net.iggnom.travelersboots.item;

import net.iggnom.travelersboots.TravelersBoots;
import net.iggnom.travelersboots.equipment.ModArmorMaterials;
import net.iggnom.travelersboots.equipment.TravelersBootsItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItem {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(TravelersBoots.MOD_ID);

    public static final DeferredItem<Item> TRAVELERS_BOOTS_ITEM = ITEMS.register("travelers_boots",
            () -> new TravelersBootsItem(ModArmorMaterials.TRAVELERS_BOOTS, new Item.Properties().stacksTo(1).rarity(Rarity.EPIC)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
