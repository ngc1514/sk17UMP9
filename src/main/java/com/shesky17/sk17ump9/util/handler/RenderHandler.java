package com.shesky17.sk17ump9.util.handler;

import com.shesky17.sk17ump9.entity.nineMil.Entity9mm;
import com.shesky17.sk17ump9.entity.nineMil.Render9mm;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderHandler {
    public static void registerEntityRenders(){
        RenderingRegistry.registerEntityRenderingHandler(Entity9mm.class, new IRenderFactory<Entity9mm>()
        {
            @Override
            public Render<? super Entity9mm> createRenderFor(RenderManager manager)
            {
                return new Render9mm(manager);
            }
        });
    }
}
