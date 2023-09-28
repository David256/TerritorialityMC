package co.superstuff;
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
}
