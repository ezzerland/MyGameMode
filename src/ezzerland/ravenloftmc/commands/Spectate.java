package ezzerland.ravenloftmc.commands;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ezzerland.ravenloftmc.MyGameMode;

public class Spectate implements CommandExecutor
{ //This handles [gamemodespectate, gmsp, gm3]
  private MyGameMode mgm;
  public Spectate (MyGameMode plugin) { mgm = plugin; }

  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
  { //Trying to set mode of all players?
    if (((args.length == 1) && (args[0].equalsIgnoreCase("all"))) && ((!(sender instanceof Player)) || (sender.hasPermission("mygamemode.spectate.all"))))
    {
      for (Player target : mgm.getServer().getOnlinePlayers())
      {
        mgm.setGameMode(target,GameMode.SPECTATOR);
        target.sendMessage(mgm.CleanMessage("%player%", sender.getName(), mgm.CleanMessage("%target%", target.getName(), mgm.CleanMessage("%mode%", "SPECTATE", mgm.getConfig().getString("setmode.byother")))));
      }
      sender.sendMessage(mgm.CleanMessage("%player%", sender.getName(), mgm.CleanMessage("%mode%", "SPECTATE", mgm.getConfig().getString("setmode.all"))));
      return true;
    }
    
    //Trying to change gamemode of other player?
    if ((args.length == 1) && ((!(sender instanceof Player)) || (sender.hasPermission("mygamemode.spectate.other"))))
    {
      Player target = mgm.getServer().getPlayer(args[0]);
      if ((target != null) && (mgm.setGameMode(target, GameMode.SPECTATOR)))
      {
        sender.sendMessage(mgm.CleanMessage("%player%", sender.getName(), mgm.CleanMessage("%target%", args[0], mgm.CleanMessage("%mode%", "SPECTATOR", mgm.getConfig().getString("setmode.other")))));
        target.sendMessage(mgm.CleanMessage("%player%", sender.getName(), mgm.CleanMessage("%target%", args[0], mgm.CleanMessage("%mode%", "SPECTATOR", mgm.getConfig().getString("setmode.byother")))));
        return true;
      }
      else { //Either couldn't find the player or they were not online
        sender.sendMessage(mgm.CleanMessage("%player%", sender.getName(), mgm.CleanMessage("%target%", args[0], mgm.CleanMessage("%mode%", "SPECTATOR", mgm.getConfig().getString("syntax.wrongname")))));
        return false;
      }
    }
    
    //Trying to change own game mode?
    if ((sender instanceof Player) && (sender.hasPermission("mygamemode.spectate.self")))
    { //Ignore console, player has perm so lets go!
      if (mgm.setGameMode((Player)sender, GameMode.SPECTATOR))
      {
        sender.sendMessage(mgm.CleanMessage("%player%", sender.getName(), mgm.CleanMessage("%mode%", "SPECTATOR", mgm.getConfig().getString("setmode.self"))));
        return true;
      }
    }
    return false;
  }
}