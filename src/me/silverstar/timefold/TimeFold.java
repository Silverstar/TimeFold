package me.silverstar.timefold;

import java.util.logging.Logger;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * TimeFold for Bukkit
 *
 * @author Silverstar
 */

public class TimeFold extends JavaPlugin {
	public final TimeFold plugin = this;
	public static final Logger log = Logger.getLogger("Minecraft");
//	public static Timer timer;
//	public static boolean debug = false;
//	public static CommandSender debugreceiver;

	public void onEnable() {
		new FileHandler(this);
//		ActionListener TimeFoldActionListener = new TimeFoldActionListener(this);
//		PlayerListener TimeFoldBedListener = new TimeFoldBedListener(this);
//
//		PluginManager pm = getServer().getPluginManager();
//		pm.registerEvent(Event.Type.PLAYER_BED_ENTER, TimeFoldBedListener, Event.Priority.Monitor, this);
//
//		int timertime = 5000;
//
//		timer = new Timer(timertime, TimeFoldActionListener);
//		timer.setInitialDelay(10000);
//		timer.start();

		PluginDescriptionFile pdfFile = this.getDescription();
		log.info("#TimeFold version " + pdfFile.getVersion() + " is enabled!");
	}
	public void onDisable(){
//		timer.stop();

		PluginDescriptionFile pdfFile = this.getDescription();
		log.info("#TimeFold version " + pdfFile.getVersion() + " is disabled!");
	}

//	public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String args[]){
//		if(cmdLabel.equalsIgnoreCase("TimeFold")){
//			if(args.length == 0){
//				if(sender instanceof Player){
//					sender.sendMessage(getCycle(findWorld(((Player) sender).getWorld().getName())));
//				}else{
//					sender.sendMessage("No world specified!");
//					sender.sendMessage("Use \"timefold <worldname>\"");
//				}
//			}else if(args.length == 1){
//				if(args[0].equalsIgnoreCase("report")){
//					FileHandler.createReport(sender);
//				}else if(args[0].equalsIgnoreCase("debug")){
//					if(debug){
//						debug = false;
//						debugreceiver = null;
//						sender.sendMessage("Debug off");
//					}else if(!debug){
//						debug = true;
//						debugreceiver = sender;
//						sender.sendMessage("Debug on");
//					}
//				}else if(sender instanceof Player){
//					if(sender.isOp() && args[0].equalsIgnoreCase("getraw")){
//						sender.sendMessage(String.valueOf(((Player) sender).getWorld().getTime()));
//					}else if(sender.isOp() && args[0].equalsIgnoreCase("push")){
//						if(findWorld(((Player) sender).getWorld().getName()) != -1){
//							int i = findWorld(((Player) sender).getWorld().getName());
//							if(TimeFoldActionListener.dayscomplete.get(i)){
//								TimeFoldActionListener.nights.put(i, (TimeFoldActionListener.nights.get(i)+1));
//							}else if(TimeFoldActionListener.nightscomplete.get(i)){
//								TimeFoldActionListener.days.put(i, (TimeFoldActionListener.days.get(i)+1));
//							}
//						}
//					}
//				}else{
//					sender.sendMessage(getCycle(findWorld(args[0])));
//				}
//			}
//			return true;
//		}
//		return false;
//	}
//
//	int findWorld(String world){
//		int i = 0;
//		boolean found = false;
//
//		for(Map.Entry<String, String> entry : FileHandler.worlds.entrySet()){
//			if(entry.getValue().equalsIgnoreCase(world)){
//				i = Integer.valueOf(entry.getKey());
//				found = true;
//				break;
//			}
//		}
//
//		if(found){
//			return i;
//		}
//		return -1;
//	}
//
//	String getCycle(int i){
//		if(i != -1){
//			int nights = Integer.valueOf(FileHandler.worlds.get(String.valueOf(i)+"2"));
//			int days = Integer.valueOf(FileHandler.worlds.get(String.valueOf(i)+"1"));
//			if(nights == 0){
//				return "It's day all the time.";
//			}else if(days == 0){
//				return "It's always night.";
//			}else if(days == 1 && nights == 1){
//				return "Normal day/night cycle. Look up in the sky!";
//			}else if(TimeFoldActionListener.dayscomplete.get(i)){
//				StringBuilder message = new StringBuilder().append(ChatColor.WHITE).append("It's night ").append(ChatColor.YELLOW).append(TimeFoldActionListener.nights.get(i)).append(ChatColor.WHITE).append(" of ").append(ChatColor.YELLOW).append(FileHandler.worlds.get(i+"2"));
//				return message.toString();
//			}else{
//				StringBuilder message = new StringBuilder().append(ChatColor.WHITE).append("It's day ").append(ChatColor.YELLOW).append(TimeFoldActionListener.days.get(i)).append(ChatColor.WHITE).append(" of ").append(ChatColor.YELLOW).append(FileHandler.worlds.get(i+"1"));
//				return message.toString();
//			}
//		}else{
//			return "TimeFold is not configured for this world.";
//		}
//	}
}