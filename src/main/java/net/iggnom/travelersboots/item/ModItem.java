package net.iggnom.travelersboots.item;

import net.iggnom.travelersboots.TravelersBoots;
import net.iggnom.travelersboots.equipment.ModArmorMaterials;
import net.iggnom.travelersboots.equipment.TravelersBootsItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItem {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, TravelersBoots.MOD_ID);

    public static final RegistryObject<Item> TRAVELERS_BOOTS_ITEM = ITEMS.register("travelers_boots",
            () -> new TravelersBootsItem(ModArmorMaterials.TRAVELERSBOOTS, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT).rarity(Rarity.EPIC)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
