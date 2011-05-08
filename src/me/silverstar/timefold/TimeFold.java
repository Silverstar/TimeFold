package me.silverstar.timefold;

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
	public static TimeFold instance;
	public static final Logger log = Logger.getLogger("Minecraft");
	public static Timer timer;
	private static int timertime = 60000;
	@SuppressWarnings("unused")
	private TimeFoldFileHandler FH;
	private static String debug = "off";
    //private final TimeFoldPlayerListener playerListener = new TimeFoldPlayerListener(this);
    //private final TimeFoldBlockListener blockListener = new TimeFoldBlockListener(this);
    //private final HashMap<Player, ArrayList<Block>> tfUsers = new HashMap<Player, ArrayList<Block>>();
	private final TimeFoldActionListener TimeFoldAction = new TimeFoldActionListener(this);
	//private final TimeFoldFileHandler TimeFoldFile = new TimeFoldFileHandler(this);
	
    public void onEnable() {
        //PluginManager pm = getServer().getPluginManager();
        //pm.registerEvent(Event.Type.BLOCK_BREAK, blockListener, Event.Priority.Normal, this);
        //pm.registerEvent(Event.Type.PLAYER_MOVE, playerListener, Event.Priority.Normal, this);
    	//FileHandler("test");
    	instance = this;
    	FH = new TimeFoldFileHandler(this);
    	
    	timer = new Timer(timertime, TimeFoldAction);
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
    			sender.sendMessage(ChatColor.YELLOW + "/timefold get " + ChatColor.WHITE +"- shows the current time");
    			sender.sendMessage(ChatColor.YELLOW + "/timefold debug " + ChatColor.GRAY + "<on/off> " + ChatColor.WHITE +"- shows if debug is on/off " + ChatColor.GRAY + "<sets debug on/off>");
    		}else if(args.length == 1){
        		if(args[0].equalsIgnoreCase("get")) {
            		String sendertoplayer[] = sender.toString().split("=");
                	String player[] = sendertoplayer[1].split("}");
            		Long time = getTime(Bukkit.getServer().getPlayer(player[0]));
            		sender.sendMessage(time.toString());
            	}else if(args[0].equalsIgnoreCase("debug")){
            		sender.sendMessage("Debug is " + debug);
            	}
    		}else if(args.length == 2){
        		if(args[0].equalsIgnoreCase("debug") && args[1].equalsIgnoreCase("on")){
        			timer.stop();
        			TimeFold.timer = new Timer(10000, TimeFoldAction);
        			timer.start();
        			debug = "on";
            		sender.sendMessage("Debug is " + debug);
        		}else if(args[0].equalsIgnoreCase("debug") && args[1].equalsIgnoreCase("off")){
        			timer.stop();
        			TimeFold.timer = new Timer(timertime, TimeFoldAction);
        			timer.start();
        			debug = "off";
            		sender.sendMessage("Debug is " + debug);
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