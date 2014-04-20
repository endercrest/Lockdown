package com.endercrest;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

public class LoginEvent implements Listener {

	Lockdown plugin;
	
	public LoginEvent(Lockdown plugin){
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerLoginEvent e){
		Player p = e.getPlayer();
		if(plugin.level != -1){
			if(!p.hasPermission("lock.*")){
				if(!p.hasPermission("lock." + plugin.level)){
					e.disallow(Result.KICK_OTHER, ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString(plugin.lockdown + ".message")));
				}
			}
		}
	}
}
