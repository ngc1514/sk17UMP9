package com.shesky17.sk17ump9.util.handler;

import com.shesky17.sk17ump9.init.ModBlocks;
import com.shesky17.sk17ump9.init.ModGuns;
import com.shesky17.sk17ump9.init.ModKnife;
import com.shesky17.sk17ump9.init.ModItems;
import com.shesky17.sk17ump9.util.IHasModel;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemSword;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber
public class RegistryHandler
{
    //NOTE: register items
    @SubscribeEvent
    public static void onItemRegister(RegistryEvent.Register<Item> event)
    {
        event.getRegistry().registerAll(ModItems.ITEMS_LIST.toArray(new Item[0]));
    }

    //NOTE: register blocks
    @SubscribeEvent
    public static void onBlockRegister(RegistryEvent.Register<Block> event)
    {
        event.getRegistry().registerAll(ModBlocks.BLOCK_LIST.toArray(new Block[0]));
    }

    //NOTE: register knives
    @SubscribeEvent
    public static void onKnifeRegister(RegistryEvent.Register<Item> event)
    {
        event.getRegistry().registerAll(ModKnife.KNIFE_LIST.toArray(new ItemSword[0]));
    }

    //NOTE: register guns
    @SubscribeEvent
    public static void onGunRegister(RegistryEvent.Register<Item> event)
    {
        event.getRegistry().registerAll(ModGuns.GUN_LIST.toArray(new ItemBow[0]));
    }

    //NOTE: REMEMBER TO REGISTER!!!
    @SubscribeEvent
    public static void onModelRegister(ModelRegistryEvent event)
    {
        for(Item item: ModItems.ITEMS_LIST) {
            if(item instanceof IHasModel) {
                ((IHasModel)item).registerModels();
            }
        }

        for(Block block: ModBlocks.BLOCK_LIST) {
            if(block instanceof IHasModel) {
                ((IHasModel)block).registerModels();
            }
        }

        for(ItemSword knife: ModKnife.KNIFE_LIST) {
            if(knife instanceof IHasModel) {
                ((IHasModel)knife).registerModels();
            }
        }

        for(ItemBow gun: ModGuns.GUN_LIST) {
            if(gun instanceof IHasModel) {
                ((IHasModel)gun).registerModels();
            }
        }
    }
}
