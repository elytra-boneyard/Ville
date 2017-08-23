package com.elytradev.ville.gui;

import com.elytradev.concrete.inventory.gui.client.ConcreteGui;
import com.elytradev.ville.entity.IExtendedMerchant;
import com.elytradev.ville.inventory.ContainerExtendedMerchant;
import net.minecraft.entity.player.InventoryPlayer;

public class GuiExtendedMerchant extends ConcreteGui {

    public GuiExtendedMerchant(InventoryPlayer player, IExtendedMerchant merchant) {
        super(new ContainerExtendedMerchant(player, merchant));
    }



}
