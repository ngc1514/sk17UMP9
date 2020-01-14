package com.shesky17.sk17ump9.guns;

import com.shesky17.sk17ump9.Main;
import com.shesky17.sk17ump9.init.ModGuns;
import com.shesky17.sk17ump9.util.IHasModel;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBow;

public class GunBase extends ItemBow implements IHasModel
{
    public GunBase(String name){
        this.maxStackSize = 1;
        this.setMaxDamage(385);

        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(CreativeTabs.COMBAT);
        ModGuns.GUN_LIST.add(this);
    }


    @Override
    public void registerModels() {
        Main.proxy.registerItemRenderer(this, 0, "inventory");
    }
}
