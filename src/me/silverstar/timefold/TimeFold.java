package me.silverstar.timefold;

import java.awt.event.ActionListener;
import java.util.Map;
import java.util.logging.Logger;

import javax.swing.Timer;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * TimeFold for Bukkit
 *
 * @author Silverstar
 */
public class TimeFold extends JavaPlugin {
	public static final Logger log = Logger.getLogger("Minecraft");
	public static Timer timer;
	public static boolean debug = false;

	public void onEnable() {
		new TimeFoldFileHandler(this);
		ActionListener TimeFoldActionListener = new TimeFoldActionListener(this);
		PlayerListener TimeFoldBedListener = new TimeFoldBedListener(this);

		PluginManager pm = getServer().getPluginManager();
		pm.registerEvent(Event.Type.PLAYER_BED_ENTER, TimeFoldBedListener, Event.Priority.Monitor, this);

		int timertime = 5000;

		timer = new Timer(timertime, TimeFoldActionListener);
		timer.setInitialDelay(10000);
		timer.start();

		PluginDescriptionFile pdfFile = this.getDescription();
		log.info("#TimeFold version " + pdfFile.getVersion() + " is enabled!");
	}
	public void onDisable(){
		timer.stop();

		PluginDescriptionFile pdfFile = this.getDescription();
		log.info("#TimeFold version " + pdfFile.getVersion() + " is disabled!");
	}

	public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String args[]){
		if(cmdLabel.equalsIgnoreCase("TimeFold")){
			if(sender instanceof Player){
				if(args.length == 0){
					sender.sendMessage(getCycle(((Player) sender).getWorld().getName()));
				}else if(args.length == 1){
					if(args[0].equalsIgnoreCase("report")){
						TimeFoldFileHandler.createReport();
					}
					if(args[0].equalsIgnoreCase("get")){
						sender.sendMessage(String.valueOf(((Player) sender).getWorld().getTime()));
					}else if(args[0].equalsIgnoreCase("debug")){
						if(debug){
							debug = false;
							sender.sendMessage("Debug off");
						}else if(!debug){
							debug = true;
							sender.sendMessage("Debug on");
						}
					}
				}else if(args.length == 2){
					
				}
				return true;
			}else{
				if(args.length == 0){
					log.warning("#TimeFold: no world specified!");
					log.info("#TimeFold: use \"timefold <worldname>\"");
				}else if(args.length == 1){
					String cycle;
					cycle = getCycle(args[0]);
					if(cycle.matches("(?i).*TimeFold.*")){
						log.warning("#" + cycle);
					}else{
						log.info("#TimeFold: " + cycle.replaceAll("§.", ""));
					}
				}
				return true;
			}
		}
		return false;
	}

	String getCycle(String world){
		int i = 0;
		boolean found = false;

		for(Map.Entry<String, String> entry : TimeFoldFileHandler.worlds.entrySet()){
			if(entry.getValue().equalsIgnoreCase(world)){
				i = Integer.valueOf(entry.getKey());
				found = true;
				break;
			}
		}

		if(found){
			int nights = Integer.valueOf(TimeFoldFileHandler.worlds.get(String.valueOf(i)+"2"));
			int days = Integer.valueOf(TimeFoldFileHandler.worlds.get(String.valueOf(i)+"1"));
			if(nights == 0){
				return "It's day all the time.";
			}else if(days == 0){
				return "It's always night.";
			}else if(days == 1 && nights == 1){
				return "Normal day/night cycle. Look up in the sky!";
			}else if(TimeFoldActionListener.dayscomplete.get(i)){
				StringBuilder message = new StringBuilder().append(ChatColor.WHITE).append("It's night ").append(ChatColor.YELLOW).append(TimeFoldActionListener.nights.get(i)).append(ChatColor.WHITE).append(" of ").append(ChatColor.YELLOW).append(TimeFoldFileHandler.worlds.get(i+"2"));
				return message.toString();
			}else{
				StringBuilder message = new StringBuilder().append(ChatColor.WHITE).append("It's day ").append(ChatColor.YELLOW).append(TimeFoldActionListener.days.get(i)).append(ChatColor.WHITE).append(" of ").append(ChatColor.YELLOW).append(TimeFoldFileHandler.worlds.get(i+"1"));
				return message.toString();
			}
		}else{
			return "TimeFold is not configured for this world.";
		}
	}
}