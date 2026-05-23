package me.susalert.bloodcore.api;

import com.projectkorra.projectkorra.event.PlayerBindChangeEvent;

public interface BindChangeAware {
    void onBindChange(PlayerBindChangeEvent event);
}
