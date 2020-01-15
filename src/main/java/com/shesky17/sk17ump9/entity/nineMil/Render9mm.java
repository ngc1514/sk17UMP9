package com.shesky17.sk17ump9.entity.nineMil;

import com.shesky17.sk17ump9.util.Ref;
import net.minecraft.client.renderer.entity.RenderArrow;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public class Render9mm extends RenderArrow<Entity9mm> {

    public Render9mm(RenderManager manager)
    {
        super(manager);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(Entity9mm entity) {
        return new ResourceLocation(Ref.MOD_ID + ":textures/entity/bullets/9mm.png");
    }
}
