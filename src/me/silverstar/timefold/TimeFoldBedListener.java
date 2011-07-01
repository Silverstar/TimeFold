package me.silverstar.timefold;

import java.util.Iterator;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerListener;
/**
 * TimeFold bed listener
 * @author Silverstar
 */
public class TimeFoldBedListener extends PlayerListener {
	public TimeFold plugin;

	public TimeFoldBedListener(TimeFold instance){
		plugin = instance;
	}

	@Override
	public void onPlayerBedEnter(PlayerBedEnterEvent e){
		int i = 0;
		int j = 99;
		boolean found = false;

		while(TimeFoldFileHandler.worlds.get(i+"0") != null){
			if(TimeFoldFileHandler.worlds.get(i+"0").equalsIgnoreCase(e.getPlayer().getWorld().getName())){
				j = i;
				found = true;
				break;
			}
			i++;
		}

		if(!found){
			TimeFold.log.warning("#TimeFold: Oops! Didn't find the world TFBL");
		}else if(found){
			allasleep(e, j);
		}
	}

	public void allasleep(PlayerBedEnterEvent e, int j){
		Iterator<Player> players = (e.getPlayer().getWorld().getPlayers()).iterator();
		boolean allasleep = true;
		while(players.hasNext()){
			Player player = players.next();
			if(player.isOnline() && !(player.isSleepingIgnored() || player.isSleeping())){
				e.getPlayer().sendMessage("On" + String.valueOf(player.isOnline() + " Ig" + String.valueOf(player.isSleepingIgnored()) + " Sl" + String.valueOf(player.isSleeping())));
				allasleep = false;
			}
		}

		if(allasleep){
			e.getPlayer().sendMessage("all asleep");
			TimeFoldActionListener.nightscomplete.put(j, true);
			TimeFoldActionListener.days.put(j, 1);
			TimeFoldActionListener.dayscomplete.put(j, false);
		}else{
			e.getPlayer().sendMessage("not all asleep");
		}
	}
}