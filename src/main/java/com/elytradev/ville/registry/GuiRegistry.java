package com.elytradev.ville.registry;


import com.elytradev.concrete.inventory.gui.ConcreteContainer;
import com.elytradev.ville.Ville;
import com.elytradev.ville.entity.IExtendedMerchant;
import com.elytradev.ville.gui.GuiExtendedMerchant;
import com.elytradev.ville.inventory.ContainerExtendedMerchant;
import com.elytradev.ville.inventory.InventoryExtendedMerchant;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class GuiRegistry implements IGuiHandler {

    public static final int GUI_EXTENDED_MERCHANT = 0;

    @Nullable
    @Override
    public Container getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        ConcreteContainer container = null;
        switch(ID) {
            case GUI_EXTENDED_MERCHANT: {
                Entity entity = world.getEntityByID(x);
                if(entity instanceof  IExtendedMerchant) {
                    IExtendedMerchant merchant = (IExtendedMerchant)entity;
                    container = new ContainerExtendedMerchant(player.inventory, merchant, new InventoryExtendedMerchant(player.inventory, merchant), world);
                }
                else {
                    Ville.LOG.error("Entity " + entity + " is not instanceof IExtendedMerchant - can't open ContainerExtendedMerchant!");
                }
            }
        }

        if(container != null)
            container.validate();

        return container;
    }

    @Nullable
    @Override
    @SideOnly(Side.CLIENT)
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch(ID) {
            case GUI_EXTENDED_MERCHANT: {
                Entity entity = world.getEntityByID(x);
                if(entity instanceof  IExtendedMerchant) {
                    return new GuiExtendedMerchant(player.inventory, (IExtendedMerchant)entity, world);
                }
                else {
                    Ville.LOG.error("Entity " + entity + " is not instanceof IExtendedMerchant - can't open GuiExtendedMerchant!");
                }
            }
        }
        return null;
    }

}
