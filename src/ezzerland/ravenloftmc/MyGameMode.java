package ezzerland.ravenloftmc;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import ezzerland.ravenloftmc.commands.Adventure;
import ezzerland.ravenloftmc.commands.Check;
import ezzerland.ravenloftmc.commands.Creative;
import ezzerland.ravenloftmc.commands.Gamemode;
import ezzerland.ravenloftmc.commands.Reload;
import ezzerland.ravenloftmc.commands.Spectate;
import ezzerland.ravenloftmc.commands.Survival;

public class MyGameMode extends JavaPlugin
{
  public void onEnable() 
  {
    saveDefaultConfig();
    RegisterCommands();
  }
  
  public void RegisterCommands()
  {
    getCommand("gamemode").setExecutor(new Gamemode(this));
    getCommand("gamemodesurvival").setExecutor(new Survival(this));
    getCommand("gamemodecreative").setExecutor(new Creative(this));
    getCommand("gamemodeadventure").setExecutor(new Adventure(this));
    getCommand("gamemodespectate").setExecutor(new Spectate(this));
    getCommand("checkgamemode").setExecutor(new Check(this));
    getCommand("mygamemodereload").setExecutor(new Reload(this));
  }
  
  /*
   * Method to change gamemode, also validates target is online
   * Returns a boolean to the command so we can proceed to issue messages
  */
  public boolean setGameMode(Player target, GameMode mode)
  {
    if (!target.isOnline()) { return false; }
    target.setGameMode(mode);
    return true;
  }
  
  public void doReload() { reloadConfig(); } //Reload config.yml
  
  /* Correct color codes or replace text if provided */
  public String CleanMessage (String message) { return message.replaceAll("&", "\247"); }
  public String CleanMessage (String reg, String fix, String message) { return CleanMessage(message).replaceAll(reg, fix); }
 
}