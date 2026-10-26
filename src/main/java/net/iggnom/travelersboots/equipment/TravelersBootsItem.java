package net.iggnom.travelersboots.equipment;

import net.iggnom.travelersboots.TravelersBoots;
import net.iggnom.travelersboots.item.ModItem;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import org.jetbrains.annotations.NotNull;

import net.neoforged.neoforge.event.entity.living.LivingFallEvent;
import net.neoforged.neoforge.event.entity.living.LivingEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class TravelersBootsItem extends ArmorItem {
    public TravelersBootsItem(Holder<ArmorMaterial> material, Properties properties) {
        super(material, ArmorItem.Type.BOOTS, properties);
    }

    public static final AttributeModifier STEP_HEIGHT_MODIFIER = new AttributeModifier(ResourceLocation.fromNamespaceAndPath(TravelersBoots.MOD_ID,
            "travelersbootsstepassist"), 0.5f, AttributeModifier.Operation.ADD_VALUE);

    private static final float SPRINT_GROUND_SPEED_BOOST = 0.084f;
    private static final float SPRINT_AIR_SPEED_BOOST = 0.020f;
    private static final float WALK_GROUND_SPEED_BOOST = 0.08f;
    private static final float WALK_AIR_SPEED_BOOST = 0.025f;
    private static final float WATER_SPEED_DIVISOR = 5f;
    private static final float SPRINT_EXHAUSTION_CANCEL_PER_BLOCK = 0.05f;
    private static final float JUMP_BOOST = 0.265f;
    private static final float SAFE_FALL_DISTANCE = 5f;
    private static final float HARD_FALL_DAMAGE_MULTIPLIER = 0.5f;

    private static final List<Predicate<Player>> IS_WEARING_PREDICATES = new ArrayList<>();

    static {
        addIsWearingPredicate(player -> player.getItemBySlot(EquipmentSlot.FEET).getItem() instanceof TravelersBootsItem);
    }

    public static synchronized void addIsWearingPredicate(Predicate<Player> predicate) {
        IS_WEARING_PREDICATES.add(predicate);
    }

    public static void onPlayerTickPre(PlayerTickEvent.Pre event) {
        Player player = event.getEntity();
        if (player.level().isClientSide())
            return;
        AttributeInstance stepHeight = player.getAttribute(Attributes.STEP_HEIGHT);
        if (stepHeight == null)
            return;
        boolean active = isWornBy(player) && !player.isCrouching();
        if (active != stepHeight.hasModifier(STEP_HEIGHT_MODIFIER.id())) {
            if (active)
                stepHeight.addTransientModifier(STEP_HEIGHT_MODIFIER);
            else
                stepHeight.removeModifier(STEP_HEIGHT_MODIFIER);
        }
    }

    public static void onPlayerTickPost(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        if (isWornBy(player) && !player.isFallFlying()) {
            float speedModifier = 0f;
            if (player.isSprinting()) {
                speedModifier = player.onGround() ? SPRINT_GROUND_SPEED_BOOST : SPRINT_AIR_SPEED_BOOST;
                if (player.onGround()) {
                    float dx = (float) (player.getX() - player.xo);
                    float dz = (float) (player.getZ() - player.zo);
                    player.causeFoodExhaustion(-SPRINT_EXHAUSTION_CANCEL_PER_BLOCK * Mth.sqrt(dx * dx + dz * dz));
                }
            }
            else if (player.zza > 0f)
                speedModifier = player.onGround() ? WALK_GROUND_SPEED_BOOST : WALK_AIR_SPEED_BOOST;
            speedModifier /= player.isInWater() ? WATER_SPEED_DIVISOR : 1f;
            float rotation = Mth.PI / 180f * player.getYRot();
            player.setDeltaMovement(player.getDeltaMovement().add(-Mth.sin(rotation) * speedModifier, 0f, Mth.cos(rotation) * speedModifier));
        }
    }

    public static void onLivingJump(LivingEvent.LivingJumpEvent event) {
        LivingEntity livingEntity = event.getEntity();
        if (isWornBy(livingEntity))
            livingEntity.setDeltaMovement(livingEntity.getDeltaMovement().add(0f, JUMP_BOOST, 0f));
    }

    public static void onLivingFall(LivingFallEvent event) {
        if (isWornBy(event.getEntity())) {
            if (event.getDistance() <= SAFE_FALL_DISTANCE)
                event.setDamageMultiplier(0f);
            else
                event.setDamageMultiplier(event.getDamageMultiplier() * HARD_FALL_DAMAGE_MULTIPLIER);
        }
    }

    public static boolean isWornBy(Entity entity) {
        if (!(entity instanceof Player player))
            return false;
        for (Predicate<Player> predicate : IS_WEARING_PREDICATES) {
            if (predicate.test(player)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean canWalkOnPowderedSnow(ItemStack stack, @NotNull LivingEntity wearer) {
        return stack.is(ModItem.TRAVELERS_BOOTS_ITEM);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, List<Component> tooltipComponents, @NotNull TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("tooltip.travelersboots.travelers_boots").withStyle(ChatFormatting.GOLD));
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}
