package com.elytradev.ville.entity.ai;

import com.elytradev.ville.entity.IExtendedMerchant;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;

public class EntityAILookAtCustomer<T extends EntityLiving & IExtendedMerchant> extends EntityAIWatchClosest {

    private final T merchantEntity;

    public EntityAILookAtCustomer(T entityIn) {
        super(entityIn, EntityPlayer.class, 8.0F);
        this.merchantEntity = entityIn;
    }

    public boolean shouldExecute() {
        if(this.merchantEntity.isTrading()) {
            this.closestEntity = this.merchantEntity.getCustomer();
            return true;
        } else {
            return false;
        }
    }
}
