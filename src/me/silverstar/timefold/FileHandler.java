package me.silverstar.timefold;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.util.config.Configuration;



/**
 * TimeFold file handler
 * 
 * @author Silverstar
 */
public class FileHandler {
	public Configuration config;
	public HashMap<String, HashMap<String, String>> worlds;
	//	HashMap<WorldName, HashMap<day/night, value>>

	public FileHandler(TimeFold instance){
		loadConfig();
		processConfig();
		saveConfig();
	}

	public boolean loadConfig(){
			try {
				config = Bukkit.getServer().getPluginManager().getPlugin("TimeFold").getConfiguration();
				config.load();
			} catch (Exception e) {
				TimeFold.log.severe("#TimeFold: Could not load config! Check file permissions.");
				return false;
			}
			if(config.getAll().isEmpty()){
				config.setProperty("world0", "world");
				config.setProperty("world0days", 1);
				config.setProperty("world0nights", 1);
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
		Map<String, Object> configMap = config.getAll();
		int worldcount = 0;

		for(String a : configMap.keySet()){
			String b = a.split("^world")[1];
			if(b.matches("^[0-9]+$")){
				int c = Integer.valueOf(b);
				int d = worldcount + 1;
				if(c == d){
					worldcount = c;
				}else if(c > worldcount && c != d){
					config.setProperty("world"+d, config.getProperty("world"+c));
					config.setProperty("world"+d+"days", config.getProperty("world"+c+"days"));
					config.setProperty("world"+d+"nights", config.getProperty("world"+c+"nights"));
					config.removeProperty("world"+c);
					config.removeProperty("world"+c+"days");
					config.removeProperty("world"+c+"nights");
					TimeFold.log.info("#TimeFold: Resorted config: World \"" + config.getProperty("world"+d) + "\" from " + c + " to " + d);
				}
			}
		}
		return true;
	}
}