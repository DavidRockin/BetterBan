package nu.com.davidt.BetterBan;

import nu.com.davidt.BetterBan.Util;
import nu.com.davidt.BetterBan.Commands.CommandBan;
import nu.com.davidt.BetterBan.Commands.CommandKick;
import nu.com.davidt.BetterBan.Commands.CommandKickall;
import nu.com.davidt.BetterBan.Commands.CommandMaint;
import nu.com.davidt.BetterBan.Commands.CommandUnban;
import nu.com.davidt.BetterBan.PlayerListener;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/*
 * Not the best coding,
 * Probably one of my worst coding... ever..
 *
 * Made this plugin while i was lazy and tired.
 * Will redo it from scratch, soon ;)
 * Stay tuned to my github and plugin page :D
 */


public final class BetterBan extends JavaPlugin
{

	@Override
	public void onEnable()
	{
		this.saveDefaultConfig();

		Util.info("BetterBan loaded!");
		
		// Register listeners :D
		PluginManager pm = this.getServer().getPluginManager();
        pm.registerEvents(new PlayerListener(this), this);
        
		// Register commands! :D
		getCommand("ban").setExecutor(new CommandBan(this));
		getCommand("kick").setExecutor(new CommandKick(this));
		getCommand("kickall").setExecutor(new CommandKickall(this));
		getCommand("unban").setExecutor(new CommandUnban(this));
		getCommand("maint").setExecutor(new CommandMaint(this));
	}
	
	@Override
	public void onDisable()
	{
		Util.info("BetterBan unloaded!");
		this.saveConfig();
	}
	
	public static void main(String[] args)
	{
		/* */
	}

}
