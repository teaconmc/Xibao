package org.teacon.xibao;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiDisconnected;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod(modid = Xibao.MODID, version = Xibao.VERSION)
public class Xibao
{

    public static final String MODID = "examplemod";
    public static final String VERSION = "1.0";
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
		// some example code
        MinecraftForge.EVENT_BUS.register(this);
    }
    private static final ResourceLocation LOCATION = new ResourceLocation("xibao", "textures/xibao.png");
    @SubscribeEvent
    public void onDisconnect(GuiScreenEvent.BackgroundDrawnEvent e){

        if (e.gui instanceof GuiDisconnected){
            GuiDisconnected s= ((GuiDisconnected) e.gui);
            Minecraft.getMinecraft().getTextureManager().bindTexture(LOCATION);
            Tessellator tessellator = Tessellator.getInstance();
            WorldRenderer worldrenderer = tessellator.getWorldRenderer();
            //Minecraft.getMinecraft().getTextureManager().bindTexture(optionsBackground);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

            worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
            worldrenderer.pos(0.0D, s.height, 0.0D).tex(0F, 1F).color(255, 255, 255, 255).endVertex();
            worldrenderer.pos(s.width, s.height, 0.0D).tex(1F, 1F).color(255, 255, 255, 255).endVertex();
            worldrenderer.pos(s.width, 0.0D, 0.0D).tex(1F, 0F).color(255, 255, 255, 255).endVertex();
            worldrenderer.pos(0.0D, 0.0D, 0.0D).tex(0F, 0F).color(255, 255, 255, 255).endVertex();
            tessellator.draw();


        }
    }
}
