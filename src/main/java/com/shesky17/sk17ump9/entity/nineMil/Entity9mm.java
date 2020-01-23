package com.shesky17.sk17ump9.entity.nineMil;

import com.shesky17.sk17ump9.init.ModBullet;
import com.shesky17.sk17ump9.util.handler.SoundHandler;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SPacketChangeGameState;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import java.util.Random;

import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class Entity9mm extends EntityArrow
{
    //NOTE: I KNOW I KNOW!!! You might be wondering why use reflection....
    // I promise I will find a better way in the future... Please I just wanna push to production sooner....
    // There is not any noticeable performance trade-off as far as i can tell, forgive me.
    private int knockback = ReflectionHelper.getPrivateValue(EntityArrow.class,this,"knockbackStrength");

    /** Useless constructors */
    public Entity9mm(World worldIn){
        super(worldIn);
        ReflectionHelper.setPrivateValue(EntityArrow.class, this, -1, "xTile"); //this.xTile = -1;
        ReflectionHelper.setPrivateValue(EntityArrow.class, this, -1, "yTile");//this.yTile = -1;
        ReflectionHelper.setPrivateValue(EntityArrow.class, this, -1, "zTile");//this.zTile = -1;
        this.pickupStatus = EntityArrow.PickupStatus.DISALLOWED;
        this.setDamage(2.0D);
        this.setSize(0.5F, 0.5F);
    }
    public Entity9mm(World worldIn, double x, double y, double z){
        super(worldIn, x, y, z);
    }
    public Entity9mm(World worldIn, EntityLivingBase shooter){
        super(worldIn, shooter);
    }

    @Override
    protected ItemStack getArrowStack() {
        return new ItemStack(ModBullet.NINEMIL);
    }

    @Override
    //what happens when arrow hit
    protected void arrowHit(EntityLivingBase living) {
        super.arrowHit(living);
        living.setFire(2);
    }

    @Override
    //particle and stuffs
    public void onUpdate() {
        super.onUpdate();
        this.pickupStatus = PickupStatus.DISALLOWED;
        if(this.world.isRemote){
            if(this.inGround){
                /** Despawn arrow when in ground */
                this.onKillCommand();
//                if(this.timeInGround % 5 == 0){this.spawnMyParticles(2);}
            }
            else{
                this.spawnMyParticles(2);
            }
        }
    }

    private void spawnMyParticles(int particleCount){
        Random rnd = new Random();
        int i = rnd.nextInt(15);
        double d0 = (double)(i >> 16 & 255) / 255.0D;
        double d1 = (double)(i >> 8 & 255) / 255.0D;
        double d2 = (double)(i >> 0 & 255) / 255.0D;
        for(int j = 0; j<particleCount; ++j){
            /** this is how you create explosion when hit
             * trying other types of particle types may cause out of bound */
            this.world.spawnParticle(EnumParticleTypes.SMOKE_LARGE,
                    this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width,
                    this.posY + this.rand.nextDouble() * (double)this.height,
                    this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width,
                    d0, d1, d2
            );
        }
    }

    @Override
    protected void onHit(RayTraceResult raytraceResultIn)
    {
        Entity entity = raytraceResultIn.entityHit;

        if (entity != null) {
            float f = MathHelper.sqrt(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
            int i = MathHelper.ceil((double)f * this.getDamage());

            if (this.getIsCritical()) {
                i += this.rand.nextInt(i / 2 + 2);
            }

            DamageSource damagesource;

            if (this.shootingEntity == null) {
                damagesource = DamageSource.causeArrowDamage(this, this);
            }
            else {
                damagesource = DamageSource.causeArrowDamage(this, this.shootingEntity);
            }

            if (this.isBurning() && !(entity instanceof EntityEnderman)) {
                entity.setFire(5);
            }

            if (entity.attackEntityFrom(damagesource, (float)i)) {
                if (entity instanceof EntityLivingBase) {
                    EntityLivingBase entitylivingbase = (EntityLivingBase)entity;
                    if (knockback > 0) { //knockbackStrength
                        float f1 = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);

                        if (f1 > 0.0F) {
                            //(double)this.knockbackStrength
                            entitylivingbase.addVelocity(this.motionX * knockback * 0.6000000238418579D / (double)f1, 0.1D, this.motionZ * knockback * 0.6000000238418579D / (double)f1);
                        }
                    }

                    if (this.shootingEntity instanceof EntityLivingBase) {
                        EnchantmentHelper.applyThornEnchantments(entitylivingbase, this.shootingEntity);
                        EnchantmentHelper.applyArthropodEnchantments((EntityLivingBase)this.shootingEntity, entitylivingbase);
                    }

                    this.arrowHit(entitylivingbase);

                    if (this.shootingEntity != null && entitylivingbase != this.shootingEntity && entitylivingbase instanceof EntityPlayer && this.shootingEntity instanceof EntityPlayerMP) {
                        ((EntityPlayerMP)this.shootingEntity).connection.sendPacket(new SPacketChangeGameState(6, 0.0F));
                    }
                }

                this.playSound(SoundHandler.ump_shooting, 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));

                if (!(entity instanceof EntityEnderman)) {
                    this.setDead();
                }
            }
            else
            {
                this.motionX *= -0.10000000149011612D;
                this.motionY *= -0.10000000149011612D;
                this.motionZ *= -0.10000000149011612D;
                this.rotationYaw += 180.0F;
                this.prevRotationYaw += 180.0F;
                ReflectionHelper.setPrivateValue(EntityArrow.class, this, 0, "ticksInAir"); //this.ticksInAir = 0;

                if (!this.world.isRemote && this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ < 0.0010000000474974513D) {
                    if (this.pickupStatus == EntityArrow.PickupStatus.ALLOWED) {
                        this.entityDropItem(this.getArrowStack(), 0.1F);
                    }
                    this.setDead();
                }
            }
        }
        else
        {
            BlockPos blockpos = raytraceResultIn.getBlockPos();
            ReflectionHelper.setPrivateValue(EntityArrow.class, this, blockpos.getX(), "xTile");
            ReflectionHelper.setPrivateValue(EntityArrow.class, this, blockpos.getY(), "yTile");
            ReflectionHelper.setPrivateValue(EntityArrow.class, this, blockpos.getZ(), "zTile");
//            this.xTile = blockpos.getX();
//            this.yTile = blockpos.getY();
//            this.zTile = blockpos.getZ();
            IBlockState iblockstate = this.world.getBlockState(blockpos);

            ReflectionHelper.setPrivateValue(EntityArrow.class, this, iblockstate.getBlock(), "inTile");
            //this.inTile = iblockstate.getBlock();

            Block inT = ReflectionHelper.getPrivateValue(EntityArrow.class, this, "inTile");
            ReflectionHelper.setPrivateValue(EntityArrow.class, this, inT.getMetaFromState(iblockstate), "inData");
            //this.inData = this.inTile.getMetaFromState(iblockstate);

            this.motionX = (double)((float)(raytraceResultIn.hitVec.x - this.posX));
            this.motionY = (double)((float)(raytraceResultIn.hitVec.y - this.posY));
            this.motionZ = (double)((float)(raytraceResultIn.hitVec.z - this.posZ));
            float f2 = MathHelper.sqrt(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
            this.posX -= this.motionX / (double)f2 * 0.05000000074505806D;
            this.posY -= this.motionY / (double)f2 * 0.05000000074505806D;
            this.posZ -= this.motionZ / (double)f2 * 0.05000000074505806D;
            this.playSound(SoundHandler.ump_shooting, 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
            this.inGround = true;
            this.arrowShake = 7;
            this.setIsCritical(false);

            if (iblockstate.getMaterial() != Material.AIR) {
                //this.inTile.onEntityCollidedWithBlock(this.world, blockpos, iblockstate, this);
                inT.onEntityCollidedWithBlock(this.world, blockpos, iblockstate, this);
            }
        }
    }


}
