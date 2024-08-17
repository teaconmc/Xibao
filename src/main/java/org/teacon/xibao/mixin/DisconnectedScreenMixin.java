package org.teacon.xibao.mixin;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.layouts.LinearLayout;
import net.minecraft.client.gui.screens.DisconnectedScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.teacon.xibao.Xibao;

import java.nio.file.Files;

@Mixin(DisconnectedScreen.class)
public abstract class DisconnectedScreenMixin extends Screen {

    @Unique
    private static final ResourceLocation xibao$LOCATION = ResourceLocation.fromNamespaceAndPath("xibao", "textures/xibao.png");

    @Final
    @Shadow
    private LinearLayout layout;

    protected DisconnectedScreenMixin(Component title) {
        super(title);
    }

    @Inject(method = "init", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/layouts/LinearLayout;arrangeElements()V"))
    private void addMoreButton(CallbackInfo ci) {
        if (Files.exists(Xibao.getXibaoStopFile())) {
            return;
        }
        var translatable = Component.translatable("xibao.do_not_show_again");
        this.layout.addChild(Button.builder(translatable, Xibao::onPress).width(200).build());
    }

    @Override
    public void renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        if (Files.exists(Xibao.getXibaoStopFile())) {
            return;
        }
        ((GuiGraphicsAccessor)guiGraphics).invokeInnerBlit(
                xibao$LOCATION, 0, this.width, 0, this.height, 0, 0, 1, 0, 1
        );
    }
}
