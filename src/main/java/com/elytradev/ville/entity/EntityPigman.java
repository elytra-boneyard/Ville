package com.elytradev.ville.entity;

import com.elytradev.ville.Ville;
import com.elytradev.ville.entity.ai.EntityAILookAtCustomer;
import com.elytradev.ville.entity.ai.EntityAIMerchant;
import com.google.common.base.Predicate;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class EntityPigman extends EntityAgeable implements INpc, IExtendedMerchant {
    public EntityPigman(World worldIn) {
        super(worldIn);
        this.setSize(0.6F, 1.95F);
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIAttackMelee(this, 1.0D, false));
        this.tasks.addTask(1, new EntityAIMerchant<>(this));
        this.tasks.addTask(1, new EntityAILookAtCustomer<>(this));
        this.tasks.addTask(2, new EntityAIMoveIndoors(this));
        this.tasks.addTask(3, new EntityAIRestrictOpenDoor(this));
        this.tasks.addTask(4, new EntityAIOpenDoor(this, true));
        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 0.6D));
        //this.tasks.addTask(6, new EntityAIVillagerMate(this));
        this.tasks.addTask(9, new EntityAIWatchClosest2(this, EntityPlayer.class, 3.0F, 1.0F));
        //this.tasks.addTask(9, new EntityAIVillagerInteract(this));
        this.tasks.addTask(9, new EntityAIWanderAvoidWater(this, 0.6D));
        this.tasks.addTask(10, new EntityAIWatchClosest(this, EntityLiving.class, 8.0F));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, EntityPigZombie.class));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityZombie.class, 10, false, true, new Predicate<EntityZombie>() {
            @Override
            public boolean apply(@Nullable EntityZombie input) {
                return true;
            }
        }));


    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1.0D);
    }

    @Nullable
    @Override
    public EntityAgeable createChild(EntityAgeable ageable) {
        return null;
    }

    @Override
    public void setCustomer(@Nullable EntityPlayer player) {

    }

    @Nullable
    @Override
    public EntityPlayer getCustomer() {
        return null;
    }

    @Override
    public boolean isTrading() {
        return this.getCustomer() != null;
    }

    @Override
    public boolean processInteract(EntityPlayer player, EnumHand hand) {
        player.sendMessage(new TextComponentString("<Pigman> Test test, 1 2 3!"));
        return true;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvent.REGISTRY.getObject(new ResourceLocation(Ville.MOD_ID, "entity.pigman.ambient"));
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
        return SoundEvent.REGISTRY.getObject(new ResourceLocation(Ville.MOD_ID, "entity.pigman.hurt"));
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvent.REGISTRY.getObject(new ResourceLocation(Ville.MOD_ID, "entity.pigman.death"));
    }

    @Nullable
    @Override
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
        this.setEquipmentBasedOnDifficulty(difficulty);
        return super.onInitialSpawn(difficulty, livingdata);
    }

    @Override
    protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty) {
        this.setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(Items.LEATHER_HELMET));
        this.setItemStackToSlot(EntityEquipmentSlot.CHEST, new ItemStack(Items.LEATHER_CHESTPLATE));
        this.setItemStackToSlot(EntityEquipmentSlot.LEGS, new ItemStack(Items.LEATHER_LEGGINGS));
        this.setItemStackToSlot(EntityEquipmentSlot.FEET, new ItemStack(Items.LEATHER_BOOTS));
        this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.STONE_SWORD));
        this.setItemStackToSlot(EntityEquipmentSlot.OFFHAND, new ItemStack(Items.SHIELD));
    }

    @Override
    public void onLivingUpdate() {
        this.updateArmSwingProgress();
        float f = this.getBrightness();
        if(f > 0.5F) {
            this.idleTime += 2;
        }

        super.onLivingUpdate();
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if(!this.world.isRemote && this.world.getDifficulty() == EnumDifficulty.PEACEFUL) {
            this.setDead();
        }

    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        return this.isEntityInvulnerable(source)?false:super.attackEntityFrom(source, amount);
    }



    @Override
    public boolean attackEntityAsMob(Entity entityIn) {
        float f = (float)this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
        int i = 0;
        if(entityIn instanceof EntityLivingBase) {
            f += EnchantmentHelper.getModifierForCreature(this.getHeldItemMainhand(), ((EntityLivingBase)entityIn).getCreatureAttribute());
            i += EnchantmentHelper.getKnockbackModifier(this);
        }

        boolean flag = entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), f);
        if(flag) {
            if(i > 0 && entityIn instanceof EntityLivingBase) {
                ((EntityLivingBase)entityIn).knockBack(this, (float)i * 0.5F, (double) MathHelper.sin(this.rotationYaw * 0.017453292F), (double)(-MathHelper.cos(this.rotationYaw * 0.017453292F)));
                this.motionX *= 0.6D;
                this.motionZ *= 0.6D;
            }

            int j = EnchantmentHelper.getFireAspectModifier(this);
            if(j > 0) {
                entityIn.setFire(j * 4);
            }


            this.applyEnchantments(this, entityIn);
        }

        return flag;
    }

    @Override
    public void onDeath(DamageSource cause) {
        if(cause.getImmediateSource() instanceof EntityZombie) {
            EntityPigZombie zombie = new EntityPigZombie(this.world);
            zombie.copyLocationAndAnglesFrom(this);
            zombie.setItemStackToSlot(EntityEquipmentSlot.HEAD, this.getItemStackFromSlot(EntityEquipmentSlot.HEAD));
            zombie.setItemStackToSlot(EntityEquipmentSlot.CHEST, this.getItemStackFromSlot(EntityEquipmentSlot.CHEST));
            zombie.setItemStackToSlot(EntityEquipmentSlot.LEGS, this.getItemStackFromSlot(EntityEquipmentSlot.LEGS));
            zombie.setItemStackToSlot(EntityEquipmentSlot.FEET, this.getItemStackFromSlot(EntityEquipmentSlot.FEET));
            zombie.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, this.getItemStackFromSlot(EntityEquipmentSlot.MAINHAND));
            zombie.setItemStackToSlot(EntityEquipmentSlot.OFFHAND, this.getItemStackFromSlot(EntityEquipmentSlot.OFFHAND));
            world.spawnEntity(zombie);
            this.world.playEvent(null, 1026, new BlockPos(this), 0);
        }
    }
}
