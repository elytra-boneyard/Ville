package com.elytradev.ville.entity;

import com.elytradev.ville.Ville;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.model.ModelZombie;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public class RenderPigman extends RenderLiving<EntityPigman> {
    private ResourceLocation pigmanTexture;

    public RenderPigman(RenderManager renderManagerIn) {
        this(renderManagerIn, new ModelPlayer(0.0f, false), 0.5f);
    }

    public RenderPigman(RenderManager rendermanagerIn, ModelBase modelbaseIn, float shadowsizeIn) {
        super(rendermanagerIn, modelbaseIn, shadowsizeIn);
        this.addLayer(new LayerBipedArmor(this));
        this.addLayer(new LayerHeldItem(this));
        pigmanTexture = new ResourceLocation(Ville.MOD_ID, "textures/entity/pigman/default.png");
    }


    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityPigman entity) {
        return pigmanTexture;
    }

    public void transformHeldFull3DItemLayer() {
        GlStateManager.translate(0.0F, 0.1875F, 0.0F);
    }

    @Override
    public void renderName(EntityPigman entityIn, double x, double y, double z) {
        double d0 = entityIn.getDistanceSqToEntity(this.renderManager.renderViewEntity);

        if (d0 <= (double)(16.0d * 16.0d))
        {
            boolean flag = entityIn.isSneaking();
            float f = this.renderManager.playerViewY;
            float f1 = this.renderManager.playerViewX;
            boolean flag1 = this.renderManager.options.thirdPersonView == 2;
            float f2 = entityIn.height + 0.5F - (flag ? 0.25F : 0.0F);
            EntityRenderer.drawNameplate(Minecraft.getMinecraft().standardGalacticFontRenderer, "Pigman", (float)x, (float)y + f2, (float)z, 0, f, f1, flag1, flag);
        }
    }
}
