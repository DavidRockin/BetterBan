package nu.com.davidt.BetterBan;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

public class PlayerListener implements Listener
{

	private final transient BetterBan plugin;
	
	public PlayerListener(final BetterBan parent)
	{
		this.plugin = parent;
	}
	
	/*
	 * Listeners
	 */
	
	// Player Login events
    @EventHandler
    public void PlayerLoginEvent(PlayerLoginEvent event)
    {
    	Player player = event.getPlayer();
    	
    	// Check if server is under Maintenance mode?
    	if (this.plugin.getConfig().getBoolean("maintenanceEnabled") == true)
    	{
    		Boolean bypassMaintCheck = false;
    		
    		// Is the whitelist bypass enabled?
    		if (this.plugin.getConfig().getBoolean("maintenanceWhitelistBypass") == true && player.isWhitelisted())
    		{
    			// Player is on the whitelist, bypass the permission and is operator check!
    			bypassMaintCheck = true;
    		}
   
    		// Make sure the user isn't bypassing with whitelist
    		if (bypassMaintCheck == false)
    		{
	    		// Does player not have permission or OP?
	    		if (!player.hasPermission("betterban.maintenance.allow") || !player.isOp())
	    		{
	    			// They don't! So send them a message!
	    			String message = this.plugin.getConfig().getString("maintenanceMessage");
	    				message = Util.formatChatColours(message);
	    			
	    			event.disallow(Result.KICK_OTHER, message);
	    			return;
	    		}
	    		
    		}
    	}
    	
    	// Check if server's whitelist is enabled!
    	if (this.plugin.getServer().hasWhitelist())
    	{
    		// Check if player isn't on the whitelist
    		if (!player.isWhitelisted())
    		{
    			// Let the user know, they're not on the whitelist
    			String message = this.plugin.getConfig().getString("whitelistMessage");
    				message = Util.formatChatColours(message);
    			
    			event.disallow(Result.KICK_WHITELIST, message);
    			return;
    		}
    	}
    	
    	// Check if player is banned    	
    	if (player.isBanned())
    	{
    		// Let the player know they're banned!
			String message = this.plugin.getConfig().getString("bannedMessage");
				message = Util.formatChatColours(message);	
    	
    		event.disallow(Result.KICK_BANNED, message);
    		return;
    	}
    	
    	// Ok, let the player join!
    	event.allow();
    }
	
	// Player Join/Quit messages
    @EventHandler
    public void PlayerJoinEvent(PlayerJoinEvent event)
    {
    	if (this.plugin.getConfig().getBoolean("showLoginQuitMessages") == false)
    	{
    		event.setJoinMessage(null);
    		return;
    	}
    	
    	if (this.plugin.getConfig().getBoolean("customLoginQuitMessages") == true)
    	{
    		String message = this.plugin.getConfig().getString("loginMessage");
    			message = message.replaceAll("%p", event.getPlayer().getDisplayName());
    			message = Util.formatChatColours(message);
    		
    		event.setJoinMessage(message);
    	}
    }
    
    @EventHandler
    public void PlayerQuitEvent(PlayerQuitEvent event)
    {
    	if (this.plugin.getConfig().getBoolean("showLoginQuitMessages") != true)
    	{
    		event.setQuitMessage(null);
    		return;
    	}
    	
    	// Check if we will use custom login/quit messages
    	if (this.plugin.getConfig().getBoolean("customLoginQuitMessages") == true)
    	{
    		String message = this.plugin.getConfig().getString("quitMessage");
				message = message.replaceAll("%p", event.getPlayer().getDisplayName());
				message = Util.formatChatColours(message);

    		event.setQuitMessage(message);
    	}
    }

    // Server MOTD
    @EventHandler
    public void ServerListPingEvent(ServerListPingEvent event)
    {
    	// If maintenance is active, let's change the server MOTD to let others know the server
    	// Is under maintenance :D
    	if (this.plugin.getConfig().getBoolean("maintenanceEnabled") == true)
    	{
    		String message = this.plugin.getConfig().getString("maintenanceMOTD");
    			message = Util.formatChatColours(message);
    		event.setMotd(message);
    	}
    	
    }
	
}
