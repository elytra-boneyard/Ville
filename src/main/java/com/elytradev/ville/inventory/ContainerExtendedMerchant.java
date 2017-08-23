package com.elytradev.ville.inventory;

import com.elytradev.concrete.inventory.gui.ConcreteContainer;
import com.elytradev.ville.entity.IExtendedMerchant;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;

public class ContainerExtendedMerchant extends ConcreteContainer {
    public ContainerExtendedMerchant(InventoryPlayer player, IExtendedMerchant merchant) {
        super(player, new InventoryExtendedMerchant(player, merchant));
    }
}
