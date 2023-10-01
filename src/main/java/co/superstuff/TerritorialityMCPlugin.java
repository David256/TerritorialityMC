package co.superstuff;
import co.superstuff.commands.RegistrationProcess;
import co.superstuff.utils.environment.TerritoryDataLoader;
import co.superstuff.utils.environment.UserDataLoader;
import co.superstuff.utils.plugin.CustomConfig;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class TerritorialityMCPlugin extends JavaPlugin {

    RegistrationProcess registrationProcess;

    private TerritoryDataLoader territoryDataLoader;

    private UserDataLoader userDataLoader;

    @Override
    public void onEnable() {
        System.out.println("Territoriality is on");

        territoryDataLoader = TerritoryDataLoader.fromDefault(this);
        userDataLoader = UserDataLoader.fromDefault(this);

        registrationProcess = new RegistrationProcess(this);

        CustomConfig.setUp(this);
        YamlConfiguration config = CustomConfig.get();
        int maxTerritories = config.getInt("max-territories");
        System.out.println("max of territories is: " + maxTerritories);

        Objects.requireNonNull(getCommand("register")).setExecutor(registrationProcess);

        Runnable runnable = () -> {
            Bukkit.getServer().broadcastMessage("Pues venga");
        };

        Bukkit.getScheduler().scheduleSyncDelayedTask(this, runnable, 2L * 20);
    }

    @Override
    public void onDisable() {
        System.out.println("Territoriality is off");
    }

    public TerritoryDataLoader getTerritoryDataLoader() {
        return territoryDataLoader;
    }

    public UserDataLoader getUserDataLoader() {
        return userDataLoader;
    }
}
