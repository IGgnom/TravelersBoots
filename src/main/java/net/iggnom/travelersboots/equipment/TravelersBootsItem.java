package net.iggnom.travelersboots.equipment;

import net.iggnom.travelersboots.TravelersBoots;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import org.jetbrains.annotations.NotNull;

import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.living.LivingFallEvent;
import net.neoforged.neoforge.event.entity.living.LivingEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.List;

public class TravelersBootsItem extends ArmorItem {
    public TravelersBootsItem(Holder<ArmorMaterial> material, Properties properties) {
        super(material, Type.BOOTS, properties);
        NeoForge.EVENT_BUS.addListener(this::onLivingJump);
        NeoForge.EVENT_BUS.addListener(this::onLivingFall);
        NeoForge.EVENT_BUS.addListener(this::onPlayerTickPost);
        NeoForge.EVENT_BUS.addListener(this::onPlayerTickPre);
    }

    public static final AttributeModifier STEP_HEIGHT_MODIFIER = new AttributeModifier(ResourceLocation.fromNamespaceAndPath(TravelersBoots.MOD_ID,
            "travelersbootsstepassist"), 0.5f, AttributeModifier.Operation.ADD_VALUE);

    public void onPlayerTickPre(PlayerTickEvent.Pre event) {
        Player player = event.getEntity();
        if (isWornBy(player) && player.getAttribute(Attributes.STEP_HEIGHT) != null) {
            if (player.isCrouching())
                player.getAttribute(Attributes.STEP_HEIGHT).removeModifier(STEP_HEIGHT_MODIFIER);
            else
                player.getAttribute(Attributes.STEP_HEIGHT).addOrUpdateTransientModifier(STEP_HEIGHT_MODIFIER);
        }
        else
            player.getAttribute(Attributes.STEP_HEIGHT).removeModifier(STEP_HEIGHT_MODIFIER);
    }

    public void onPlayerTickPost(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        if (isWornBy(player)) {
            float speedModifier = 0f;
            if (player.isSprinting())
                speedModifier = player.onGround() ? 0.084f : 0.020f;
            else if (player.zza > 0f)
                speedModifier = player.onGround() ? 0.08f : 0.025f;
            speedModifier /= player.isInWater() ? 5f : 1f;
            float rotation = Mth.PI / 180f * player.getYRot();
            player.setDeltaMovement(player.getDeltaMovement().add(-Mth.sin(rotation) * speedModifier, 0f, Mth.cos(rotation) * speedModifier));
        }
    }

    public void onLivingJump(LivingEvent.LivingJumpEvent event) {
        LivingEntity livingEntity = event.getEntity();
        if (isWornBy(livingEntity))
            livingEntity.setDeltaMovement(livingEntity.getDeltaMovement().add(0f, 0.265f, 0f));
    }

    public void onLivingFall(LivingFallEvent event) {
        if (isWornBy(event.getEntity())) {
            if (event.getDistance() <= 5f)
                event.setDamageMultiplier(0f);
            else
                event.setDamageMultiplier(event.getDamageMultiplier() * 0.5f);
        }
    }

    public static boolean isWornBy(Entity entity) {
        if (!(entity instanceof LivingEntity livingEntity))
            return false;
        return livingEntity.getItemBySlot(EquipmentSlot.FEET).getItem() instanceof TravelersBootsItem;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, List<Component> tooltipComponents, @NotNull TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("tooltip.travelersboots.travelers_boots.tooltip").withStyle(ChatFormatting.GOLD));
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}
