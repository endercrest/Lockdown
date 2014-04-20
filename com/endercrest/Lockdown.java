package com.endercrest;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Lockdown extends JavaPlugin {

	public int level = -1;
	public String lockdown;
	public String prefix = ChatColor.GOLD + "[" + ChatColor.BLUE + "Lockdown" + ChatColor.GOLD + "]" + ChatColor.WHITE;
	
	@Override
	public void onEnable(){
		this.saveDefaultConfig();
		getServer().getPluginManager().registerEvents(new LoginEvent(this), this);
	}
	
	@Override
	public void onDisable(){
		
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if(cmd.getName().equalsIgnoreCase("lock")){
			if(sender instanceof Player){
				Player p = (Player)sender;
				if(p.hasPermission("lock.set")){
					//Message for how to use command
					if(args.length == 0){
						p.sendMessage(prefix + ChatColor.GREEN + "/lock (disable/status/[type]");
						return true;
					}else if(args.length == 1){
						if(args[0].equalsIgnoreCase("disable")){
							if(level != -1){
							p.sendMessage(prefix + ChatColor.GREEN + "Lockdown has been disabled!");
							level = -1;
							lockdown = null;
							}else{
								p.sendMessage(prefix + ChatColor.RED + "The server was not in lockdown.");
							}
							return true;
						}else if(args[0].equalsIgnoreCase("status")){
							if(level == -1){
								p.sendMessage(prefix + ChatColor.GREEN + "The server is not in lockdown");
							}else{
								p.sendMessage(prefix + "The is locked down in " + lockdown + ". The security level is: " + level);
							}
							return true;
						}else if(args[0].equalsIgnoreCase("help")){
							//TODO Create a help menu.
							return true;
						}else if(this.getConfig().contains(args[0])){
							lockdown = args[0];
							level = this.getConfig().getInt(lockdown + ".level");
							p.sendMessage(lockdown);
							p.sendMessage(prefix + ChatColor.GREEN + "Lockdown activated. Level: " + level);
							for(Player OP : getServer().getOnlinePlayers()){
								if(!OP.hasPermission("lock." + level)){
									OP.kickPlayer(ChatColor.translateAlternateColorCodes('&', getConfig().getString(lockdown + ".message")));
								}else if(OP != p){
									OP.sendMessage(prefix + ChatColor.GREEN + "A lockdown has been enabled.");
								}
							}
						}else{
							p.sendMessage(prefix + ChatColor.RED + "This is not a valid lockdown.");
							return true;
						}
					}else{
						p.sendMessage(prefix + ChatColor.RED + "You have to many arguments!");
						return true;
					}
				}
			}else{
				sender.sendMessage(ChatColor.RED + "Please send as player. This option is coming soon.");
				return true;
			}
		}
		return true;
	}
}
