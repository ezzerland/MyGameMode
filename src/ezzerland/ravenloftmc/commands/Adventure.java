package ezzerland.ravenloftmc.commands;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ezzerland.ravenloftmc.MyGameMode;

public class Adventure implements CommandExecutor
{ //This handles [gamemodeadventure, gma, gm2]
  private MyGameMode mgm;
  public Adventure (MyGameMode plugin) { mgm = plugin; }

  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
  { //Trying to set mode of all players?
    if (((args.length == 1) && (args[0].equalsIgnoreCase("all"))) && ((!(sender instanceof Player)) || (sender.hasPermission("mygamemode.adventure.all"))))
    {
      for (Player target : mgm.getServer().getOnlinePlayers())
      {
        mgm.setGameMode(target,GameMode.ADVENTURE);
        target.sendMessage(mgm.CleanMessage("%player%", sender.getName(), mgm.CleanMessage("%target%", target.getName(), mgm.CleanMessage("%mode%", "ADVENTURE", mgm.getConfig().getString("setmode.byother")))));
      }
      sender.sendMessage(mgm.CleanMessage("%player%", sender.getName(), mgm.CleanMessage("%mode%", "ADVENTURE", mgm.getConfig().getString("setmode.all"))));
      return true;
    }
    
    //Trying to change gamemode of other player?
    if ((args.length == 1) && ((!(sender instanceof Player)) || (sender.hasPermission("mygamemode.adventure.other"))))
    {
      Player target = mgm.getServer().getPlayer(args[0]);
      if ((target != null) && (mgm.setGameMode(target, GameMode.ADVENTURE)))
      {
        sender.sendMessage(mgm.CleanMessage("%player%", sender.getName(), mgm.CleanMessage("%target%", args[0], mgm.CleanMessage("%mode%", "ADVENTURE", mgm.getConfig().getString("setmode.other")))));
        target.sendMessage(mgm.CleanMessage("%player%", sender.getName(), mgm.CleanMessage("%target%", args[0], mgm.CleanMessage("%mode%", "ADVENTURE", mgm.getConfig().getString("setmode.byother")))));
        return true;
      }
      else { //Either couldn't find the player or they were not online
        sender.sendMessage(mgm.CleanMessage("%player%", sender.getName(), mgm.CleanMessage("%target%", args[0], mgm.CleanMessage("%mode%", "ADVENTURE", mgm.getConfig().getString("syntax.wrongname")))));
        return false;
      }
    }
    
    //Trying to change own game mode?
    if ((sender instanceof Player) && (sender.hasPermission("mygamemode.adventure.self")))
    { //Ignore console, player has perm so lets go!
      if (mgm.setGameMode((Player)sender, GameMode.ADVENTURE))
      {
        sender.sendMessage(mgm.CleanMessage("%player%", sender.getName(), mgm.CleanMessage("%mode%", "ADVENTURE", mgm.getConfig().getString("setmode.self"))));
        return true;
      }
    }
    return false;
  }
}