package com.elytradev.ville.inventory;

import com.elytradev.concrete.inventory.gui.ConcreteContainer;
import com.elytradev.concrete.inventory.gui.widget.WItemSlot;
import com.elytradev.concrete.inventory.gui.widget.WPlainPanel;
import com.elytradev.ville.entity.IExtendedMerchant;
import com.elytradev.ville.gui.WExtendedMerchantResultSlot;
import com.elytradev.ville.gui.WItemImage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ContainerExtendedMerchant extends ConcreteContainer {

    private IExtendedMerchant merchant;
    private InventoryExtendedMerchant merchantInventory;
    private World world;

    public ContainerExtendedMerchant(InventoryPlayer playerInventory, IExtendedMerchant merchant, InventoryExtendedMerchant merchantInventory, World world) {
        super(playerInventory, merchantInventory);

        this.merchant = merchant;
        this.merchantInventory = merchantInventory;
        this.world = world;

        WPlainPanel panel = new WPlainPanel();

        this.setRootPanel(panel);

        panel.add(WItemSlot.of(this.merchantInventory, 0), 36, 53);
        panel.add(WItemSlot.of(this.merchantInventory, 1), 62, 53);
        panel.add(new WExtendedMerchantResultSlot(merchant, merchantInventory, 2), 120, 53);
        panel.add(WItemSlot.ofPlayerStorage(playerInventory), 0, 84);
        panel.add(WItemSlot.of(playerInventory, 0, 9, 1), 0, 144);
        panel.add(new WItemImage(), 36, 24);
        panel.add(new WItemImage(), 62, 24);
        panel.add(new WItemImage(), 120, 24);
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return true;
    }

    public void onContainerClosed(EntityPlayer playerIn)
    {
        super.onContainerClosed(playerIn);
        this.merchant.setCustomer(null);

        if (!this.world.isRemote)
        {
            ItemStack itemStack = this.merchantInventory.removeStackFromSlot(0);

            if (!itemStack.isEmpty())
                playerIn.dropItem(itemStack, false);

            itemStack = this.merchantInventory.removeStackFromSlot(1);

            if (!itemStack.isEmpty())
                playerIn.dropItem(itemStack, false);
        }
    }

}
