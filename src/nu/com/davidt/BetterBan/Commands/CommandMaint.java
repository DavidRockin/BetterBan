package nu.com.davidt.BetterBan.Commands;

import nu.com.davidt.BetterBan.BetterBan;
import nu.com.davidt.BetterBan.Util;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandMaint implements CommandExecutor
{

	/*
	 * Command setup
	 */

	private BetterBan plugin;
	
	public CommandMaint(final BetterBan parent)
	{
		this.plugin = parent;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if (cmd.getName().equalsIgnoreCase("maint"))
		{
			return this.command(sender, cmd, label, args);
		}

		return false;
	}
	
	/*
	 * Command 
	 */
	
	private boolean command(CommandSender sender, Command cmd, String label, String[] args)
	{
		if (sender.hasPermission("betterban.maint") || sender.isOp())
		{
			if (args.length != 0)
			{
				if (args[0].equalsIgnoreCase("on") || args[0].equalsIgnoreCase("off"))
				{
					if (args[0].equalsIgnoreCase("on"))
					{
						String kickReason = this.plugin.getConfig().getString("maintenanceMessage");
						
						this.plugin.getConfig().set("maintenanceEnabled", true);
						
						for (Player p : this.plugin.getServer().getOnlinePlayers())
						{
							if (!p.hasPermission("betterban.kickall.bypass") || !p.isOp() && p.getName() != sender.getName())
							{
								p.kickPlayer(kickReason);
							}
						}
						
						// Got lazy here, will be in next version probably?
						this.plugin.getServer().broadcastMessage(ChatColor.GOLD + "Player " + ChatColor.YELLOW + sender.getName() + " " + ChatColor.GOLD + "has enabled maintenance mode on the server.");
					} else if (args[0].equalsIgnoreCase("off"))
					{
						this.plugin.getConfig().set("maintenanceEnabled", false);
						
						// Got lazy here, will be in next version probably?
						this.plugin.getServer().broadcastMessage(ChatColor.GOLD + "Player " + ChatColor.YELLOW + sender.getName() + " " + ChatColor.GOLD + "has disabled maintenance mode on the server.");
					}
				} else
				{
					sender.sendMessage(ChatColor.RED + "Maintenance mode can only be on or off.");
				}

				return true;
			} else
			{
				sender.sendMessage(ChatColor.RED + "Invalid command, maintenance state (on or off) is required.");
			
				return false;
			}
		} else
		{
			String message = this.plugin.getConfig().getString("noPermissionCmd");
				message = Util.formatChatColours(message);
			sender.sendMessage(message);
			Util.info(sender.getName() + " was denied access to /" + cmd.getName());
			
			return true;
		}
	}

}
