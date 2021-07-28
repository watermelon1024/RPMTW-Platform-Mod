package siongsng.rpmtwupdatemod.packs;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.SplashOverlay;
import net.minecraft.client.resource.ResourceReloadLogger;
import net.minecraft.resource.ProfiledResourceReload;
import net.minecraft.resource.ReloadableResourceManagerImpl;
import net.minecraft.util.Util;
import org.apache.commons.io.FileUtils;
import siongsng.rpmtwupdatemod.RpmtwUpdateMod;
import siongsng.rpmtwupdatemod.function.SendMsg;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ReloadPack {
    final static Path PackDir = Paths.get(System.getProperty("user.home") + "/.rpmtw/1.17");
    final static Path PackFile = PackDir.resolve("RPMTW-1.17.zip");

    public ReloadPack() {
        SendMsg.send("正在執行翻譯包更新中，請稍後...");
        Thread thread = new Thread(() -> {
            try {
                FileUtils.copyURLToFile(new URL(RpmtwUpdateMod.PackDownloadUrl), PackFile.toFile()); //下載資源包檔案
                reloadLanguage();
                MinecraftClient.getInstance().reloadResources();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            SendMsg.send("§b處理完成。");
        });
        thread.start();
    }

    private CompletableFuture<Void> reloadLanguage() {
        var mc = MinecraftClient.getInstance();
        var completableFeature = new CompletableFuture<Void>();
        var resourceManager = mc.getResourceManager();
        var resourcePackManager = mc.getResourcePackManager();
        var langManager = mc.getLanguageManager();
        var list = resourcePackManager.createResourcePacks();
        if (resourceManager instanceof ReloadableResourceManagerImpl rm) {
            mc.resourceReloadLogger.reload(ResourceReloadLogger.ReloadReason.MANUAL, list);

            rm.clear();

            for (var pack : list) {
                try {
                    rm.addPack(pack);
                } catch (Exception e) {
                    e.printStackTrace();
                    completableFeature.complete(null);
                    return completableFeature;
                }
            }

            var profiledResourceReload = ProfiledResourceReload.create(
                    resourceManager,
                    List.of(langManager),
                    Util.getMainWorkerExecutor(),
                    mc,
                    MinecraftClient.COMPLETED_UNIT_FUTURE
            );

            mc.setOverlay(new SplashOverlay(
                    mc,
                    profiledResourceReload,
                    throwable -> Util.ifPresentOrElse(throwable, mc::handleResourceReloadException, () -> {
                        mc.worldRenderer.reload();
                        mc.resourceReloadLogger.finish();
                        completableFeature.complete(null);
                    }),
                    true)
            );

            return completableFeature;
        } else {
            throw new IllegalStateException("This method had been called in valid moment, please report this error to RPMTW Update Mod (https://www.rpmtw.ga).");
        }
    }
}
