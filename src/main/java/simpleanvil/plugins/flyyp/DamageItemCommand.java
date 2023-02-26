package simpleanvil.plugins.flyyp;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DamageItemCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {
        Player player = (Player)sender;

        int durability = player.getItemInHand().getDurability();

        player.sendMessage("Aktualne zużycie itemu: " + durability);

        if (durability >= 0) {
            player.getItemInHand().setDurability((short) (durability + 400));
            sender.sendMessage("Przedmiot został uszkodzony");
        }
        return true;
    }
}