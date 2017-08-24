package com.elytradev.ville.gui;

import com.elytradev.concrete.inventory.gui.widget.WItemSlot;
import com.elytradev.ville.entity.IExtendedMerchant;
import com.elytradev.ville.inventory.InventoryExtendedMerchant;

public class WExtendedMerchantResultSlot extends WItemSlot {

    protected IExtendedMerchant merchant;
    protected InventoryExtendedMerchant merchantInventory;

    public WExtendedMerchantResultSlot(IExtendedMerchant merchant, InventoryExtendedMerchant merchantInventory, int slotIndex) {
        super(merchantInventory, slotIndex, 1, 1, true, false);

        this.merchant = merchant;
        this.merchantInventory = merchantInventory;
    }
}
