package nu.com.davidt.BetterBan.Commands;

import nu.com.davidt.BetterBan.BetterBan;
import nu.com.davidt.BetterBan.Util;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandUnban implements CommandExecutor
{

	/*
	 * Command setup
	 */

	private BetterBan plugin;
	
	public CommandUnban(final BetterBan parent)
	{
		this.plugin = parent;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if (cmd.getName().equalsIgnoreCase("unban"))
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
		if (sender.hasPermission("betterban.unban") || sender.isOp())
		{
			if (args.length != 0)
			{
				this.plugin.getServer().getOfflinePlayer(args[0]).setBanned(false);
				
				String message = this.plugin.getConfig().getString("unbanPlayerMessage");
					message = message.replaceAll("%t", args[0]);
					message = Util.formatChatColours(message);
				
				sender.sendMessage(message);
	
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
