package ezzerland.ravenloftmc.commands;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ezzerland.ravenloftmc.MyGameMode;

public class Gamemode implements CommandExecutor
{ //This handles [gamemode, gm]
  private MyGameMode mgm;
  private GameMode mode;
  private String permission;
  public Gamemode (MyGameMode plugin) { mgm = plugin; }

  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
  { //set our TBD vars//Determine mode we're trying to change to
    
    if ((args.length>=1) && (!validGameMode(args[0]))) { sender.sendMessage(mgm.CleanMessage("%mode%", args[0], mgm.getConfig().getString("syntax.invalidmode"))); return false; }
    
    //Trying to set mode of all players?
    if ((args.length == 2) && (args[1].equalsIgnoreCase("all")))
    {
      permission.concat(".all");
      if ((!(sender instanceof Player)) || (sender.hasPermission(permission)))
      {
        for (Player target : mgm.getServer().getOnlinePlayers())
        {
          mgm.setGameMode(target, mode);
          target.sendMessage(mgm.CleanMessage("%player%", sender.getName(), mgm.CleanMessage("%target%", target.getName(), mgm.CleanMessage("%mode%", mode.toString(), mgm.getConfig().getString("setmode.byother")))));
        }
        sender.sendMessage(mgm.CleanMessage("%player%", sender.getName(), mgm.CleanMessage("%mode%", mode.toString(), mgm.getConfig().getString("setmode.all"))));
        return true;
      }
      return false;
    }
    
    //Trying to change gamemode of other player?
    if (args.length == 2)
    { 
      permission.concat(".other");
      
      if ((!(sender instanceof Player)) || (sender.hasPermission(permission)))
      { //Let's go ahead and try and change modes
        Player target = mgm.getServer().getPlayer(args[1]);
        if ((target != null) && (mgm.setGameMode(target, mode)))
        {
          sender.sendMessage(mgm.CleanMessage("%player%", sender.getName(), mgm.CleanMessage("%target%", args[1], mgm.CleanMessage("%mode%", mode.toString(), mgm.getConfig().getString("setmode.other")))));
          target.sendMessage(mgm.CleanMessage("%player%", sender.getName(), mgm.CleanMessage("%target%", args[1], mgm.CleanMessage("%mode%", mode.toString(), mgm.getConfig().getString("setmode.byother")))));
          return true;
        }
        else { //Either couldn't find the player or they were not online
          sender.sendMessage(mgm.CleanMessage("%player%", sender.getName(), mgm.CleanMessage("%target%", args[1], mgm.CleanMessage("%mode%", mode.toString(), mgm.getConfig().getString("syntax.wrongname")))));
          return false;
        }
      }
    }
    
    //Trying to change own game mode?
    if ((sender instanceof Player) && (args.length == 1))
    { //Ignored Console
      permission.concat(".self");
      
      if ((sender.hasPermission(permission)) && (mgm.setGameMode((Player)sender, mode)))
      {
        sender.sendMessage(mgm.CleanMessage("%player%", sender.getName(), mgm.CleanMessage("%mode%", mode.toString(), mgm.getConfig().getString("setmode.self"))));
        return true;
      }
    }
    return false;
  }
  
  private boolean validGameMode(String type) {
    if ((type.equalsIgnoreCase("survival")) || (type.equalsIgnoreCase("s")) || (type.equalsIgnoreCase("0"))) { mode = GameMode.SURVIVAL; permission = "mygamemode.survival"; return true; }
    if ((type.equalsIgnoreCase("creative")) || (type.equalsIgnoreCase("c")) || (type.equalsIgnoreCase("1"))) { mode = GameMode.CREATIVE; permission = "mygamemode.creative"; return true; }
    if ((type.equalsIgnoreCase("adventure")) || (type.equalsIgnoreCase("a")) || (type.equalsIgnoreCase("2"))) { mode = GameMode.ADVENTURE; permission = "mygamemode.adventure"; return true; }
    if ((type.equalsIgnoreCase("spectate")) || (type.equalsIgnoreCase("sp")) || (type.equalsIgnoreCase("3"))) { mode = GameMode.SPECTATOR; permission = "mygamemode.spectate"; return true; }
    return false;
  }
}