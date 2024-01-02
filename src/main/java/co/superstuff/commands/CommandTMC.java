package co.superstuff.commands;

import co.superstuff.Territoriality;
import co.superstuff.classes.Territory;
import co.superstuff.items.TerritoryTurretItem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

public class CommandTMC implements CommandExecutor, TabCompleter {
    @Override
    public List<String> onTabComplete(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {
        List<String> hints = new ArrayList<>();

        switch (args.length) {
            case 1 -> {
                hints.add("register");
                hints.add("help");
                hints.add("delete");
            }
            case 2 -> {
                switch (args[0]) {
                    case "register" -> {
                        hints.add("<territory name>");
                    }
                    case "delete" -> {
                        hints.add(" ");
                        hints.add("id");
                        hints.add("owner");
                    }
                }
            }
            case 3 -> {
                if (args[0].equals("delete")) {
                    switch (args[1]) {
                        case "id" -> {
                            hints.add("<id>");
                        }
                        case "owner" -> {
                            hints.add("<owner name>");
                        }
                    }
                }
            }
        }

        return hints;
    }

    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {
        if (args.length < 1) {
            helpCommand(sender, command, label, args);
            return true;
        }

        String subcommand = args[0];

        return switch (subcommand) {
            case "register" -> registerCommand(sender, command, label, args);
            case "delete" -> deleteCommand(sender, command, label, args);
            case "help" -> helpCommand(sender, command, label, args);
            default -> false;
        };
    }

    private boolean registerCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            return onCommandRegister(player, command, label, args);
        } else {
            sender.sendMessage("This command is for player");
            return true;
        }
    }

    private boolean deleteCommand(CommandSender sender, Command command, String label, String[] args) {
        @Nullable String keyType = null;
        if (args.length > 1) {
            keyType = args[1];
        }
        @Nullable String data;
        if (args.length > 2) {
            data = args[2];
        } else {
            data = null;
        }

        if (keyType != null && data != null) {
            switch (keyType) {
                case "id" -> {
                    boolean deleted = Territoriality.deleteTerritoryById(data);
                    if (deleted) {
                        sender.sendMessage(ChatColor.GOLD + "Territory deleted");
                    } else {
                        sender.sendMessage(ChatColor.RED + "Cannot delete the territory with that id");
                    }
                }
                case "owner" -> {
                    OfflinePlayer offlinePlayer = Arrays.stream(Bukkit.getOfflinePlayers()).filter(player -> player.getName() != null && player.getName().equals(data)).findFirst().orElse(null);
                    if (offlinePlayer == null) {
                        sender.sendMessage(ChatColor.RED + "Cannot find the player named: " + data);
                    } else {
                        boolean deleted = Territoriality.deleteTerritoryByOwnerId(offlinePlayer.getUniqueId().toString());
                        if (deleted) {
                            sender.sendMessage(ChatColor.GOLD + "Territory deleted for owner: " + data);
                        } else {
                            sender.sendMessage(ChatColor.RED + "Cannot delete that territory");
                        }
                    }
                }
            }
        } else {
            if (!(sender instanceof Player player)) {
                sender.sendMessage(ChatColor.GOLD + "This command is for player");
                sender.sendMessage(ChatColor.GOLD + "But you can use /delete <id|owner> <value>");
                return true;
            }

            Territoriality.deleteTerritoryByOwner(player);
        }

        return true;
    }

    private boolean onCommandRegister(Player player, Command command, String s, String[] strings) {
        if (strings.length == 1) {
            player.sendMessage(ChatColor.DARK_PURPLE + "Name! I need the name");
            player.sendMessage(ChatColor.GREEN + "Please, give the territory name:");
            return false;
        }

        StringJoiner stringJoiner = new StringJoiner(" ");
        for (int i = 1; i < strings.length; i++) {
            stringJoiner.add(strings[i]);
        }
        String territoryName = stringJoiner.toString();
        Territory territory = Territoriality.createTerritory(player, territoryName);

        if (territory != null) {
            player.sendMessage(ChatColor.AQUA + "Territory created");
            player.sendMessage(ChatColor.YELLOW + "You have been joined to " + territory.getName());

            /*
            Give the user a territory turret generator
             */

            ItemStack itemStack = TerritoryTurretItem.create(territoryName, territory.getId());

            // Give the user the new item
            player.getInventory().addItem(itemStack);
        }

        return true;
    }

    private boolean helpCommand(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage(ChatColor.GOLD + "Usage of the command /tmc:");
        sender.sendMessage(ChatColor.GOLD + "    register - Register the user as landowner.");
        return true;
    }
}
