/*
 *  The MIT License (MIT)
 *
 *  Copyright (c) 2017:
 *      Ethan Brooks (CalmBit),
 *      and contributors
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy of
 *  this software and associated documentation files (the "Software"), to deal in
 *  the Software without restriction, including without limitation the rights to
 *  use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 *  of the Software, and to permit persons to whom the Software is furnished to do
 *  so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
 *  copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 *
 */

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
