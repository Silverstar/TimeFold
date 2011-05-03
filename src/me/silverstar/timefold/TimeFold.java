package me.silverstar.timefold;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Timefold for Bukkit
 *
 * @author Silverstar
 */
public class TimeFold extends JavaPlugin {
	private static final Logger log = Logger.getLogger("Minecraft");
    //private final TimeFoldPlayerListener playerListener = new TimeFoldPlayerListener(this);
    //private final TimeFoldBlockListener blockListener = new TimeFoldBlockListener(this);
    //private final HashMap<Player, ArrayList<Block>> tfUsers = new HashMap<Player, ArrayList<Block>>();

    public void onEnable() {
        //PluginManager pm = getServer().getPluginManager();
        //pm.registerEvent(Event.Type.BLOCK_BREAK, blockListener, Event.Priority.Normal, this);
        //pm.registerEvent(Event.Type.PLAYER_MOVE, playerListener, Event.Priority.Normal, this);
        
        PluginDescriptionFile pdfFile = this.getDescription();
        log.info( pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!" );
    }
    public void onDisable() {
        PluginDescriptionFile pdfFile = this.getDescription();
        log.info( pdfFile.getName() + " version " + pdfFile.getVersion() + " is disabled!" );
    }
    
    public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String args[]){
    	if(cmdLabel.equalsIgnoreCase("TimeFold")){
    		if(args.length == 0){
    			sender.sendMessage(ChatColor.YELLOW + "/timefold get " + ChatColor.WHITE +"- shows the current time");
    		}else{
        		if(args[0].equalsIgnoreCase("get")){
            		String sendertoplayer[] = sender.toString().split("=");
                	String player[] = sendertoplayer[1].split("}");
            		Long time = getTime(Bukkit.getServer().getPlayer(player[0]));
            		sender.sendMessage(time.toString());
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
}