package net.iggnom.travelersboots;

import net.iggnom.travelersboots.equipment.TravelersBootsItem;
import net.iggnom.travelersboots.item.ModItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(TravelersBoots.MOD_ID)
public class TravelersBoots {
    public static final String MOD_ID = "travelersboots";

    public TravelersBoots() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModItem.register(modEventBus);
        MinecraftForge.EVENT_BUS.register(this);

        modEventBus.addListener(this::addCreative);

        registerEventHandlers();
    }

    private void registerEventHandlers() {
        MinecraftForge.EVENT_BUS.addListener(TravelersBootsItem::onPlayerTick);
        MinecraftForge.EVENT_BUS.addListener(TravelersBootsItem::onLivingJump);
        MinecraftForge.EVENT_BUS.addListener(TravelersBootsItem::onLivingFall);
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.COMBAT) {
            event.accept(ModItem.TRAVELERS_BOOTS_ITEM);
        }
    }
}
