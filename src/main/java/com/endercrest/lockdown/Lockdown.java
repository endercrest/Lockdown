package com.endercrest.lockdown;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public class Lockdown extends JavaPlugin {

    public int level = -1;
    public String lockdown;
    public String prefix = ChatColor.GOLD + "[" + ChatColor.BLUE + "Lockdown" + ChatColor.GOLD + "]" + ChatColor.WHITE;

    @Override
    public void onEnable(){
        this.saveDefaultConfig();
        getServer().getPluginManager().registerEvents(new LoginEvent(this), this);
        log("&av" + this.getDescription().getVersion() + " by EnderCrest enabled");
    }

    @Override
    public void onDisable(){

    }

    /**
     * This replaces minecraft colorcodes to add colour
     * @param str String that needs converting
     * @return returns the coloured text
     */
    public static String colorize(String str){ return str.replaceAll("(?i)&([a-f0-9k-or])", "\u00a7$1");}

    /**
     * Sends messages to the console
     * @param obj - object to be sent to console.
     */
    public void log(Object obj){
        if(getConfig().getBoolean("color-logs", true)){
            getServer().getConsoleSender().sendMessage(colorize("&3[&d" + getName() + "&3] &r" + obj));
        }else{
            Bukkit.getLogger().log(Level.INFO, "[" + getName() + "] " + (colorize((String) obj)).replaceAll("(?)\u00a7([a-f0-9k-or])", ""));
        }
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        if(cmd.getName().equalsIgnoreCase("lock")){
            if(sender instanceof Player){
                Player p = (Player)sender;
                    //Message for how to use command
                    if(args.length == 0){
                        p.sendMessage(prefix + ChatColor.GREEN + "/lock (disable/status/[type]");
                        return true;
                    }else if(args.length == 1){
                        if(args[0].equalsIgnoreCase("disable")){
                            return disableLockDown();
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
                            p.sendMessage(prefix + ChatColor.GREEN + "Lockdown activated. Level: " + level);
                            kickPlayer(p);
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
        return true;
    }

    public void kickPlayer(Player p){
        for(Player OP : getServer().getOnlinePlayers()){
            boolean kick = true;
            for(int i = 0; i <= level; i++){
                if(OP.hasPermission("lock.*")){
                    kick = false;
                }
                if(OP.hasPermission("lock." + i)){
                    kick = false;
                }
            }
            if(kick){
                OP.kickPlayer(ChatColor.translateAlternateColorCodes('&', getConfig().getString(lockdown + ".message")));
            }
            if(OP != p && kick == false){
                OP.sendMessage(prefix + ChatColor.GREEN + "A lockdown has been enabled.");
            }
        }
    }

    public boolean disableLockDown(){
        for(Player OP : getServer().getOnlinePlayers()){
            if(level != -1){
                OP.sendMessage(prefix + ChatColor.GREEN + "Lockdown has been disabled!");
            }else{
                OP.sendMessage(prefix + ChatColor.RED + "The server was not in lockdown.");
            }
        }
        level = -1;
        lockdown = null;
        return true;
    }
}
