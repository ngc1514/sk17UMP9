package com.shesky17.sk17ump9.util.handler;

import com.shesky17.sk17ump9.util.Ref;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class SoundHandler
{
    public static SoundEvent ump_shooting;

    public static void registerSounds()
    {
        ump_shooting = registerSound("entity.bullet.ump_shooting");
    }

    private static SoundEvent registerSound(String name)
    {
        ResourceLocation location = new ResourceLocation(Ref.MOD_ID, name);
        SoundEvent event = new SoundEvent(location);
        event.setRegistryName(name);
        ForgeRegistries.SOUND_EVENTS.register(event);
        return event;
    }
}