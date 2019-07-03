package ezzerland.ravenloft;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import ezzerland.ravenloft.commands.Adventure;
import ezzerland.ravenloft.commands.Check;
import ezzerland.ravenloft.commands.Creative;
import ezzerland.ravenloft.commands.Gamemode;
import ezzerland.ravenloft.commands.Reload;
import ezzerland.ravenloft.commands.Spectate;
import ezzerland.ravenloft.commands.Survival;

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