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
        while(TimeFoldFileHandler.worlds.get(String.valueOf(i)+"0") != null){
        	days.put(i, 1);
        	nights.put(i, 1);
        	dayscomplete.put(i, false);
        	nightscomplete.put(i, false);
        	i++;
        }
    }

	public void actionPerformed(ActionEvent e){
		int i = 0;
		
		while(TimeFoldFileHandler.worlds.get(String.valueOf(i)+"0") != null){
			int t = (int) Bukkit.getServer().getWorld(TimeFoldFileHandler.worlds.get(String.valueOf(i)+"0")).getTime();
			if(Bukkit.getServer().getWorld(TimeFoldFileHandler.worlds.get(String.valueOf(i)+"0")).getLoadedChunks().length != 0){
				if(!nightscomplete.get(i) && !dayscomplete.get(i) && Integer.valueOf(TimeFoldFileHandler.worlds.get(String.valueOf(i)+"1")) > days.get(i)){
					if(t > 11500){
						Bukkit.getServer().getWorld(TimeFoldFileHandler.worlds.get(String.valueOf(i)+"0")).setTime(01);
						days.put(i, (days.get(i)+1));
					}
				}else if(!nightscomplete.get(i) && !dayscomplete.get(i) && Integer.valueOf(TimeFoldFileHandler.worlds.get(String.valueOf(i)+"1")) <= days.get(i) && t > 13000){
					dayscomplete.put(i, true);
				}else if(dayscomplete.get(i) && !nightscomplete.get(i) && Integer.valueOf(TimeFoldFileHandler.worlds.get(String.valueOf(i)+"2")) > nights.get(i)){
					if(t > 22500){
						Bukkit.getServer().getWorld(TimeFoldFileHandler.worlds.get(String.valueOf(i)+"0")).setTime(13000);
						nights.put(i, (nights.get(i)+1));
					}	
				}else if(dayscomplete.get(i) && !nightscomplete.get(i) && Integer.valueOf(TimeFoldFileHandler.worlds.get(String.valueOf(i)+"2")) <= nights.get(i) && t > 23500){
					nightscomplete.put(i, true);
				}else if(dayscomplete.get(i) && nightscomplete.get(i)){
					days.put(i, 1);
					nights.put(i, 1);
					dayscomplete.put(i, false);
					nightscomplete.put(i, false);
				}
			}
			i++;
		}
	}
}
