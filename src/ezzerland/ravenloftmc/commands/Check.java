package ezzerland.ravenloftmc.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ezzerland.ravenloftmc.MyGameMode;

public class Check implements CommandExecutor
{ //This handles [checkgamemode, cgm, getgamemode, ggm]
  private MyGameMode mgm;
  public Check (MyGameMode plugin) { mgm = plugin; }
  
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
  {
    if (((sender instanceof Player) || (sender.hasPermission("mygamemode.check"))) && (args.length == 1))
    {
      Player target = mgm.getServer().getPlayer(args[0]);
      if (target != null && target.isOnline()) {
        sender.sendMessage(mgm.CleanMessage("%player%", sender.getName(), mgm.CleanMessage("%target%", args[0], mgm.CleanMessage("%mode%", target.getGameMode().toString(), mgm.getConfig().getString("getmode")))));
        return true;
      }
      sender.sendMessage(mgm.CleanMessage("%player%", sender.getName(), mgm.CleanMessage("%target%", args[0], mgm.getConfig().getString("syntax.wrongname"))));
    }
    return false;
  }
}