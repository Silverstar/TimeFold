package me.silverstar.timefold;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.bukkit.Bukkit;
/**
 * TimeFold action listener
 * @author Silverstar
 */
public class TimeFoldActionListener implements ActionListener {
    public static TimeFold plugin;

    public TimeFoldActionListener(TimeFold instance) {
        plugin = instance;
    }

	public void actionPerformed(ActionEvent e) {
		Bukkit.getServer().broadcastMessage("Test");
		
	}
}
