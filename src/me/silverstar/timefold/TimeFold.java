package me.silverstar.timefold;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * TimeFold for Bukkit
 *
 * @author Silverstar
 */

public class TimeFold extends JavaPlugin {
	public final TimeFold plugin = this;
	public static final Logger log = Logger.getLogger("Minecraft");

	public void onEnable() {
		new FileHandler(this);
		new ActionHandler(this);
//		PlayerListener TimeFoldBedListener = new TimeFoldBedListener(this);
//
//		PluginManager pm = getServer().getPluginManager();
//		pm.registerEvent(Event.Type.PLAYER_BED_ENTER, TimeFoldBedListener, Event.Priority.Monitor, this);

		log.info("#TimeFold version " + plugin.getDescription().getVersion() + " is enabled!");
	}

	public void onDisable(){
		log.info("#TimeFold version " + plugin.getDescription().getVersion() + " is disabled!");
	}

	public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String args[]){
		if(cmdLabel.equalsIgnoreCase("TimeFold")){
			switch(args.length){
			case 0:
				if(sender instanceof Player){
					sender.sendMessage(getCycle(((Player) sender).getWorld().getName()));
				}else if(sender instanceof ConsoleCommandSender){
					sender.sendMessage("#TimeFold: No world specified!");
					sender.sendMessage("#TimeFold: Use \"timefold <worldname>\"");
					break;
				}
			case 1:
				if(sender instanceof Player){
					return false;
				}else if(sender instanceof ConsoleCommandSender){
					sender.sendMessage("#TimeFold: " + getCycle(args[0]));
					break;
				}
			}
			return true;
		}
		return false;
	}

	String getCycle(String world){
		if(FileHandler.config.getNode(world) != null){
			if(Bukkit.getServer().getWorld(world) != null){
				int days = FileHandler.config.getNode(world).getInt("days", 1);
				int nights = FileHandler.config.getNode(world).getInt("nights", 1);
				int elDays = ActionHandler.elDays.get(world);
				int elNights = ActionHandler.elNights.get(world);

				if(nights == 0){
					return "It's day all the time.";
				}else if(days == 0){
					return "Neverending darkness...";
				}else if(days == 1 && nights == 1){
					return "Normal day/night cycle. Look up in the sky!";
				}else if(ActionHandler.isDay.get(world)){
					StringBuilder message = new StringBuilder().append(ChatColor.WHITE).append("It's day ").append(ChatColor.YELLOW).append(elDays).append(ChatColor.WHITE).append(" of ").append(ChatColor.YELLOW).append(days);
					return message.toString();
				}else if(ActionHandler.isNight.get(world)){
					StringBuilder message = new StringBuilder().append(ChatColor.WHITE).append("It's night ").append(ChatColor.YELLOW).append(elNights).append(ChatColor.WHITE).append(" of ").append(ChatColor.YELLOW).append(nights);
					return message.toString();
				}else{
					return "Unknown State. Please tell the author.";
				}
			}else{
				return "World does not exist / is not loaded!";
			}
		}else{
			return "No configuration for this world.";
		}
	}
}