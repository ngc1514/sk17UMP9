package com.shesky17.sk17ump9.init;

import java.util.ArrayList;
import java.util.List;

import com.shesky17.sk17ump9.guns.GunBase;
import net.minecraft.item.ItemBow;

public class ModGuns
{
    //NOTE: it's a bow for now
    public static final List<ItemBow> GUN_LIST = new ArrayList<ItemBow>();

    public static final ItemBow UMP9 = new GunBase("ump9");
    public static final ItemBow UMP45 = new GunBase("ump45_test");
}
