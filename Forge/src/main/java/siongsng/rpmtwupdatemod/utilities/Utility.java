package siongsng.rpmtwupdatemod.utilities;

import net.minecraft.client.Minecraft;
import siongsng.rpmtwupdatemod.config.RPMTWConfig;
import siongsng.rpmtwupdatemod.gui.CosmicChatScreen;
import siongsng.rpmtwupdatemod.gui.EULAScreen;

public class Utility {
    public static void openCosmicChatScreen(String initMessage) {
        if (RPMTWConfig.isEULA.get()) {
            Minecraft.getInstance().setScreen(new CosmicChatScreen());
        } else {
            Minecraft.getInstance().setScreen(new EULAScreen(initMessage));
        }
    }
}
