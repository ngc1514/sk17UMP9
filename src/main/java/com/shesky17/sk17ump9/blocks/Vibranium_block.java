package com.shesky17.sk17ump9.blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class Vibranium_block extends BlockBase
{
    public Vibranium_block(String name, Material material) {
        super(name, material);
        setSoundType(SoundType.METAL);
        setHardness(50.0F);
        setResistance(6000.0F);
        setHarvestLevel("pickaxe",2);
        setLightLevel(1F);
    }
}
