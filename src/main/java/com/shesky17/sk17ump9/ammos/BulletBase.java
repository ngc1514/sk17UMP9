package com.shesky17.sk17ump9.ammos;

import com.shesky17.sk17ump9.Main;
import com.shesky17.sk17ump9.entity.nineMil.Entity9mm;
import com.shesky17.sk17ump9.init.ModBullet;
import com.shesky17.sk17ump9.util.IHasModel;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BulletBase extends ItemArrow implements IHasModel
{
    public BulletBase(String name){
        setUnlocalizedName(name);
        setRegistryName(name);
        this.setCreativeTab(CreativeTabs.COMBAT);
        ModBullet.AMMO_LIST.add(this);
    }


    @Override
    public EntityArrow createArrow(World worldIn, ItemStack stack, EntityLivingBase shooter) {
        Entity9mm entity9MM = new Entity9mm(worldIn, shooter);
        return entity9MM;
    }


    @Override
    public void registerModels() {
        Main.proxy.registerItemRenderer(this, 0, "inventory");
    }
}
