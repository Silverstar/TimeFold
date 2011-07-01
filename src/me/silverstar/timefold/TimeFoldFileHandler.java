package me.silverstar.timefold;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;


/**
 * TimeFold file handler
 * @author Silverstar
 */
public class TimeFoldFileHandler {
	public static Map<String,String> worlds = new HashMap<String,String>();

	public TimeFoldFileHandler(TimeFold instance){
		int Error = 0;

		if(!new File("plugins" + File.separator + "TimeFold").exists()){
			try {
				new File("plugins" + File.separator + "TimeFold").mkdir();
			}catch(Exception e){
				TimeFold.log.severe("#TimeFold: Can't create the TimeFold directory");
				Error++;
			}
		}

		String path = "plugins" + File.separator + "TimeFold" + File.separator + "TimeFold.settings";
		if(!new File(path).exists()){
			try {
				new File(path);
				FileOutputStream stream = new FileOutputStream(path);
				DataOutputStream out = new DataOutputStream(stream);
				BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out));
				bw.write(Bukkit.getServer().getWorlds().get(0).getName().toString() + ":1:1");
				bw.close();
			}catch(Exception e){
				TimeFold.log.severe("#TimeFold: Can't create the TimeFold settings file");
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
				if(checkWorld(line[0])){
					worlds.put(String.valueOf(i)+"0", line[0]);
					while(k <= 2){
						worlds.put(String.valueOf(i)+String.valueOf(k), line[k]);
						k++;
					}
					if(Integer.valueOf(worlds.get(String.valueOf(i)+"1")) == 0 && Integer.valueOf(worlds.get(String.valueOf(i)+"2")) == 0){
						TimeFold.log.warning("#TimeFold: Misconfiguration for world \"" + line[0] + "\" found");
						TimeFold.log.warning("#TimeFold: Setting days:nights to 1:1 for " + line[0]);
						worlds.put((String.valueOf(i)+"1"), "1");
						worlds.put((String.valueOf(i)+"2"), "1");
					}
					i++;
				}
			}
		}catch (FileNotFoundException e){
			TimeFold.log.severe("#TimeFold: Can't find the TimeFold settings file");
		}catch (Exception e){
			TimeFold.log.severe("#TimeFold: Error while reading the TimeFold settings file");
		}
	}

	private boolean checkWorld(String world){
		if(Bukkit.getServer().getWorld(world) != null){
			return true;
		}
		return false;
	}

	public static boolean createReport(CommandSender sender){
		String path = "plugins" + File.separator + "TimeFold" + File.separator + "TimeFold_report.txt";
		if(new File(path).exists()){
			try {
				new File(path).delete();
			} catch (Exception e) {
				TimeFold.log.severe("#TimeFold: Can't delete the TimeFold reports file");
				return false;
			}
		}
		try {
			new File(path);
			FileOutputStream stream = new FileOutputStream(path);
			DataOutputStream out = new DataOutputStream(stream);
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out));
			bw.write("Server Version " + Bukkit.getServer().getVersion());
			bw.newLine();
			bw.write("TimeFold Version " + Bukkit.getServer().getPluginManager().getPlugin("TimeFold").getDescription().getVersion());
			bw.newLine();
			bw.write("Loaded worlds: " + Bukkit.getServer().getWorlds().toString());
			bw.newLine();
			bw.write("Configured worlds: " + worlds.toString());
			bw.close();
		} catch (Exception e) {
			TimeFold.log.severe("#TimeFold: Can't create the TimeFold reports file");
			return false;
		}
		sender.sendMessage("Report successfully created in the TimeFold directory!");
		return true;
	}
}