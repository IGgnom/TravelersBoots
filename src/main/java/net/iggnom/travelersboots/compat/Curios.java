package net.iggnom.travelersboots.compat;

import net.iggnom.travelersboots.equipment.TravelersBootsItem;
import net.iggnom.travelersboots.item.ModItem;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;

import java.util.Optional;

public class Curios {
    private static void registerCapabilities(final RegisterCapabilitiesEvent evt) {
        evt.registerItem(CuriosCapability.ITEM, (stack, context) -> () -> stack, ModItem.TRAVELERS_BOOTS_ITEM);
    }

    public static void init(IEventBus modEventBus) {
        modEventBus.addListener(Curios::registerCapabilities);

        TravelersBootsItem.addIsWearingPredicate(player -> {
            Optional<ICuriosItemHandler> playerInv = CuriosApi.getCuriosInventory(player);
            if (playerInv.isPresent()) {
                ICurioStacksHandler handler = playerInv.get().getStacksHandler("travelers_boots").orElse(null);
                if (handler == null)
                    return false;
                for (int i = 0; i < handler.getSlots(); i++) {
                    if (handler.getStacks().getStackInSlot(i).getItem() instanceof TravelersBootsItem)
                        return true;
                }
            }
            return false;
        });
    }
}
