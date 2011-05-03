package me.silverstar.timefold;

import org.bukkit.event.player.PlayerListener;

/**
 * Handle events for all Player related events
 * @author Silverstar
 */
public class TimeFoldPlayerListener extends PlayerListener {
    public static TimeFold plugin;

    public TimeFoldPlayerListener(TimeFold instance) {
        plugin = instance;
    }
    //Insert Player related code here
}