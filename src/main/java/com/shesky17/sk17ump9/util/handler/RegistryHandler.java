package com.shesky17.sk17ump9.util.handler;

import com.shesky17.sk17ump9.init.ModItems;
import com.shesky17.sk17ump9.util.IHasModel;

import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber
public class RegistryHandler
{
    @SubscribeEvent
    //here i register the item as type <Item>
    public static void onItemRegister(RegistryEvent.Register<Item> event)
    {
        event.getRegistry().registerAll(ModItems.ITEMS_LIST.toArray(new Item[0]));
    }

    //NOTE: guess i need to register blocks?

    @SubscribeEvent
    public static void onModelRegister(ModelRegistryEvent event)
    {
        for(Item item: ModItems.ITEMS_LIST) {
            if(item instanceof IHasModel) {
                ((IHasModel)item).registerModels();
            }
        }
    }
}
