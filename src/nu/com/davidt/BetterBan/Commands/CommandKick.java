package nu.com.davidt.BetterBan.Commands;

import nu.com.davidt.BetterBan.BetterBan;
import nu.com.davidt.BetterBan.Util;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandKick implements CommandExecutor
{

	/*
	 * Command setup
	 */

	private BetterBan plugin;
	
	public CommandKick(final BetterBan parent)
	{
		this.plugin = parent;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if (cmd.getName().equalsIgnoreCase("kick"))
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
		if (sender.hasPermission("betterban.kick") || sender.isOp())
		{
			if (args.length != 0)
			{
				String kickReason = this.plugin.getConfig().getString("defaultKick");
	
				if (args.length >= 2)
					kickReason = Util.arrayJoin(args, 1);
				
				if (this.plugin.getServer().getOfflinePlayer(args[0]).isOnline())
				{
					this.plugin.getServer().getPlayer(args[0]).kickPlayer(kickReason);
				} else
				{
					String message = this.plugin.getConfig().getString("userNotOnline");
						message = message.replaceAll("%t", args[0]);
						message = Util.formatChatColours(message);
					
					sender.sendMessage(message);

					return true;
				}
				
				if (this.plugin.getConfig().getBoolean("showKickNotifications") == true)
				{

					String message = this.plugin.getConfig().getString("kickNotification");
						message = message.replaceAll("%p", sender.getName());
						message = message.replaceAll("%t", args[0]);
						message = message.replaceAll("%r", kickReason);
						message = Util.formatChatColours(message);
					
					this.plugin.getServer().broadcastMessage(message);
				} else
				{
					String message = this.plugin.getConfig().getString("playerKickedMessage");
						message = message.replaceAll("%t", args[0]);
						message = Util.formatChatColours(message);
					
					sender.sendMessage(message);
				}

				return true;
			} else
			{
				sender.sendMessage(ChatColor.RED + "Invalid command, player username is required.");

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
