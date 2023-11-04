package org.wallentines.fbev.player;

import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.server.level.ServerPlayer;

/**
 * An event fired when a player makes an advancement
 */
public record AdvancementEvent(ServerPlayer player, AdvancementHolder advancement) { }
