package org.wallentines.fbev.mixin;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerPlayerGameMode;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.wallentines.fbev.player.PlayerInteractBlockEvent;
import org.wallentines.midnightlib.event.Event;

@Mixin(ServerPlayerGameMode.class)
public class MixinServerPlayerGameMode {

    @Inject(method="useItemOn", at=@At(value = "HEAD"), cancellable = true)
    private void onInteractBlock(ServerPlayer serverPlayer, Level level, ItemStack itemStack, InteractionHand interactionHand, BlockHitResult blockHitResult, CallbackInfoReturnable<InteractionResult> cir) {

        PlayerInteractBlockEvent event = new PlayerInteractBlockEvent(serverPlayer, interactionHand, blockHitResult);
        Event.invoke(event);

        if(event.getResult().consumesAction()) {
            cir.setReturnValue(event.getResult());
        }
    }

}
