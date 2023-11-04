package org.wallentines.fbev.mixin;

import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.wallentines.fbev.player.PlayerLeaveEvent;
import org.wallentines.midnightlib.event.Event;

@Mixin(MinecraftServer.class)
public class MixinMinecraftServer {

    @Shadow private PlayerList playerList;

    @Inject(method="stopServer", at=@At(value="INVOKE", target="Lnet/minecraft/server/players/PlayerList;saveAll()V"))
    private void onSavePlayers(CallbackInfo ci) {
        for(ServerPlayer spl : playerList.getPlayers()) {
            Event.invoke(new PlayerLeaveEvent(spl, Component.empty()));
        }
    }

}
