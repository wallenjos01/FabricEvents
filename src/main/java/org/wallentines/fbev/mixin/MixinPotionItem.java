package org.wallentines.fbev.mixin;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.wallentines.fbev.entity.EntityEatEvent;
import org.wallentines.midnightlib.event.Event;

@Mixin(PotionItem.class)
public class MixinPotionItem {


    @Inject(method = "finishUsingItem", at=@At(value="HEAD"), cancellable = true)
    private void onDrink(ItemStack itemStack, Level level, LivingEntity livingEntity, CallbackInfoReturnable<ItemStack> cir) {

        if(level.isClientSide) return;

        EntityEatEvent ev = new EntityEatEvent(livingEntity, itemStack);
        Event.invoke(ev);

        if(ev.isCancelled()) {
            cir.setReturnValue(itemStack);
        }

    }

}
