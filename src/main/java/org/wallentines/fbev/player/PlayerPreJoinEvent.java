package org.wallentines.fbev.player;

import net.minecraft.server.level.ServerPlayer;

public class PlayerPreJoinEvent {


    private final ServerPlayer player;

    public PlayerPreJoinEvent(ServerPlayer player) {
        this.player = player;
    }

    public ServerPlayer getPlayer() {
        return player;
    }
}
