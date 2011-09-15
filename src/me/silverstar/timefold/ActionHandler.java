package me.silverstar.timefold;

import java.util.HashMap;
import java.util.Iterator;

import org.bukkit.Bukkit;

/**
 * TimeFold action handler
 * 
 * @author Silverstar
 */

public class ActionHandler {
	private static TimeFold plugin;
	public static HashMap<String, Boolean> isDay = new HashMap<String, Boolean>();
	public static HashMap<String, Boolean> isNight = new HashMap<String, Boolean>();
	public static HashMap<String, Integer> elDays = new HashMap<String, Integer>();
	public static HashMap<String, Integer> elNights = new HashMap<String, Integer>();
	private static HashMap<String, Boolean> isDimmed = new HashMap<String, Boolean>();
	private static HashMap<String, Integer> sinceDim = new HashMap<String, Integer>();
	private static HashMap<String, Boolean> only = new HashMap<String, Boolean>();
	private static HashMap<String, Integer> days = new HashMap<String, Integer>();
	private static HashMap<String, Integer> nights = new HashMap<String, Integer>();

	public ActionHandler(TimeFold instance){
		plugin = instance;

		Iterator<String> it = FileHandler.config.getKeys().iterator();
		while(it.hasNext()){
			String entry = it.next();
			days.put(entry, (Integer) FileHandler.config.getNode(entry).getProperty("days"));
			nights.put(entry, FileHandler.config.getNode(entry).getInt("nights", 1));

			if(days.get(entry) > 0 && nights.get(entry) == 0){
				isDay.put(entry, true);
				isNight.put(entry, false);
				only.put(entry, true);
			}else if(days.get(entry) == 0 && nights.get(entry) > 0){
				isDay.put(entry, false);
				isNight.put(entry, true);
				only.put(entry, true);
			}else if(days.get(entry) > 0 && nights.get(entry) > 0){
				isDay.put(entry, true);
				isNight.put(entry, false);
				only.put(entry, false);
			}else{
				TimeFold.log.severe("#TimeFold: Impossible configuration occured!");
				TimeFold.log.severe("#TimeFold: Check your config file and report to the author!");
			}

			isDimmed.put(entry, false);
			sinceDim.put(entry, 0);
			elDays.put(entry, 1);
			elNights.put(entry, 1);
		}

		plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
			public void run() {
				action();
			}
		}, 200L, 20L);
	}

	public void action(){
		Iterator<String> it = FileHandler.config.getKeys().iterator();
		String entry;
		int t;

		while(it.hasNext()){
			entry = it.next();
			t = (int) Bukkit.getServer().getWorld(entry).getTime();

			if(Bukkit.getServer().getWorld(entry) != null){
				if(isDay.get(entry) && t > 11500 && t < 12000){
					if(days.get(entry) > elDays.get(entry) || nights.get(entry) == 0){
						Bukkit.getServer().getWorld(entry).setTime(0);
						elDays.put(entry, (elDays.get(entry)+1));
					}else if(days.get(entry) == elDays.get(entry)){
						isDay.put(entry, false);
						isNight.put(entry, true);
						elNights.put(entry, 1);
					}
				}else if(t > 22500 && t < 23000 && isNight.get(entry)){
					if(nights.get(entry) > elNights.get(entry) || days.get(entry) == 0){
						Bukkit.getServer().getWorld(entry).setTime(13500);
						elNights.put(entry, (elNights.get(entry)+1));
					}else if(nights.get(entry) == elNights.get(entry)){
						isNight.put(entry, false);
						isDay.put(entry, true);
						elDays.put(entry, 1);
					}
				}
			}
		}
	}
}
