package org.wallentines.fbev.mixin;

import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ServerboundChatPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.server.players.PlayerList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.wallentines.fbev.player.PlayerChatEvent;
import org.wallentines.fbev.player.PlayerLeaveEvent;
import org.wallentines.midnightlib.event.Event;

@Mixin(ServerGamePacketListenerImpl.class)
public class MixinServerGamePacketListener {

    @Shadow public ServerPlayer player;


    @Redirect(method = "removePlayerFromWorld", at=@At(value="INVOKE", target="Lnet/minecraft/server/players/PlayerList;broadcastSystemMessage(Lnet/minecraft/network/chat/Component;Z)V"))
    private void onMessageSend(PlayerList instance, Component component, boolean b) {

        PlayerLeaveEvent event = new PlayerLeaveEvent(player, component);
        Event.invoke(event);

        Component comp = event.getLeaveMessage();
        if(comp != null) instance.broadcastSystemMessage(comp, false);
    }

    @Inject(method = "handleChat", cancellable = true, at=@At(value="INVOKE", target="Lnet/minecraft/server/MinecraftServer;submit(Ljava/lang/Runnable;)Ljava/util/concurrent/CompletableFuture;"))
    private void onChat(ServerboundChatPacket serverboundChatPacket, CallbackInfo ci) {

        PlayerChatEvent event = new PlayerChatEvent(serverboundChatPacket.message(), player);
        Event.invoke(event);

        if(event.isCancelled()) {
            ci.cancel();
        }
    }

}
