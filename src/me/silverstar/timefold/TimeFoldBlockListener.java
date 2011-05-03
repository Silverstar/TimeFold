package me.silverstar.timefold;

import org.bukkit.event.block.BlockListener;

/**
 * TimeFold block listener
 * @author Silverstar
 */
public class TimeFoldBlockListener extends BlockListener {
    public static TimeFold plugin;

    public TimeFoldBlockListener(TimeFold instance) {
        plugin = instance;
    }
    //
}