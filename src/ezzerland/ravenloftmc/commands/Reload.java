package ezzerland.ravenloftmc.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ezzerland.ravenloftmc.MyGameMode;

public class Reload implements CommandExecutor
{ //This handles [mygamemodereload, gmreload, mgmreload, mgmr, gmr]
  private MyGameMode mgm;
  public Reload (MyGameMode plugin) { mgm = plugin; }
  
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
  {
    if ((!(sender instanceof Player)) || (sender.hasPermission("mygamemode.reload")))
    {
      mgm.doReload();
      sender.sendMessage(mgm.CleanMessage(mgm.getConfig().getString("reload")));
      return true;
    }
    return false;
  }
}