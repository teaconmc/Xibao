package org.teacon.xibao;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.DisconnectedScreen;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.IExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLPaths;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

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
        public static void on(ScreenEvent.InitScreenEvent.Post event) {
            var showXibao = !Files.exists(FMLPaths.GAMEDIR.get().resolve(".xibao_stop"));
            if (showXibao && event.getScreen() instanceof DisconnectedScreen s) {
                var disableXibao = new Button(s.width / 2 - 75, s.height - 30, 150, 20, new TranslatableComponent("xibao.do_not_show_again"), btn -> {
                    var gameDir = FMLPaths.GAMEDIR.get();
                    try {
                        Files.writeString(gameDir.resolve(".xibao_stop"), "Remove this file to show Xibao again", StandardCharsets.UTF_8);
                    } catch (IOException e) {
                        return;
                    }
                    btn.active = false;
                });
                event.addListener(disableXibao);
            }
        }

        @SubscribeEvent
        public static void on(ScreenEvent.BackgroundDrawnEvent event) {
            var showXibao = !Files.exists(FMLPaths.GAMEDIR.get().resolve(".xibao_stop"));
            if (showXibao && event.getScreen() instanceof DisconnectedScreen s) {
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
