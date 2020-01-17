package com.shesky17.sk17ump9.init;

import com.shesky17.sk17ump9.Main;
import com.shesky17.sk17ump9.entity.nineMil.Entity9mm;
import com.shesky17.sk17ump9.util.Ref;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraft.entity.Entity;

public class EntityInit {
    public static void registerEntities(){
        //Will not add ump9 mag now, need to figure out how 1 mag = 30 rounds
        registerArrow("9mm", Entity9mm.class, Ref.ENTITY_9MM);
    }

    private static void registerArrow(String name, Class<? extends Entity> entity, int id)
    {
        EntityRegistry.registerModEntity(new ResourceLocation(Ref.MOD_ID + ":" + name),
                entity,
                name,
                id,
                Main.instance,
                64,
                20,
                true);
    }
}
