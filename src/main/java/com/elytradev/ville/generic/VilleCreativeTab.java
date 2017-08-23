package com.elytradev.ville.generic;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class VilleCreativeTab extends CreativeTabs {
    public VilleCreativeTab(String label) {
        super(label);
    }

    @Override
    public ItemStack getTabIconItem() {
        return ItemStack.EMPTY;
    }
}
