package co.superstuff.commands;

import co.superstuff.TerritorialityMCPlugin;
import co.superstuff.saved.TerritoryData;
import co.superstuff.saved.UserData;
import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

public class RegistrationProcess implements CommandExecutor {
    private final TerritorialityMCPlugin plugin;

    public RegistrationProcess(TerritorialityMCPlugin plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if (sender instanceof Player player) {

            if (strings.length == 0) {
                player.sendMessage(ChatColor.DARK_PURPLE + "Name! I need the name");
                player.sendMessage(ChatColor.GREEN + "Please, give the territory name:");
                return false;
            }

            String territoryName = String.join(" ", strings);
            TerritoryData territoryData = registerPlayer(player, territoryName);

            if (territoryData != null) {
                // Join the user to this territory
                UserData userData = this.plugin.getUserDataLoader().addUser(player, territoryData);
                System.out.println("User created: " + userData.getUsername());

                player.sendMessage(ChatColor.YELLOW + "You have been joined to " + territoryData.getName());
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

    private TerritoryData registerPlayer(Player player, String territoryName) {
        player.sendMessage("Registering...");

        // Code here...
        String ownerId = player.getUniqueId().toString();

        // Find the player's territory
        TerritoryData territoryData = this.plugin.getTerritoryDataLoader().getTerritoryByOwnerId(ownerId);
        if (territoryData == null) {
            System.out.println("It's going to founding \"" + territoryName + "\n");

            // Save the territory data
            TerritoryData createdTerritoryData = this.plugin.getTerritoryDataLoader().create(territoryName, player);

            player.sendMessage("Right, created: " + createdTerritoryData.getName());

            return createdTerritoryData;

        } else {
            player.sendMessage(ChatColor.DARK_PURPLE + "You have a territory already");
            return null;

        }
    }
}
