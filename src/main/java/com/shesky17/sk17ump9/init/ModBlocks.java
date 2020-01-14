package com.shesky17.sk17ump9.init;

import java.util.ArrayList;
import java.util.List;

import com.shesky17.sk17ump9.blocks.BlockBase;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class ModBlocks
{
    public static final List<Block> BLOCK_LIST = new ArrayList<Block>();

    public static final Block OB_INGOT_BLOCK = new BlockBase("ob_ingot_block", Material.IRON);
}
