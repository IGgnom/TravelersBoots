package net.iggnom.travelersboots;

import net.iggnom.travelersboots.compat.Curios;
import net.iggnom.travelersboots.compat.Mods;
import net.iggnom.travelersboots.equipment.TravelersBootsItem;
import net.iggnom.travelersboots.item.ModItem;
import net.minecraft.world.item.CreativeModeTabs;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

@Mod(TravelersBoots.MOD_ID)
public class TravelersBoots {
    public static final String MOD_ID = "travelersboots";

    public TravelersBoots(IEventBus modEventBus, ModContainer modContainer) {
        ModItem.register(modEventBus);
        modEventBus.addListener(this::addCreative);

        registerEventHandlers();

        Mods.CURIOS.executeIfInstalled(() -> () -> Curios.init(modEventBus));
    }

    private void registerEventHandlers() {
        NeoForge.EVENT_BUS.addListener(TravelersBootsItem::onPlayerTickPre);
        NeoForge.EVENT_BUS.addListener(TravelersBootsItem::onPlayerTickPost);
        NeoForge.EVENT_BUS.addListener(TravelersBootsItem::onLivingJump);
        NeoForge.EVENT_BUS.addListener(TravelersBootsItem::onLivingFall);
    }

    private void addCreative(final BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.COMBAT) {
            event.accept(ModItem.TRAVELERS_BOOTS_ITEM);
        }
    }
}
