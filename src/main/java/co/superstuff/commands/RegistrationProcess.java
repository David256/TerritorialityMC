package co.superstuff.commands;

import co.superstuff.TerritorialityMCPlugin;
import co.superstuff.classes.Member;
import co.superstuff.classes.Territory;
import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

public class RegistrationProcess implements CommandExecutor {
    private final TerritorialityMCPlugin plugin;

    public RegistrationProcess(TerritorialityMCPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if (sender instanceof Player player) {

            if (strings.length == 0) {
                player.sendMessage(ChatColor.DARK_PURPLE + "Name! I need the name");
                player.sendMessage(ChatColor.GREEN + "Please, give the territory name:");
                return false;
            }

            String territoryName = String.join(" ", strings);
            Territory territory = registerPlayer(player, territoryName);

            if (territory != null) {
                // Join the user to this territory
                Member member = Member.create(plugin.getMemberPersistentManager(), player, territory);
                System.out.println("Add as member to " + member.getName());

                plugin.getMembers().add(member);
                plugin.getTerritories().add(territory);

                player.sendMessage(ChatColor.YELLOW + "You have been joined to " + territory.getName());
            }

            return true;

        } else if (sender instanceof ConsoleCommandSender) {
            sender.sendMessage("Only user can call this command");
        } else if (sender instanceof BlockCommandSender) {
            System.out.println("The command /register was run by a command block");
        } else {
            System.out.println("What");
        }
        return true;
    }

    private Territory registerPlayer(Player player, String territoryName) {
        player.sendMessage("Registering...");

        // Code here...
        String ownerId = player.getUniqueId().toString();

        Territory territory = Territory.findByOwnerId(plugin.getTerritoryPersistentManager(), ownerId);
        if (territory == null) {

            territory = Territory.create(plugin.getTerritoryPersistentManager(), territoryName, player);
            System.out.println("new territory of " + player.getName() + " called " + territory.getName());

            player.sendMessage("Right, created: " + territory.getName());
            return territory;

        } else {
            player.sendMessage(ChatColor.DARK_PURPLE + "You have a territory already");
            return null;

        }
    }
}
