package com.shesky17.sk17ump9.knives;

import com.shesky17.sk17ump9.Main;
import com.shesky17.sk17ump9.init.ModKnife;
import com.shesky17.sk17ump9.util.IHasModel;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemSword;

public class KnifeBase extends ItemSword implements IHasModel
{
    public KnifeBase(String name){
        super(ToolMaterial.valueOf("ob_ingot"));
        this.maxStackSize = 1;
        this.setMaxDamage(385);

        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(CreativeTabs.COMBAT);
        ModKnife.KNIFE_LIST.add(this);
    }


    @Override
    public void registerModels() {
        Main.proxy.registerItemRenderer(this, 0, "inventory");
    }
}
