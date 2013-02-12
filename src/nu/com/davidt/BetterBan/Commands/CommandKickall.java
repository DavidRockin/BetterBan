package nu.com.davidt.BetterBan.Commands;

import nu.com.davidt.BetterBan.BetterBan;
import nu.com.davidt.BetterBan.Util;

import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandKickall implements CommandExecutor
{

	/*
	 * Command setup
	 */

	private BetterBan plugin;
	
	public CommandKickall(final BetterBan parent)
	{
		this.plugin = parent;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if (cmd.getName().equalsIgnoreCase("kickall"))
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
		if (sender.hasPermission("betterban.kickall") || sender.isOp())
		{
			String kickReason = this.plugin.getConfig().getString("defaultKick");

			if (args.length >= 2)
				kickReason = Util.arrayJoin(args, 1);
			
			for (Player p : this.plugin.getServer().getOnlinePlayers())
			{
				if (!p.hasPermission("betterban.kickall.bypass") || !p.isOp() && p.getName() != sender.getName())
				{
					p.kickPlayer(kickReason);
				}
			}
			
			String message = this.plugin.getConfig().getString("kickallNotification");
				message = message.replaceAll("%p", sender.getName());
				message = message.replaceAll("%r", kickReason);
				message = Util.formatChatColours(message);
	
			this.plugin.getServer().broadcastMessage(message);

			return true;	
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
