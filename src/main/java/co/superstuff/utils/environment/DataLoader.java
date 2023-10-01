package co.superstuff.utils.environment;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public abstract class DataLoader {
    protected File file;

    protected YamlConfiguration config;

    @Deprecated
    public DataLoader(JavaPlugin plugin, String filename) {
        file = new File(plugin.getDataFolder(), filename);
    }


    public DataLoader(File file) {
        config = new YamlConfiguration();

        if (!file.exists()) {
            /*
             * Create the plugin folder
             */
            if (file.getParentFile().mkdirs()) {
                System.out.println(file.getParentFile().toString() + " folder is created");
            }

            try {
                if (file.createNewFile()) {
                    System.out.println(file.getName() + "file created as empty file");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        reload();
    }

    public void save() {
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reload() {
        config = YamlConfiguration.loadConfiguration(file);
    }
}
