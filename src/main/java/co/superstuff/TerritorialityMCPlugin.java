package co.superstuff;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class TerritorialityMCPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        System.out.println("Territoriality is on");
    }

    @Override
    public void onDisable() {
        System.out.println("Territoriality is off");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("register")) {
            if (sender instanceof Player player) {
                player.sendMessage("Register...");
            } else if (sender instanceof ConsoleCommandSender) {
                sender.sendMessage("Only user can call this command");
            } else if (sender instanceof BlockCommandSender) {
                System.out.println("The command /register was run by a command block");
            }
        }
        return true;
    }
}
