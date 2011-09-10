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
	private static HashMap<String, Boolean> isDay;
	private static HashMap<String, Boolean> isNight;
	private static HashMap<String, Boolean> isDimmed;
	private static HashMap<String, Integer> sinceDim;
	private static HashMap<String, Integer> elDays;
	private static HashMap<String, Integer> elNights;
	private static HashMap<String, Boolean> only;

	public ActionHandler(TimeFold instance){
		plugin = instance;

		Iterator<String> it = FileHandler.config.getKeys().iterator();
		while(it.hasNext()){
			String entry = it.next();
			if(FileHandler.config.getNode(entry).getInt("days", 1) > 0 && FileHandler.config.getNode(entry).getInt("nights", 1) == 0){
				isDay.put(entry, true);
				isNight.put(entry, false);
				only.put(entry, true);
			}else if(FileHandler.config.getNode(entry).getInt("days", 1) == 0 && FileHandler.config.getNode(entry).getInt("nights", 1) > 0){
				isDay.put(entry, false);
				isNight.put(entry, true);
				only.put(entry, true);
			}else if(FileHandler.config.getNode(entry).getInt("days", 1) > 0 && FileHandler.config.getNode(entry).getInt("nights", 1) > 0){
				isDay.put(entry, true);
				isNight.put(entry, false);
				only.put(entry, false);
			}else{
				TimeFold.log.severe("#TimeFold: Impossible configuration occured!");
				TimeFold.log.severe("#TimeFold: Check your config file and report to the author!");
			}

			isDimmed.put(entry, false);
			sinceDim.put(entry, 0);
			elDays.put(entry, 0);
			elNights.put(entry, 0);
		}

		plugin.getServer().getScheduler().scheduleAsyncRepeatingTask(plugin, new Runnable() {
			public void run() {
				action();
			}
		}, 200L, 20L);
	}

	public void action(){
		Iterator<String> it = FileHandler.config.getKeys().iterator();
		String entry;
		int t;
		Boolean iDay;
		Boolean iNight;
		Boolean iDimmed;
		int sDim;
		int eDays;
		int eNights;

		while(it.hasNext()){
			entry = it.next();
			t = (int) Bukkit.getServer().getWorld(entry).getTime();
			iDay = isDay.get(entry);
			iNight = isNight.get(entry);
			iDimmed = isDimmed.get(entry);
			sDim = sinceDim.get(entry);
			eDays = elDays.get(entry);
			eNights = elNights.get(entry);

			if(Bukkit.getServer().getWorld(entry) != null){
				if(iDay && t > 11500 && t < 12000){
					if(true){
						
					}
				}
			}
		}
	}
}
