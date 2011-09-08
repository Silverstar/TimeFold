package me.silverstar.timefold;

import org.bukkit.Bukkit;
import org.bukkit.util.config.Configuration;



/**
 * TimeFold file handler
 * 
 * @author Silverstar
 */

public class FileHandler {
	private TimeFold plugin = null;
	public Configuration config;

	public FileHandler(TimeFold instance){
		plugin = instance;
		loadConfig();
		processConfig();
		saveConfig();
	}

	public boolean loadConfig(){
			try {
				config = plugin.getConfiguration();
				config.load();
			} catch (Exception e) {
				TimeFold.log.severe("#TimeFold: Could not load config! Check file permissions.");
				return false;
			}
			if(config.getAll().isEmpty()){
				String w = Bukkit.getServer().getWorlds().listIterator().next().getName();
				config.setProperty(w+".days", 1);
				config.setProperty(w+".nights", 1);
				config.setProperty(w+".dimEvery", 0);
				saveConfig();
				TimeFold.log.info("#TimeFold: Config was empty, wrote default config.");
			}
			TimeFold.log.info("#TimeFold: Config loaded.");
			return true;
	}

	public boolean saveConfig(){
		try {
			config.save();
		} catch (Exception e) {
			return false;
		}
		TimeFold.log.info("#TimeFold: Config saved.");
		return true;
	}

	public boolean processConfig(){
//		Map<String, Object> configMap = config.getAll();
//		int worldcount = 0;
//
//		for(String a : configMap.keySet()){
//			String b = a.split("^world")[1];
//			if(b.matches("^[0-9]+$")){
//				int c = Integer.valueOf(b);
//				int d = worldcount + 1;
//				if(c == d){
//					worldcount = c;
//				}else if(c > worldcount && c != d){
//					config.setProperty("world"+d, config.getProperty("world"+c));
//					config.setProperty("world"+d+"days", config.getProperty("world"+c+"days"));
//					config.setProperty("world"+d+"nights", config.getProperty("world"+c+"nights"));
//					config.removeProperty("world"+c);
//					config.removeProperty("world"+c+"days");
//					config.removeProperty("world"+c+"nights");
//					TimeFold.log.info("#TimeFold: Resorted config: World \"" + config.getProperty("world"+d) + "\" from " + c + " to " + d);
//				}
//			}
//		}
		return false;
	}
}