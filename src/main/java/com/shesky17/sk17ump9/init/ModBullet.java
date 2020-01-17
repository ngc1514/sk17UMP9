package com.shesky17.sk17ump9.init;

import java.util.ArrayList;
import java.util.List;

import com.shesky17.sk17ump9.ammos.BulletBase;
import net.minecraft.item.ItemArrow;

public class ModBullet
{
    public static final List<ItemArrow> AMMO_LIST = new ArrayList<ItemArrow>();

    public static final ItemArrow NINEMIL = new BulletBase("9mm");

    //NOTE: will not add magazine for now, need to figure out how to make 1 mag = 30 9mm rounds
    //public static final ItemArrow UMP9AMMO = new BulletBase("ump9ammo");
}
