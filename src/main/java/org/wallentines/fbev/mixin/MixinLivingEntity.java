package org.wallentines.fbev.mixin;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.wallentines.fbev.entity.EntityEatEvent;
import org.wallentines.midnightlib.event.Event;

@Mixin(LivingEntity.class)
public class MixinLivingEntity {

    @Inject(method = "eat", at=@At(value="INVOKE", target="Lnet/minecraft/world/level/Level;playSound(Lnet/minecraft/world/entity/player/Player;DDDLnet/minecraft/sounds/SoundEvent;Lnet/minecraft/sounds/SoundSource;FF)V"), cancellable = true)
    private void onEat(Level level, ItemStack itemStack, CallbackInfoReturnable<ItemStack> cir) {

        if(level.isClientSide()) return;

        LivingEntity le = (LivingEntity) (Object) this;

        EntityEatEvent event = new EntityEatEvent(le, itemStack);
        Event.invoke(event);

        if(event.isCancelled()) {
            le.gameEvent(GameEvent.EAT);
            cir.setReturnValue(itemStack);
        }

    }

}
