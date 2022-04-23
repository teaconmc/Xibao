package org.teacon.xibao;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.gui.screens.DisconnectedScreen;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.IExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;

@Mod("xibao")
public class Xibao {
    public Xibao() {
        ModLoadingContext.get().registerExtensionPoint(IExtensionPoint.DisplayTest.class,
            () -> new IExtensionPoint.DisplayTest(() -> "ANY", (a, b) -> b));
    }

    @Mod.EventBusSubscriber(modid = "xibao", value = Dist.CLIENT)
    public static final class XibaoImpl {
        private static final ResourceLocation LOCATION = new ResourceLocation("xibao", "textures/xibao.png");
        @SubscribeEvent
        public static void on(ScreenEvent.BackgroundDrawnEvent event) {
            if (event.getScreen() instanceof DisconnectedScreen s) {
                Tesselator tesselator = Tesselator.getInstance();
                BufferBuilder bufferbuilder = tesselator.getBuilder();
                RenderSystem.setShaderTexture(0, LOCATION);
                bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
                bufferbuilder.vertex(0.0D, s.height, 0.0D).uv(0F, 1F).color(255, 255, 255, 255).endVertex();
                bufferbuilder.vertex(s.width, s.height, 0.0D).uv(1F, 1F).color(255, 255, 255, 255).endVertex();
                bufferbuilder.vertex(s.width, 0.0D, 0.0D).uv(1F, 0F).color(255, 255, 255, 255).endVertex();
                bufferbuilder.vertex(0.0D, 0.0D, 0.0D).uv(0F, 0F).color(255, 255, 255, 255).endVertex();
                tesselator.end();
            }
        }
    }
}
