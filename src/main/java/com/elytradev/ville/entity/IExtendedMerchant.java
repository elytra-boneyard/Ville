package com.elytradev.ville.entity;

import net.minecraft.entity.player.EntityPlayer;

import javax.annotation.Nullable;

public interface IExtendedMerchant {

    void setCustomer(@Nullable EntityPlayer player);

    @Nullable
    EntityPlayer getCustomer();

    boolean isTrading();
}
