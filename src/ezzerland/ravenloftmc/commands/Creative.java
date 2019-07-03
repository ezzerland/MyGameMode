package ezzerland.ravenloftmc.commands;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ezzerland.ravenloftmc.MyGameMode;

public class Creative implements CommandExecutor
{ //This handles [gamemodecreative, gmc, gm1]
  private MyGameMode mgm;
  public Creative (MyGameMode plugin) { mgm = plugin; }

  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
  { //Trying to change gamemode of other player?
    if ((args.length == 1) && ((!(sender instanceof Player)) || (sender.hasPermission("mygamemode.creative.other"))))
    {
      Player target = mgm.getServer().getPlayer(args[0]);
      if ((target != null) && (mgm.setGameMode(target, GameMode.CREATIVE)))
      {
        sender.sendMessage(mgm.CleanMessage("%player%", sender.getName(), mgm.CleanMessage("%target%", args[0], mgm.CleanMessage("%mode%", "CREATIVE", mgm.getConfig().getString("setmode.other")))));
        target.sendMessage(mgm.CleanMessage("%player%", sender.getName(), mgm.CleanMessage("%target%", args[0], mgm.CleanMessage("%mode%", "CREATIVE", mgm.getConfig().getString("setmode.byother")))));
        return true;
      }
      else { //Either couldn't find the player or they were not online
        sender.sendMessage(mgm.CleanMessage("%player%", sender.getName(), mgm.CleanMessage("%target%", args[0], mgm.CleanMessage("%mode%", "CREATIVE", mgm.getConfig().getString("syntax.wrongname")))));
        return false;
      }
    }
    
    //Trying to change own game mode?
    if ((sender instanceof Player) && (sender.hasPermission("mygamemode.creative.self")))
    { //Ignore console, player has perm so lets go!
      if (mgm.setGameMode((Player)sender, GameMode.CREATIVE))
      {
        sender.sendMessage(mgm.CleanMessage("%player%", sender.getName(), mgm.CleanMessage("%mode%", "CREATIVE", mgm.getConfig().getString("setmode.self"))));
        return true;
      }
    }
    return false;
  }
}