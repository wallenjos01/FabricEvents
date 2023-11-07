package org.wallentines.fbev.mixin;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.wallentines.fbev.player.PlayerInteractEvent;
import org.wallentines.midnightlib.event.Event;

@Mixin(ItemStack.class)
public class MixinItemStack {


    @Inject(method="use",at=@At("HEAD"),cancellable = true)
    private void onUse(Level level, Player player, InteractionHand interactionHand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir) {
        if(player.level().isClientSide()) return;

        ItemStack item = player.getItemInHand(interactionHand);
        if(!item.isEmpty() && item.getItem() instanceof BucketItem) return;

        PlayerInteractEvent event = new PlayerInteractEvent((ServerPlayer) player, interactionHand);
        Event.invoke(event);

        if(event.getResult().consumesAction()) {
            cir.setReturnValue(new InteractionResultHolder<>(event.getResult(), item));
        }

    }

}
