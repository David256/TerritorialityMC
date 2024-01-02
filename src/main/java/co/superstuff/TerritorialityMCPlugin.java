package co.superstuff;
import co.superstuff.classes.*;
import co.superstuff.commands.CommandTMC;
import co.superstuff.events.OnPutTerritoryTurret;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

public class TerritorialityMCPlugin extends JavaPlugin {
    public static String TERRITORY_TURRET_GENERATOR_ID = "territory_generator";
    public static String TERRITORIES_FILENAME = "territories.yml";
    public static String MEMBERS_FILENAME = "members.yml";
    private static TerritorialityMCPlugin instance;
    private YamlConfiguration territoryPersistent = null;
    private YamlConfiguration memberPersistent = null;

    @Override
    public void onEnable() {
        instance = this;
        System.out.println("Territoriality is on");

        ConfigurationSerialization.registerClass(Chunk.class);
        ConfigurationSerialization.registerClass(Plot.class);
        ConfigurationSerialization.registerClass(Member.class);
        ConfigurationSerialization.registerClass(Territory.class);
        ConfigurationSerialization.registerClass(MainPlot.class);
        ConfigurationSerialization.registerClass(ExtensionPlot.class);
        ConfigurationSerialization.registerClass(TerritorialTurret.class);

        reloadConfig();

        loadTerritories();
        loadMembers();

        CommandTMC commandTMC = new CommandTMC();

        FileConfiguration config = getConfig();

        int maxTerritories = config.getInt("max-territories");
        System.out.println("max of territories is: " + maxTerritories);

        PluginCommand tmcCommand = getCommand("tmc");
        if (tmcCommand != null) {
            tmcCommand.setExecutor(commandTMC);
            tmcCommand.setTabCompleter(commandTMC);
        }

        getServer().getPluginManager().registerEvents(new OnPutTerritoryTurret(), this);
    }

    @Override
    public void onDisable() {
        System.out.println("Territoriality is off");
    }

    @Override
    public void reloadConfig() {
        super.reloadConfig();

        saveDefaultConfig();

        FileConfiguration config = getConfig();

        config.addDefault("max-territories", 10);

        config.options().copyDefaults(true);

        saveConfig();

        saveResource(TERRITORIES_FILENAME, false);
        saveResource(MEMBERS_FILENAME, false);

        loadTerritories();
        loadMembers();
    }

    public static TerritorialityMCPlugin getInstance() {
        return instance;
    }

    public void loadTerritories() {
        Logger logger = getLogger();

        territoryPersistent = YamlConfiguration.loadConfiguration(new File(getDataFolder(), TERRITORIES_FILENAME));

        List<?> rawTerritories = (List<?>) territoryPersistent.get("territories", Territory.class);

        Territoriality.getTerritories().clear();

        rawTerritories.forEach(raw -> {
            if (raw instanceof Territory territory) {
                Territoriality.getTerritories().add(territory);
            }
        });

        logger.info(Territoriality.getTerritories().size() + " territories loaded");
    }

    public void saveTerritories() {
        Logger logger = getLogger();

        territoryPersistent.set("territories", Territoriality.getTerritories());

        try {
            territoryPersistent.save(new File(getDataFolder(), TERRITORIES_FILENAME));
        } catch (IOException err) {
            logger.severe("Cannot save territories: " + err.getMessage());
        }
    }

    public void loadMembers() {
        Logger logger = getLogger();

        memberPersistent = YamlConfiguration.loadConfiguration(new File(getDataFolder(), MEMBERS_FILENAME));

        List<?> rawMembers = (List<?>) memberPersistent.get("members", Member.class);

        Territoriality.getMembers().clear();

        rawMembers.forEach(raw -> {
            if (raw instanceof Member member) {
                Territoriality.getMembers().add(member);
            }
        });

        logger.info(Territoriality.getMembers().size() + " members loaded");
    }

    public void saveMembers() {
        Logger logger = getLogger();

        memberPersistent.set("members", Territoriality.getMembers());

        try {
            memberPersistent.save(new File(getDataFolder(), MEMBERS_FILENAME));
        } catch (IOException err) {
            logger.severe("Cannot save members: " + err.getMessage());
        }
    }
}
