package me.silverstar.timefold;

import java.util.Map;
import java.util.logging.Logger;

import javax.swing.Timer;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
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
        //PluginManager pm = getServer().getPluginManager();
        //pm.registerEvent(Event.Type.BLOCK_BREAK, blockListener, Event.Priority.Normal, this);
        //pm.registerEvent(Event.Type.PLAYER_MOVE, playerListener, Event.Priority.Normal, this);
    	int timertime = 5000;
    	new TimeFoldFileHandler(this);
    	TimeFoldActionListener TimeFoldAction = new TimeFoldActionListener(this);

    	timer = new Timer(timertime, TimeFoldAction);
    	timer.setInitialDelay(10000);
    	timer.start();

        PluginDescriptionFile pdfFile = this.getDescription();
        log.info("#" + pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!");
    }
    public void onDisable(){
    	timer.stop();

        PluginDescriptionFile pdfFile = this.getDescription();
        log.info("#" + pdfFile.getName() + " version " + pdfFile.getVersion() + " is disabled!");
    }

    public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String args[]){
    	if(cmdLabel.equalsIgnoreCase("TimeFold")){
    		if(args.length == 0){
    			sender.sendMessage(getCycle(sender));    			
    		}else if(args.length == 1){
        		if(args[0].equalsIgnoreCase("get")) {
            		Long time = getTime(Bukkit.getServer().getPlayer(senderToPlayer(sender)));
            		sender.sendMessage(time.toString());
        		}else if(args[0].equalsIgnoreCase("debug")){
        			if(debug){
        				debug = false;
        				sender.sendMessage("Debug off");
        			}else if(!debug){
        				debug = true;
        				sender.sendMessage("Debug on");
        			}
        		}
    		}
    		return true;
    	}
    	return false;
    }

    String senderToPlayer(CommandSender sender){
		int start = sender.toString().indexOf("=") + 1;
		int end = sender.toString().lastIndexOf("}");
		return sender.toString().substring(start, end);
    }

    long getTime(Player player) {
    	return player.getWorld().getTime();
    }

    String getCycle(CommandSender sender){
    	int i = 0;
		boolean found = false;
    	String world = Bukkit.getServer().getPlayer(senderToPlayer(sender)).getWorld().getName();
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