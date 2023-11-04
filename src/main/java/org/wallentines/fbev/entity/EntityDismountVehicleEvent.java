package org.wallentines.fbev.entity;

import net.minecraft.world.entity.Entity;

public class EntityDismountVehicleEvent {

    private final Entity vehicle;
    private final Entity rider;

    private boolean cancelled = false;

    public EntityDismountVehicleEvent(Entity vehicle, Entity rider) {
        this.vehicle = vehicle;
        this.rider = rider;
    }

    public Entity getVehicle() {
        return vehicle;
    }

    public Entity getRider() {
        return rider;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
