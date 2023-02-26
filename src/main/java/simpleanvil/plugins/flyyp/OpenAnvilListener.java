package simpleanvil.plugins.flyyp;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class OpenAnvilListener implements Listener {
    @EventHandler
    public void onAnvilClick (PlayerInteractEvent event) {
        if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
        if (!event.getClickedBlock().getType().equals(Material.ANVIL)) return;
        event.setCancelled(true);
    }

}
