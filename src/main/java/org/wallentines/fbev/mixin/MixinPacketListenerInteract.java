package org.wallentines.fbev.mixin;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.wallentines.fbev.player.PlayerInteractEntityEvent;
import org.wallentines.midnightlib.event.Event;

@Mixin(targets = "net/minecraft/server/network/ServerGamePacketListenerImpl$1")
public class MixinPacketListenerInteract {

    @Unique
    private Vec3 fbev$interactPos;

    @Redirect(method="performInteraction", at=@At(value="INVOKE", target="Lnet/minecraft/server/network/ServerGamePacketListenerImpl$EntityInteraction;run(Lnet/minecraft/server/level/ServerPlayer;Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/InteractionHand;)Lnet/minecraft/world/InteractionResult;"))
    private InteractionResult redirectInteract(ServerGamePacketListenerImpl.EntityInteraction instance, ServerPlayer serverPlayer, Entity entity, InteractionHand interactionHand) {

        PlayerInteractEntityEvent event = new PlayerInteractEntityEvent(serverPlayer, interactionHand, entity, fbev$interactPos);
        Event.invoke(event);

        InteractionResult res = event.getResult();
        if(!res.consumesAction()) {
            res = instance.run(serverPlayer, entity, interactionHand);
        }

        fbev$interactPos = null;

        return res;
    }

    @Inject(method="onInteraction(Lnet/minecraft/world/InteractionHand;Lnet/minecraft/world/phys/Vec3;)V", at=@At(value="HEAD"))
    private void onInteractPos(InteractionHand interactionHand, Vec3 vec3, CallbackInfo ci) {
        fbev$interactPos = vec3;
    }

}
