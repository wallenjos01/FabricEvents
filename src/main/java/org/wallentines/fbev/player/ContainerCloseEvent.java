package org.wallentines.fbev.player;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.AbstractContainerMenu;

public class ContainerCloseEvent {

    private final ServerPlayer player;
    private final AbstractContainerMenu menu;

    public ContainerCloseEvent(ServerPlayer player, AbstractContainerMenu menu) {
        this.player = player;
        this.menu = menu;
    }

    public ServerPlayer getPlayer() {
        return player;
    }

    public AbstractContainerMenu getMenu() {
        return menu;
    }

}
