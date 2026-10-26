package net.iggnom.travelersboots.compat.curios;

import net.iggnom.travelersboots.TravelersBoots;
import net.iggnom.travelersboots.compat.Mods;
import net.iggnom.travelersboots.item.ModItem;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;

@EventBusSubscriber(modid = TravelersBoots.MOD_ID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class CuriosClient {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        if (!Mods.CURIOS.isLoaded())
            return;
        CuriosRendererRegistry.register(ModItem.TRAVELERS_BOOTS_ITEM.get(), BootsCurioRenderer::new);
    }
}
