package org.wallentines.fbev.player;

import net.minecraft.network.chat.PlayerChatMessage;
import net.minecraft.server.level.ServerPlayer;

public class PlayerChatEvent {

    private final String text;
    private final ServerPlayer sender;
    private boolean cancelled = false;

    public PlayerChatEvent(String originalText, ServerPlayer sender) {
        this.text = originalText;
        this.sender = sender;
    }

    public String getText() {
        return text;
    }

    public ServerPlayer getSender() {
        return sender;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
