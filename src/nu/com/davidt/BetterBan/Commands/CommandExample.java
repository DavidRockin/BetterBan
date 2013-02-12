package nu.com.davidt.BetterBan.Commands;

import nu.com.davidt.BetterBan.BetterBan;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandExample implements CommandExecutor
{

	/*
	 * Command setup
	 */

	private BetterBan plugin;
	
	public CommandExample(final BetterBan parent)
	{
		this.plugin = parent;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if (cmd.getName().equalsIgnoreCase("command here!"))
		{
			this.command(sender, cmd, label, args);
			return true;
		}

		return false;
	}
	
	/*
	 * Command 
	 */
	
	private void command(CommandSender sender, Command cmd, String label, String[] args)
	{
		// Command code here ... //
	}

}
