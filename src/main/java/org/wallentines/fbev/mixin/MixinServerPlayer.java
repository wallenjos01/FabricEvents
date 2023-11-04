package org.wallentines.fbev.mixin;

import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.wallentines.fbev.player.ContainerCloseEvent;
import org.wallentines.midnightlib.event.Event;

@Mixin(ServerPlayer.class)
public class MixinServerPlayer {

    @Inject(method="doCloseContainer", at=@At("HEAD"))
    private void onCloseContainer(CallbackInfo ci) {
        ServerPlayer pl = (ServerPlayer) (Object) this;
        Event.invoke(new ContainerCloseEvent(pl, pl.containerMenu));
    }

}
