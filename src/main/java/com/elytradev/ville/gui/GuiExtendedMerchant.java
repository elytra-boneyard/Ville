package com.elytradev.ville.gui;

import com.elytradev.concrete.inventory.gui.client.ConcreteGui;
import com.elytradev.ville.entity.IExtendedMerchant;
import com.elytradev.ville.inventory.ContainerExtendedMerchant;
import com.elytradev.ville.inventory.InventoryExtendedMerchant;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.world.World;

public class GuiExtendedMerchant extends ConcreteGui {

    public GuiExtendedMerchant(InventoryPlayer player, IExtendedMerchant merchant, World world) {
        super(new ContainerExtendedMerchant(player, merchant, new InventoryExtendedMerchant(player, merchant), world));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}
