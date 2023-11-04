package org.wallentines.fbev.mixin;

import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.server.PlayerAdvancements;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.wallentines.fbev.player.AdvancementEvent;
import org.wallentines.midnightlib.event.Event;

@Mixin(PlayerAdvancements.class)
public class MixinPlayerAdvancements {

    @Shadow private ServerPlayer player;

    @Inject(method="award", at=@At("RETURN"))
    private void onAward(AdvancementHolder advancement, String string, CallbackInfoReturnable<Boolean> cir) {

        AdvancementEvent event = new AdvancementEvent(player, advancement);
        Event.invoke(event);

    }

}
