package simpleanvil.plugins.flyyp;

import org.bukkit.Material;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getCommand("damageitem").setExecutor((CommandExecutor) new DamageItemCommand());

        FileConfiguration config = this.getConfig();

        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(new OpenAnvilListener(), this);
    }


    @Override
    public void onDisable() {


    }


    @EventHandler
    public void playerInteract(PlayerInteractEvent event) {
        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            Material material = event.getClickedBlock().getType();
            if (material.equals(Material.ANVIL)) {

                FileConfiguration config = this.getConfig();
                Player player = event.getPlayer();

                int repairCost = config.getInt("itemRepairCost");
                int itemDurability = player.getItemInHand().getDurability();
                int playerLVL = player.getLevel() - config.getInt("itemRepairCost");

                if (itemDurability < -1) {            //powietrze
                    player.sendMessage(config.getString("handIsEmpty"));
                }
                if (itemDurability == 0) {            //blok którego nie da się naprawić
                    player.sendMessage(config.getString("itemCantBeReapired"));
                }
                if (itemDurability > 0) {             //item możliwy do naprawy

                    int durabilityToAdd = config.getInt("howManyUsageRepairShouldAdd");
                    int newDurability = itemDurability - durabilityToAdd;

                    if (repairCost > player.getLevel()) {
                        player.sendMessage(config.getString("tooLowLevel"));
                    }
                    if (repairCost < player.getLevel()) {
                        if (itemDurability > durabilityToAdd) {
                            player.getItemInHand().setDurability((short) newDurability);
                            player.setLevel(playerLVL);
                        }
                        if (itemDurability <= durabilityToAdd) {
                            player.getItemInHand().setDurability((short) 0);
                            player.setLevel(playerLVL);
                        }
                        player.sendMessage(config.getString("itemRepaired"));
                    }
                }
            }
        }
    }
}
