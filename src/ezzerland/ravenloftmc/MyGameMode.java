package ezzerland.ravenloftmc;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.GameMode;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import ezzerland.ravenloftmc.commands.Adventure;
import ezzerland.ravenloftmc.commands.Check;
import ezzerland.ravenloftmc.commands.Creative;
import ezzerland.ravenloftmc.commands.Gamemode;
import ezzerland.ravenloftmc.commands.Godmode;
import ezzerland.ravenloftmc.commands.Reload;
import ezzerland.ravenloftmc.commands.Spectate;
import ezzerland.ravenloftmc.commands.Survival;
import ezzerland.ravenloftmc.listeners.GodModeListener;

public class MyGameMode extends JavaPlugin
{
  public List<String> godmode;
  private File godModeFile;
  public FileConfiguration godModeConfig;
  
  public void onEnable() 
  {
    saveDefaultConfig();
    
    godmode = new ArrayList<String>();
    godModeFile = new File(getDataFolder(), "godmode.yml");
    if (!godModeFile.exists())
    {
      getDataFolder().mkdirs();
      copy(getResource("godmode.yml"), godModeFile);
    }
    
    getServer().getPluginManager().registerEvents(new GodModeListener(this), this);
    doReload();
    RegisterCommands();
  }
  
  /* Method to change a players setting for god mode and store it to file if they have perm */
  public void toggleGodMode(String uuid, boolean mode)
  {
    if (mode) { godmode.add(uuid); } else { godmode.remove(uuid); }
    if (getServer().getPlayer(UUID.fromString(uuid)).hasPermission("mygamemode.godmode.keep"))
    {
      godModeConfig.set(uuid, mode);
      try { godModeConfig.save(godModeFile); } catch (IOException e) { e.printStackTrace(); }
    }
  }
  
  public void RegisterCommands()
  {
    getCommand("gamemode").setExecutor(new Gamemode(this));
    getCommand("gamemodesurvival").setExecutor(new Survival(this));
    getCommand("gamemodecreative").setExecutor(new Creative(this));
    getCommand("gamemodeadventure").setExecutor(new Adventure(this));
    getCommand("gamemodespectate").setExecutor(new Spectate(this));
    getCommand("godmode").setExecutor(new Godmode(this));
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
  
  /* Load or Reload all the configuration files and establish the database we are using */
  public void doReload()
  {
    reloadConfig();
    godModeConfig = YamlConfiguration.loadConfiguration(godModeFile);
  }
  
  /* Clone internal yml file for initialization/use */
  private void copy (InputStream from, File to) 
  {
    try 
    {
      OutputStream out = new FileOutputStream(to);
      byte[] buffer = new byte[1024];
      int size = 0;
      
      while((size = from.read(buffer)) != -1) 
      {
        out.write(buffer, 0, size);
      }
      
      out.close();
      from.close();
    } catch(Exception e) {  e.printStackTrace(); }
  }
  
  /* Correct color codes or replace text if provided */
  public String CleanMessage (String message) { return message.replaceAll("&", "\247"); }
  public String CleanMessage (String reg, String fix, String message) { return CleanMessage(message).replaceAll(reg, fix); }
 
}