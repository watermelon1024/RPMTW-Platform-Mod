package siongsng.rpmtwupdatemod.mixins;

import net.dv8tion.jda.api.entities.TextChannel;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import siongsng.rpmtwupdatemod.discord.Chat;
import siongsng.rpmtwupdatemod.function.SendMsg;

@Mixin(ClientPlayerEntity.class)
public class OnChat {
    int times = 0;

    @Inject(at = @At("HEAD"), method = "sendChatMessage", cancellable = true)
    public void onChat(String message, CallbackInfo info) {
        if (message.equals("!")) return;
        if (message.startsWith("!")) {
            times++;
            TextChannel textChannel = Chat.bot.getTextChannelById(815819581440262146L);
            textChannel.sendMessage(String.format("[宇宙通訊] **%s** >> %s", MinecraftClient.getInstance().player.getDisplayName().getString(), message.split("^!")[1])).queue();
        }
        if (message.startsWith("!") && times == 1) {
            SendMsg.send("§b提醒您使用§9宇宙通訊§b功能需遵守§cDiscord使用者及社群條款\n§b以及§c不得以任何形式騷擾別人§b，違反者皆可能須附上§c法律責任§b。");
        }
    }
}