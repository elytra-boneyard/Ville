package com.elytradev.ville.inventory;

import com.elytradev.concrete.inventory.ConcreteItemStorage;
import com.elytradev.concrete.inventory.ValidatedInventoryView;
import com.elytradev.concrete.inventory.Validators;
import com.elytradev.ville.entity.IExtendedMerchant;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;


public class InventoryExtendedMerchant extends ValidatedInventoryView {

    protected InventoryPlayer player;
    protected IExtendedMerchant merchant;

    public InventoryExtendedMerchant(InventoryPlayer player, IExtendedMerchant merchant) {
        super(new ConcreteItemStorage(3).withValidators(Validators.ANYTHING, Validators.ANYTHING, Validators.NOTHING).withName("Pigman"));
        this.player = player;
        this.merchant = merchant;
    }
}
