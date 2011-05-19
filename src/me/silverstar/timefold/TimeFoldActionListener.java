package me.silverstar.timefold;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
/**
 * TimeFold action listener
 * @author Silverstar
 */
public class TimeFoldActionListener implements ActionListener {
	public static TimeFold plugin;
	public static Map<Integer,Integer> days = new HashMap<Integer,Integer>();
	public static Map<Integer,Integer> nights = new HashMap<Integer,Integer>();
	public static Map<Integer,Boolean> dayscomplete = new HashMap<Integer,Boolean>();
	public static Map<Integer,Boolean> nightscomplete = new HashMap<Integer,Boolean>();

    public TimeFoldActionListener(TimeFold instance){
    	plugin = instance;
    	int i = 0;
    	int worlddays;
    	int worldnights;

        while(TimeFoldFileHandler.worlds.get(String.valueOf(i)+"0") != null){
        	days.put(i, 1);
        	nights.put(i, 1);
        	worlddays = Integer.valueOf(TimeFoldFileHandler.worlds.get(String.valueOf(i)+"1"));
        	worldnights = Integer.valueOf(TimeFoldFileHandler.worlds.get(String.valueOf(i)+"2"));

    			if(worlddays > 0){
    				dayscomplete.put(i, false);
    			}else if(worlddays == 0){
    	        	dayscomplete.put(i, true);
    			}
    			if(worldnights > 0){
    				nightscomplete.put(i, false);
    			}else if(worldnights == 0){
    	        	nightscomplete.put(i, true);
    			}
        	i++;
        }
    }

	public void actionPerformed(ActionEvent e){
		int i = 0;
		int t;
    	int worlddays;
    	int worldnights;
    	String worldname;
    	boolean daycomplete;
    	boolean nightcomplete;
    	int day;
    	int night;

		while(TimeFoldFileHandler.worlds.get(String.valueOf(i)+"0") != null){
			worlddays = Integer.valueOf(TimeFoldFileHandler.worlds.get(String.valueOf(i)+"1"));
        	worldnights = Integer.valueOf(TimeFoldFileHandler.worlds.get(String.valueOf(i)+"2"));
        	worldname = TimeFoldFileHandler.worlds.get(String.valueOf(i)+"0");
        	t = (int) Bukkit.getServer().getWorld(worldname).getTime();
        	daycomplete = dayscomplete.get(i);
        	nightcomplete = nightscomplete.get(i);
        	day = days.get(i);
        	night = nights.get(i);

			if(TimeFold.debug){
				actiondebug("before:", worlddays, worldnights, worldname, t, daycomplete, nightcomplete, day, night);
			}

        	if(Bukkit.getServer().getWorld(worldname).getLoadedChunks().length != 0){
        		if(!daycomplete && t > 11500 && t < 12000){
       				if(worlddays > day || worldnights == 0){
           				Bukkit.getServer().getWorld(worldname).setTime(01);
           				days.put(i, (day+1));
       				}else if(worlddays == day){
       					dayscomplete.put(i, true);
       					nights.put(i, 1);
       					nightscomplete.put(i, false);
       				}
        		}else if(t > 22500 && t < 23000 && !nightcomplete){
       				if(worldnights > night || worlddays == 0){
       					Bukkit.getServer().getWorld(worldname).setTime(13500);
       					nights.put(i, (night+1));
       				}else if(worldnights == night){
       					nightscomplete.put(i, true);
       					days.put(i, 1);
       					dayscomplete.put(i, false);
       				}
        		}
        	}
			i++;

			if(TimeFold.debug){
				actiondebug("after:", worlddays, worldnights, worldname, t, daycomplete, nightcomplete, day, night);
			}
		}
	}

	void actiondebug(String when, int worlddays, int worldnights, String worldname, int t, boolean daycomplete, boolean nightcomplete, int day, int night){
		Bukkit.getServer().broadcastMessage(when + Integer.valueOf(worlddays).toString() + Integer.valueOf(worldnights) + worldname + Integer.valueOf(t) + String.valueOf(daycomplete) + String.valueOf(nightcomplete) + Integer.valueOf(day) + Integer.valueOf(night));
	}
}
