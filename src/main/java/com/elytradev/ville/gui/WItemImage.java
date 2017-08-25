package com.elytradev.ville.gui;


import com.elytradev.concrete.inventory.gui.widget.WWidget;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class WItemImage extends WWidget {

    public ItemStack itemStack;
    public WItemImage() {
        itemStack = new ItemStack(Items.APPLE);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void paintBackground(int x, int y) {
        super.paint(x, y);
        RenderItem render = Minecraft.getMinecraft().getRenderItem();
        render.renderItemAndEffectIntoGUI(itemStack, x,y);
    }
}
