package co.superstuff;
import co.superstuff.classes.Member;
import co.superstuff.classes.PersistentManager;
import co.superstuff.classes.Territory;
import co.superstuff.commands.RegistrationProcess;
import co.superstuff.utils.plugin.CustomConfig;
import org.bukkit.Bukkit;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.*;

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

        reloadTerritories();
        reloadMembers();

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

    public void reloadTerritories() {
        Set<String> territoryKeySet = territoryPersistentManager.getConfig().getKeys(false);
        territoryKeySet.forEach(territoryId -> {
            MemorySection thing = (MemorySection) territoryPersistentManager.getConfig().get(territoryId);
            if (thing == null) {
                return;
            }
            Map<String, Object> item = thing.getValues(true);

            Territory territory = Territory.fromMap(item);
            if (territory == null) {
                System.err.println("Cannot load territory");
            } else {
                territories.add(territory);
                System.out.println("loads territory: " + territory.getName());
                System.out.println(territory);
            }
        });

        System.out.println(territories.size() + " territories loaded");
    }

    public void reloadMembers() {
        memberPersistentManager.getConfig().getKeys(false).forEach(memberId -> {
            MemorySection thing = (MemorySection) memberPersistentManager.getConfig().get(memberId);
            if (thing == null) {
                return;
            }

            Map<String, Object> item = thing.getValues(true);
            Member member = Member.fromMap(item);
            if (member == null) {
                System.err.println("Cannot load member");
            } else {
                members.add(member);
                System.out.println("Loads member: " + member.getName());
            }
        });

        System.out.println(members.size() + " members loaded");
    }
}
