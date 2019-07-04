package ezzerland.ravenloftmc.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ezzerland.ravenloftmc.MyGameMode;

public class Godmode implements CommandExecutor
{ //This handles [godmode, god]
  private MyGameMode mgm;
  public Godmode (MyGameMode plugin) { mgm = plugin; }
  
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
  { //Trying to change godmode of whole server?
    String task = "godmode.mode.enabled"; //task is what tells the user we turned god mode on or off
    if ((args.length == 2) && ((!(sender instanceof Player)) || (sender.hasPermission("mygamemode.godmode.all"))))
    {
      if ((!args[0].equalsIgnoreCase("all")) || ((!args[1].equalsIgnoreCase("on"))|| (!args[1].equalsIgnoreCase("off")))) { return false; }
      if (args[1].equalsIgnoreCase("off")) { task = "godmode.mode.disabled"; }
      for (Player target: mgm.getServer().getOnlinePlayers())
      { //Change mode for players who need it changed
        if ((args[1].equalsIgnoreCase("on")) && (!mgm.godmode.contains(target.getUniqueId().toString()))) { mgm.toggleGodMode(target.getUniqueId().toString(), true); }
        if ((args[1].equalsIgnoreCase("off")) && (mgm.godmode.contains(target.getUniqueId().toString()))) { mgm.toggleGodMode(target.getUniqueId().toString(), false); }
        target.sendMessage(mgm.CleanMessage("%player%", sender.getName(), mgm.CleanMessage("%target%", args[0], mgm.CleanMessage("%mode%", mgm.getConfig().getString(task), mgm.getConfig().getString("godmode.byother")))));
      } //Notify sender everyone's god mode was changed
      sender.sendMessage(mgm.CleanMessage("%mode%", mgm.getConfig().getString(task), mgm.getConfig().getString("godmode.all")));
      return true;
    }
    
    //Trying to change godmode of other player?
    if ((args.length == 1) && ((!(sender instanceof Player)) || (sender.hasPermission("mygamemode.godmode.other"))))
    {
      Player target = mgm.getServer().getPlayer(args[0]);
      if ((target != null) && (target.isOnline()))
      {
        if (!mgm.godmode.contains(target.getUniqueId().toString())) { mgm.toggleGodMode(target.getUniqueId().toString(), true); }
        else { task = "godmode.mode.disabled"; mgm.toggleGodMode(target.getUniqueId().toString(), false); }
        sender.sendMessage(mgm.CleanMessage("%player%", sender.getName(), mgm.CleanMessage("%target%", args[0], mgm.CleanMessage("%mode%", mgm.getConfig().getString(task), mgm.getConfig().getString("godmode.other")))));
        target.sendMessage(mgm.CleanMessage("%player%", sender.getName(), mgm.CleanMessage("%target%", args[0], mgm.CleanMessage("%mode%", mgm.getConfig().getString(task), mgm.getConfig().getString("godmode.byother")))));
        return true;
      }
      else { //Either couldn't find the player or they were not online
        sender.sendMessage(mgm.CleanMessage("%player%", sender.getName(), mgm.CleanMessage("%target%", args[0], mgm.CleanMessage("%mode%", "SURVIVAL", mgm.getConfig().getString("syntax.wrongname")))));
        return false;
      }
    }
    
    //Trying to change own godmode?
    if ((sender instanceof Player) && (sender.hasPermission("mygamemode.godmode.self")))
    { //Ignore console, player has perm so lets go!
      if (!mgm.godmode.contains(((Player)sender).getUniqueId().toString())) {mgm.toggleGodMode(((Player)sender).getUniqueId().toString(), true); }
      else { task = "godmode.mode.disabled"; mgm.toggleGodMode(((Player)sender).getUniqueId().toString(), false); }
      sender.sendMessage(mgm.CleanMessage("%player%", sender.getName(), mgm.CleanMessage("%mode%", mgm.getConfig().getString(task), mgm.getConfig().getString("godmode.self"))));
      return true;
    }
    return false;
  }
}