package co.superstuff;
import co.superstuff.classes.Member;
import co.superstuff.classes.PersistentManager;
import co.superstuff.classes.Territory;
import co.superstuff.commands.RegistrationProcess;
import co.superstuff.utils.plugin.CustomConfig;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TerritorialityMCPlugin extends JavaPlugin {

    RegistrationProcess registrationProcess;

    private List<Territory> territories;
    private List<Member> members;
    private PersistentManager territoryPersistentManager;
    private PersistentManager memberPersistentManager;

    @Override
    public void onEnable() {
        System.out.println("Territoriality is on");

        territories = new ArrayList<>();
        members = new ArrayList<>();

        territoryPersistentManager = new PersistentManager(new File(getDataFolder(), "territories.yml"));
        memberPersistentManager = new PersistentManager(new File(getDataFolder(), "users.yml"));

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

    public List<Territory> getTerritories() {
        return territories;
    }

    public List<Member> getMembers() {
        return members;
    }

    public PersistentManager getTerritoryPersistentManager() {
        return territoryPersistentManager;
    }

    public PersistentManager getMemberPersistentManager() {
        return memberPersistentManager;
    }
}
