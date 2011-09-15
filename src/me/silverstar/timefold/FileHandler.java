package me.silverstar.timefold;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
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

	public static boolean loadConfig(){
			try{
				config = plugin.getConfiguration();
				config.load();
			}catch(Exception e){
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

	public static boolean saveConfig(){
		try{
			config.save();
		}catch(Exception e){
			return false;
		}

		TimeFold.log.info("#TimeFold: Config saved.");
		return true;
	}

	public static boolean processConfig(){
		int i = 0;
		Iterator<String> it = config.getKeys().iterator();
		while(it.hasNext()){
			String entry = it.next();
			ConfigurationNode node = config.getNode(entry);

			if(node.getProperty("days") == null){
				node.setProperty("days", 1);
			}

			if(node.getProperty("nights") == null){
				node.setProperty("nights", 1);
			}

			if(node.getProperty("dimEvery") == null){
				node.setProperty("dimEvery", 0);
			}

			if(node.getNode("announce.timefold").getProperty("enabled") == null){
				node.setProperty("announce.timefold.enabled", false);
			}

			if(node.getNode("announce.timefold").getProperty("text") == null){
				node.setProperty("announce.timefold.text", "The time folds!");
			}

			if(node.getNode("announce.dawn").getProperty("enabled") == null){
				node.setProperty("announce.dawn.enabled", false);
			}

			if(node.getNode("announce.dawn").getProperty("text") == null){
				node.setProperty("announce.dawn.text", "The sun rises!");
			}

			if(node.getNode("announce.dusk").getProperty("enabled") == null){
				node.setProperty("announce.dusk.enabled", false);
			}

			if(node.getNode("announce.dusk").getProperty("text") == null){
				node.setProperty("announce.dusk.text", "The night falls...");
			}

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
		}

		if(i > 0){
			return false;
		}else{
			return true;
		}
	}

	public static boolean createReport(CommandSender sender) {
		File dir = new File("plugins" + File.separator + "TimeFold");
		File fl = new File("plugins" + File.separator + "TimeFold" + File.separator + "TimeFold_report.txt");

		if(!dir.exists()){
			if(!dir.mkdir()){
				TimeFold.log.severe("#TimeFold: Can't create TimeFold directory, check file/path permissions!");
				if(sender instanceof Player){
					sender.sendMessage("An error occured. Check log file.");
				}
				return false;
			}
		}

		if(fl.exists()){
			TimeFold.log.info("#TimeFold: Found old reports file, deleting...");
			if(!fl.delete()){
				TimeFold.log.severe("#TimeFold: Can't delete old reports file. Check file/path permissions!");
				if(sender instanceof Player){
					sender.sendMessage("An error occured. Check log file.");
				}
				return false;
			}
		}

		try{
			BufferedWriter bw = new BufferedWriter(new FileWriter(fl));
			bw.write("Server Version " + Bukkit.getServer().getVersion());
			bw.newLine();
			bw.write("TimeFold Version" + plugin.getDescription().getVersion());
			bw.newLine();
			bw.write("Worlds:");
			bw.newLine();
			Iterator<World> itw = Bukkit.getServer().getWorlds().iterator();
			while(itw.hasNext()){
				bw.write(itw.next().getName() + " # ");
			}
			bw.newLine();
			bw.write("Config:");
			bw.newLine();
			Iterator<String> itc = config.getKeys().iterator();
			while(itc.hasNext()){
				String entry = itc.next();
				ConfigurationNode node = config.getNode(entry);
				bw.write(entry + ":" + node.getInt("days", 1) + ":" + node.getInt("nights", 1) + ":" + node.getInt("dimEvery", 0));
				bw.newLine();
				bw.write(node.getNode("announce.timefold").getBoolean("enabled", false) + ":" + node.getNode("announce.timefold").getString("text", "report"));
				bw.newLine();
				bw.write(node.getNode("announce.dawn").getBoolean("enabled", false) + ":" + node.getNode("announce.dawn").getString("text", "report"));
				bw.newLine();
				bw.write(node.getNode("announce.dusk").getBoolean("enabled", false) + ":" + node.getNode("announce.dusk").getString("text", "report"));
			}

		}catch(IOException e){
			TimeFold.log.severe("#TimeFold: Error: Can't write the reports file. Check file/path permissions.");
		}
		
		return true;
	}
}