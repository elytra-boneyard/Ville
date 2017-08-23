package com.elytradev.ville.entity.ai;

import com.elytradev.ville.entity.IExtendedMerchant;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.player.EntityPlayer;

public class EntityAIMerchant<T extends EntityLiving & IExtendedMerchant> extends EntityAIBase {

    private final T merchantEntity;

    public EntityAIMerchant(T entityIn) {
        this.merchantEntity = entityIn;
        this.setMutexBits(5);
    }

    @Override
    public boolean shouldExecute() {
        if(!this.merchantEntity.isEntityAlive())
            return false;
        else if(this.merchantEntity.isInWater())
            return false;
        else if(!this.merchantEntity.onGround)
            return false;
        else if(this.merchantEntity.velocityChanged)
            return false;

        EntityPlayer entityplayer = this.merchantEntity.getCustomer();
        return entityplayer != null && ((this.merchantEntity.getDistanceSqToEntity(entityplayer) < 16.0D) && entityplayer.openContainer != null);
    }

    public void startExecuting() {
        this.merchantEntity.getNavigator().clearPathEntity();
    }

    public void resetTask() {
        this.merchantEntity.setCustomer(null);
    }
}
