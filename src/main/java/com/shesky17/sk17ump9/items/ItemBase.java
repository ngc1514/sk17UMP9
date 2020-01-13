package com.shesky17.sk17ump9.items;

import com.shesky17.sk17ump9.Main;
import com.shesky17.sk17ump9.init.ModItems;
import com.shesky17.sk17ump9.util.IHasModel;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemBase extends Item implements IHasModel {

    public ItemBase(String name) {
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(CreativeTabs.MATERIALS);

        ModItems.ITEMS_LIST.add(this);
    }


    @Override
    public void registerModels()
    {
        Main.proxy.registerItemRenderer(this, 0, "inventory");
    }

}
