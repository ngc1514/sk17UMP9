package com.shesky17.sk17ump9.init;

import java.util.ArrayList;
import java.util.List;
import com.shesky17.sk17ump9.items.ItemBase;
import net.minecraft.item.Item;

public class ModItems
{

    public static final List<Item> ITEMS_LIST = new ArrayList<Item>();

    //the name argument string has to be the same as the second string in en_us.lang file
    //item.ABC.name, ABC has to be the same as ItemBase name
    public static final Item TEST_INGOT = new ItemBase("test_ingot");
    public static final Item UMP9 = new ItemBase("ump9");
    public static final Item UMP45 = new ItemBase("ump45");

}
