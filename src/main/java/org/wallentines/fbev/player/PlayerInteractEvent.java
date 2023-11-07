package org.wallentines.fbev.player;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;

public class PlayerInteractEvent {

    private final ServerPlayer player;
    private final InteractionHand hand;

    private InteractionResult result;

    public PlayerInteractEvent(ServerPlayer player, InteractionHand hand) {
        this.player = player;
        this.hand = hand;
        this.result = InteractionResult.PASS;
    }

    public ServerPlayer getPlayer() {
        return player;
    }

    public InteractionHand getHand() {
        return hand;
    }

    public InteractionResult getResult() {
        return result;
    }

    public void setResult(InteractionResult result) {
        this.result = result;
    }
}
