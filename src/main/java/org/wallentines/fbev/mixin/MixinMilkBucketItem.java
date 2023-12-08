package org.wallentines.fbev.mixin;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MilkBucketItem;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.wallentines.fbev.entity.EntityEatEvent;
import org.wallentines.midnightlib.event.Event;

@Mixin(MilkBucketItem.class)
public class MixinMilkBucketItem {

    @Inject(method = "finishUsingItem", at=@At("HEAD"), cancellable = true)
    private void onDrink(ItemStack itemStack, Level level, LivingEntity livingEntity, CallbackInfoReturnable<ItemStack> cir) {

        EntityEatEvent event = new EntityEatEvent(livingEntity, itemStack);
        Event.invoke(event);

        if(event.isCancelled()) {
            cir.setReturnValue(itemStack);
        }
    }


}
