package com.shesky17.sk17ump9.materials;

import com.shesky17.sk17ump9.Main;
import com.shesky17.sk17ump9.init.ModItems;
import com.shesky17.sk17ump9.util.IHasModel;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.common.util.EnumHelper;


public class MaterialBase extends Item implements IHasModel
{
    //register a misc item material
    public MaterialBase(String name) {
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(CreativeTabs.MATERIALS); //misc
        EnumHelper.addToolMaterial(name, 3, 999, 6.0F, 996, 22); //set it as a material
        ModItems.ITEMS_LIST.add(this);
    }

    @Override
    public void registerModels()
    {
        Main.proxy.registerItemRenderer(this, 0, "inventory");
    }
}
