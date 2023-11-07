package org.wallentines.fbev.player;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

public class PlayerInteractEntityEvent {

    private final ServerPlayer player;
    private final Entity entity;
    private final InteractionHand hand;
    private final Vec3 position;
    private InteractionResult result;

    public PlayerInteractEntityEvent(ServerPlayer player, InteractionHand hand, Entity entity, Vec3 position) {
        this.player = player;
        this.hand = hand;
        this.entity = entity;
        this.position = position;
        this.result = InteractionResult.PASS;
    }

    public ServerPlayer getPlayer() {
        return player;
    }

    public InteractionHand getHand() {
        return hand;
    }

    public Entity getEntity() {
        return entity;
    }

    public Vec3 getPosition() {
        return position;
    }

    public InteractionResult getResult() {
        return result;
    }

    public void setResult(InteractionResult result) {
        this.result = result;
    }
}
