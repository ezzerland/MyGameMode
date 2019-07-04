package ezzerland.ravenloftmc.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import ezzerland.ravenloftmc.MyGameMode;

public class GodModeListener implements Listener
{
  private MyGameMode mgm;
  public GodModeListener(MyGameMode plugin) { mgm = plugin; }
  
  @EventHandler (ignoreCancelled = true, priority = EventPriority.HIGHEST)
  public void onEntityDamage(EntityDamageEvent e)
  { //block damage
    if (mgm.godmode.contains(e.getEntity().getUniqueId().toString())) { e.setCancelled(true); }
  }
  
  @EventHandler (ignoreCancelled = true, priority = EventPriority.HIGHEST)
  public void onFoodLevelChange (FoodLevelChangeEvent e)
  { //stop food changes
    if (mgm.godmode.contains(e.getEntity().getUniqueId().toString())) { e.setCancelled(true); }
  }
  
  @EventHandler (ignoreCancelled = true, priority = EventPriority.HIGHEST)
  public void onPlayerJoin (PlayerJoinEvent e)
  { //if they have perm, check if we should restore god mode
    if ((e.getPlayer().hasPermission("mygamemode.godmode.keep")) && mgm.godModeConfig.contains(e.getPlayer().getUniqueId().toString()) && mgm.godModeConfig.getBoolean(e.getPlayer().getUniqueId().toString()))
    {
      mgm.godmode.add(e.getPlayer().getUniqueId().toString());
    }
  }
  
  @EventHandler (ignoreCancelled = true, priority = EventPriority.HIGHEST)
  public void onPlayerQuit (PlayerQuitEvent e)
  {
    if (mgm.godmode.contains(e.getPlayer().getUniqueId().toString())) { mgm.godmode.remove(e.getPlayer().getUniqueId().toString()); }
  }
  
}