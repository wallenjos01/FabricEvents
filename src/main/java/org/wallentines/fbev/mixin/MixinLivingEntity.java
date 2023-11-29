package org.wallentines.fbev.mixin;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.wallentines.fbev.entity.EntityDamageEvent;
import org.wallentines.fbev.entity.EntityDeathEvent;
import org.wallentines.fbev.entity.EntityEatEvent;
import org.wallentines.midnightlib.event.Event;

@Mixin(LivingEntity.class)
public class MixinLivingEntity {

    @Unique
    private float fbev$lastDamage = 0.0f;

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

    @Inject(method="hurt", at=@At(value="INVOKE", target="Lnet/minecraft/world/entity/LivingEntity;isSleeping()Z"), cancellable = true)
    private void onHurt(DamageSource damageSource, float f, CallbackInfoReturnable<Boolean> cir) {

        LivingEntity le = (LivingEntity) (Object) this;

        EntityDamageEvent event = new EntityDamageEvent(le, damageSource, f);
        Event.invoke(event);

        fbev$lastDamage = event.getAmount();

        if(event.isCancelled()) {
            cir.setReturnValue(false);
        }
    }

    @ModifyVariable(method="hurt", at=@At(value="FIELD", target="Lnet/minecraft/world/entity/LivingEntity;noActionTime:I", opcode = Opcodes.PUTFIELD), ordinal = 0, argsOnly = true)
    private float modifyHurt(float f, DamageSource src) {
        return fbev$lastDamage;
    }

    @Inject(method="die", at=@At(value="INVOKE", target="Lnet/minecraft/world/damagesource/DamageSource;getEntity()Lnet/minecraft/world/entity/Entity;"))
    private void onDeath(DamageSource damageSource, CallbackInfo ci) {

        LivingEntity le = (LivingEntity) (Object) this;
        if(le.level().isClientSide) return;

        EntityDeathEvent event = new EntityDeathEvent(le, damageSource);
        Event.invoke(event);
    }

}
