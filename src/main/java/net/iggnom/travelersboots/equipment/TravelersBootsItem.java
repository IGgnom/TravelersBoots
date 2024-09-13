package net.iggnom.travelersboots.equipment;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class TravelersBootsItem extends ArmorItem {
    public TravelersBootsItem(ArmorMaterial material, Properties properties) {
        super(material, EquipmentSlot.FEET, properties);
        MinecraftForge.EVENT_BUS.addListener(this::onLivingJump);
        MinecraftForge.EVENT_BUS.addListener(this::onLivingFall);
        MinecraftForge.EVENT_BUS.addListener(this::onPlayerTick);
    }

    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (isWornBy(event.player)) {
            float speedModifier = 0f;
            if (event.player.isSprinting())
                speedModifier = event.player.isOnGround() ? 0.031f : 0.012f;
            else if (event.player.zza > 0f)
                speedModifier = event.player.isOnGround() ? 0.03f : 0.015f;
            speedModifier /= event.player.isInWater() ? 4f : 1f;
            float rotation = Mth.PI / 180f * event.player.getYRot();
            event.player.setDeltaMovement(event.player.getDeltaMovement().add(-Mth.sin(rotation) * speedModifier, 0f, Mth.cos(rotation) * speedModifier));
        }
    }

    public void onLivingJump(LivingEvent.LivingJumpEvent event) {
        LivingEntity livingEntity = event.getEntity();
        if (isWornBy(livingEntity))
            livingEntity.setDeltaMovement(livingEntity.getDeltaMovement().add(0f, 0.265f, 0f));
    }

    public void onLivingFall(LivingFallEvent event) {
        LivingEntity livingEntity = event.getEntity();
        if (isWornBy(livingEntity)) {
            if (event.getDistance() <= 5f)
                event.setDamageMultiplier(0f);
            else
                event.setDamageMultiplier(event.getDamageMultiplier() * 0.5f);
        }
    }

    @Nonnull
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(@Nonnull EquipmentSlot slot) {
        return applyModifiers(super.getDefaultAttributeModifiers(slot), slot);
    }

    public Multimap<Attribute, AttributeModifier> applyModifiers(Multimap<Attribute, AttributeModifier> map, @Nullable EquipmentSlot slot) {
        Multimap<Attribute, AttributeModifier> modifierMultimap = LinkedHashMultimap.create(map);
        if (slot == EquipmentSlot.FEET) {
            UUID uuid = new UUID(Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(this)).hashCode() + slot.name().hashCode(), 0L);
            modifierMultimap.put(ForgeMod.STEP_HEIGHT_ADDITION.get(), new AttributeModifier(uuid, "Step Height Modifier", 0.5f, AttributeModifier.Operation.ADDITION));
        }
        return modifierMultimap;
    }

    public static boolean isWornBy(Entity entity) {
        if (!(entity instanceof LivingEntity livingEntity))
            return false;
        return livingEntity.getItemBySlot(EquipmentSlot.FEET).getItem() instanceof TravelersBootsItem;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("tooltip.travelersboots.travelers_boots.tooltip").withStyle(ChatFormatting.GOLD));
        super.appendHoverText(stack, level, tooltipComponents, tooltipFlag);
    }
}
