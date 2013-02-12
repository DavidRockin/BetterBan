package nu.com.davidt.BetterBan;

import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Util
{

	private static final Logger log = Logger.getLogger("Minecraft");
	private transient static BetterBan plugin;
	
	public Util(final BetterBan parent)
	{
		this.plugin = parent;
	}
	
	/*
	 * Logging
	 */
	public static void info(String message)
	{
		log.info("[BetterBan] " + message);
	}

	public static void warning(String message)
	{
		log.warning("[BetterBan] " + message);
	}

	public static void severe(String message)
	{
		log.severe("[BetterBan] " + message);
	}
	
	/*
	 * Useful functions
	 */
	public static boolean isInteger(String str)
	{
		try
		{
			Integer.parseInt(str);
		} catch (NumberFormatException e)
		{
			return false;
		}

		return true;
	}
	
	/*
	 * Functions
	 * From http://forums.bukkit.org/threads/useful-awesome-code-snippets.104409/ and a bit modified
	 */
	
	public static String arrayJoin(String[] args, int start)
	{				
		StringBuilder strBuilder = new StringBuilder();

		for (int i = start ; i < args.length; i++)
		{
		    strBuilder.append(args[i]);
		    strBuilder.append(" ");
		}
		
		return (String) strBuilder.toString();
	}
	
		public static String arrayJoin(String[] args)
		{
			return arrayJoin(args, 0);
		}
		
	public static String formatChatColours(String s) {
	    for (ChatColor c : ChatColor.values()) {
	        s = s.replaceAll("&" + c.getChar(), ChatColor.getByChar(c.getChar()) + "");
	    }
	 
	    return s;
	}
	
}
