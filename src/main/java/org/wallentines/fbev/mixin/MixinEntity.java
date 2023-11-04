package org.wallentines.fbev.mixin;

import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.wallentines.fbev.entity.EntityDismountVehicleEvent;
import org.wallentines.midnightlib.event.Event;

@Mixin(Entity.class)
public class MixinEntity {

    @Shadow @Nullable private Entity vehicle;
    @Unique
    private Entity fbev$lastVehicle;

    @Inject(method="removeVehicle", at=@At(value = "HEAD"))
    private void onDismount(CallbackInfo ci) {

        fbev$lastVehicle = vehicle;
    }

    @Inject(method="removeVehicle", at=@At(value = "INVOKE", target="Lnet/minecraft/world/entity/Entity;removePassenger(Lnet/minecraft/world/entity/Entity;)V", shift = At.Shift.AFTER))
    private void afterRemove(CallbackInfo ci) {

        if(fbev$lastVehicle != null) {

            Entity ent = (Entity) (Object) this;
            EntityDismountVehicleEvent ev = new EntityDismountVehicleEvent(fbev$lastVehicle, ent);
            Event.invoke(ev);

            if(ev.isCancelled() && !ev.getVehicle().isRemoved()) {
                ent.startRiding(fbev$lastVehicle, true);
            }

        }
        fbev$lastVehicle = null;
    }

}
