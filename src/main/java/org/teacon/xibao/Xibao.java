package org.teacon.xibao;

import net.minecraft.client.gui.components.Button;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLPaths;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Mod(value = "xibao", dist = Dist.CLIENT)
public class Xibao {

    public static Path getXibaoStopFile() {
        return FMLPaths.GAMEDIR.get().resolve(".xibao_stop");
    }

    public static void onPress(Button btn) {
        try (var out = Files.newBufferedWriter(getXibaoStopFile())) {
            out.write("Remove this file to show Xibao again\n");
        } catch (IOException e) {
            return;
        }
        btn.active = false;
    }
}
