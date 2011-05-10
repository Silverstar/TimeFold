package me.silverstar.timefold;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;


/**
 * TimeFold action listener
 * @author Silverstar
 */
public class TimeFoldFileHandler {
	public static TimeFold plugin;
	public static Map<String,String> worlds = new HashMap<String,String>();
	
	public TimeFoldFileHandler(TimeFold instance){
		plugin = instance;
		int Error = 0;
		
		if(!new File("plugins" + File.separator + "TimeFold").exists()){
			try {
				new File("plugins" + File.separator + "TimeFold").mkdir();
			}catch(Exception e){
				TimeFold.log.info("#TimeFold: Error: Can't create the TimeFold directory");
				Error++;
			}
		}
		
		if(!new File("plugins" + File.separator + "TimeFold","TimeFold.settings").exists()){
			try {
				new File("plugins" + File.separator + "TimeFold","TimeFold.settings");	
			}catch(Exception e){
				TimeFold.log.info("#TimeFold: Error: Can't create the TimeFold settings file");
				Error++;
			}
		}
		
		if(Error == 0){
			readconfig();
		}
	}
	
	private void readconfig(){
		try {
			String path = "plugins" + File.separator + "TimeFold" + File.separator + "TimeFold.settings";
			FileInputStream stream = new FileInputStream(path);
			DataInputStream in = new DataInputStream(stream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			int i = 0;
			while((strLine = br.readLine()) != null){
				int k = 1;
				String line[] = strLine.split(":");	
				worlds.put(String.valueOf(i)+"0", line[0]);
				while(k <= 2){
					worlds.put(String.valueOf(i)+String.valueOf(k), line[k]);
					k++;
				}
				i++;
			}
		}catch (FileNotFoundException e){
			TimeFold.log.info("#TimeFold: Error: Can't read the TimeFold settings file");
		}catch (Exception e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}