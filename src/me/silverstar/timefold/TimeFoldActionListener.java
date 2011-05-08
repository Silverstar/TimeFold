package me.silverstar.timefold;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.bukkit.Bukkit;
/**
 * TimeFold action listener
 * @author Silverstar
 */
public class TimeFoldActionListener implements ActionListener {
    public static TimeFold plugin;
    public static int days = 1;
    public static int nights = 1;

    public TimeFoldActionListener(TimeFold instance){
        plugin = instance;
    }

	public void actionPerformed(ActionEvent e){
		int i = 0;

		while(TimeFoldFileHandler.world[i][0] != null){
			if(Integer.valueOf(TimeFoldFileHandler.world[i][1]).intValue() > days){
				int t = (int) Bukkit.getServer().getWorld(TimeFoldFileHandler.world[i][0]).getTime();

				if(t > 12000){
					Bukkit.getServer().getWorld(TimeFoldFileHandler.world[i][0]).setTime(01);
					days++;
				}
			}else if(Integer.valueOf(TimeFoldFileHandler.world[i][1]).intValue() <= days && Integer.valueOf(TimeFoldFileHandler.world[i][2]).intValue() > nights){
				int t = (int) Bukkit.getServer().getWorld(TimeFoldFileHandler.world[i][0]).getTime();
				
				if(t > 23000){
					Bukkit.getServer().getWorld(TimeFoldFileHandler.world[i][0]).setTime(13000);
					nights++;
				}
			}
			i++;
		}
	}
}
