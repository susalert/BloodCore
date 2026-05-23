package me.susalert.bloodcore.api;

import com.projectkorra.projectkorra.event.AbilityEndEvent;

public interface AbilityEndAware {
    void onAbilityEnd(AbilityEndEvent event);
}
