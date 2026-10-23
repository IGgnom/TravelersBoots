package net.iggnom.travelersboots.compat.curios;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

public class BootsCurioRenderer implements ICurioRenderer.HumanoidRender {
    private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(
            "travelersboots", "textures/entity/equipment/humanoid/travelers_boots.png");

    private EntityModel<HumanoidRenderState> model;

    @Override
    public EntityModel<HumanoidRenderState> getModel(ItemStack stack, SlotContext slotContext) {
        if (this.model == null) {
            ModelPart root = Minecraft.getInstance().getEntityModels()
                    .bakeLayer(ModelLayers.PLAYER_ARMOR.feet());
            this.model = new HumanoidModel<>(root);
        }
        return this.model;
    }

    @Override
    public Identifier getModelTexture(ItemStack stack, SlotContext slotContext) {
        return TEXTURE;
    }

    @Override
    public void renderFirstPersonHand(
            ItemStack stack,
            SlotContext slotContext,
            HumanoidArm arm,
            PoseStack poseStack,
            SubmitNodeCollector submitNodeCollector,
            AvatarRenderState avatarRenderState,
            AbstractClientPlayer clientPlayer,
            int packedLight) {
    }
}
