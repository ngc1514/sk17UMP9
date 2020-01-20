package com.shesky17.sk17ump9.entity.nineMil;

import com.shesky17.sk17ump9.init.ModBullet;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

import java.util.Random;

//https://www.youtube.com/watch?v=g0H1zegcnE4
public class Entity9mm extends EntityArrow
{
    public Entity9mm(World worldIn){
        super(worldIn);
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
                /** This fucking worked */
                this.onKillCommand();
                if(this.timeInGround % 5 == 0){
                    this.spawnMyParticles(2);

                }
            }
            else{
                this.spawnMyParticles(1);
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
            this.world.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL,
                    this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width,
                    this.posY + this.rand.nextDouble() * (double)this.height,
                    this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width,
                    d0, d1, d2
            );
        }
    }
}
