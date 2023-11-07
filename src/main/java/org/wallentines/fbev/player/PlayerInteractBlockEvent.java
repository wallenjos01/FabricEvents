package org.wallentines.fbev.player;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.phys.BlockHitResult;

public class PlayerInteractBlockEvent {

    private final ServerPlayer player;
    private final InteractionHand hand;
    private final BlockHitResult blockPos;
    private InteractionResult result;

    public PlayerInteractBlockEvent(ServerPlayer player, InteractionHand hand, BlockHitResult blockPos) {
        this.player = player;
        this.hand = hand;
        this.blockPos = blockPos;
        this.result = InteractionResult.PASS;
    }

    public ServerPlayer getPlayer() {
        return player;
    }

    public InteractionHand getHand() {
        return hand;
    }

    public BlockHitResult getBlockPos() {
        return blockPos;
    }

    public InteractionResult getResult() {
        return result;
    }

    public void setResult(InteractionResult result) {
        this.result = result;
    }
}
