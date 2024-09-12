package net.iggnom.travelersboots;

import net.iggnom.travelersboots.item.ModItem;
import net.minecraftforge.common.MinecraftForge;
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
    }
}
