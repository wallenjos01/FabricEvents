package org.wallentines.fbev.mixin;

import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.CommonListenerCookie;
import net.minecraft.server.players.PlayerList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.wallentines.fbev.player.PlayerJoinEvent;
import org.wallentines.fbev.player.PlayerPreJoinEvent;
import org.wallentines.midnightlib.event.Event;

@Mixin(PlayerList.class)
public abstract class MixinPlayerList {

    @Shadow public abstract void broadcastSystemMessage(Component component, boolean bl);

    @Inject(method = "placeNewPlayer", at=@At("HEAD"))
    private void onJoin(Connection connection, ServerPlayer serverPlayer, CommonListenerCookie commonListenerCookie, CallbackInfo ci)
    {
        Event.invoke(new PlayerPreJoinEvent(serverPlayer));
    }

    @Redirect(method = "placeNewPlayer", at=@At(value = "INVOKE", target="Lnet/minecraft/server/players/PlayerList;broadcastSystemMessage(Lnet/minecraft/network/chat/Component;Z)V"))
    private void onSendMessage(PlayerList instance, Component component, boolean b, Connection conn, ServerPlayer spl) {

        PlayerJoinEvent event = new PlayerJoinEvent(spl, component);
        Event.invoke(event);

        Component comp = event.getJoinMessage();
        if(comp != null) {
            broadcastSystemMessage(comp, b);
        }
    }

}
