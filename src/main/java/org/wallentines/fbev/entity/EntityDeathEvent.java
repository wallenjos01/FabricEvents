package org.wallentines.fbev.entity;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;

public class EntityDeathEvent {

    private final LivingEntity dead;
    private final DamageSource src;

    public EntityDeathEvent(LivingEntity dead, DamageSource damageSource) {
        this.dead = dead;
        this.src = damageSource;
    }

    public LivingEntity getDead() {
        return dead;
    }

    public DamageSource getDamageSource() {
        return src;
    }
}
