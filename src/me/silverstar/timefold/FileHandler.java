package me.silverstar.timefold;

import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.util.config.Configuration;
import org.bukkit.util.config.ConfigurationNode;

/**
 * TimeFold file handler
 * 
 * @author Silverstar
 */

public class FileHandler {
	private static TimeFold plugin;
	public static Configuration config;

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
				config.setProperty(w+".announce.timefold.enabled", false);
				config.setProperty(w+".announce.timefold.text", "The time folds!");
				config.setProperty(w+".announce.dawn.enabled", false);
				config.setProperty(w+".announce.dawn.text", "The sun rises!");
				config.setProperty(w+".announce.dusk.enabled", false);
				config.setProperty(w+".announce.dusk.text", "The night falls...");
				TimeFold.log.info("#TimeFold: Config was empty, set default config.");
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
		int i = 0;
		Iterator<String> it = config.getKeys().iterator();
		while(it.hasNext()){
			String entry = it.next();
			ConfigurationNode node = config.getNode(entry);
			ConfigurationNode ann = node.getNode("announce");

			if(node.getInt("days", 1) <= 0 && node.getInt("nights", 1) <= 0){
				TimeFold.log.warning("#TimeFold: Misconfiguration for world \""+entry+"\" found.");
				node.setProperty("days", 1);
				node.setProperty("nights", 1);
				TimeFold.log.info("#TimeFold: Setting days & nights to 1 for "+entry);
				i++;
			}

			if(node.getInt("days", 1) == 1 && node.getInt("nights", 1) == 1 && node.getInt("dimEvery", 0) > 0){
				TimeFold.log.warning("#TimeFold: Misconfiguration for world \""+entry+"\" found.");
				node.setProperty("dimEvery", 0);
				TimeFold.log.info("#TimeFold: Setting dimEvery to 0 for "+entry);
				i++;
			}

			if(ann.getNode("timefold") == null){
				config.setProperty(entry+"announce.timefold.enabled", false);
				config.setProperty(entry+"announce.timefold.text", "The time folds!");
				i++;
			}

			if(ann.getNode("dawn") == null){
				config.setProperty(entry+"announce.dawn.enabled", false);
				config.setProperty(entry+"announce.dawn.text", "The sun rises!");
				i++;
			}

			if(ann.getNode("dusk") == null){
				config.setProperty(entry+"announce.dusk.enabled", false);
				config.setProperty(entry+"announce.dusk.text", "The night falls...");
				i++;
			}
		}

		if(i > 0){
			return false;
		}else{
			return true;
		}
	}
}