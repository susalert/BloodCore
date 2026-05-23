package me.susalert.bloodcore.api;

import com.projectkorra.projectkorra.event.BendingReloadEvent;

public interface BendingReloadAware {
    void onBendingReload(BendingReloadEvent event);
}
