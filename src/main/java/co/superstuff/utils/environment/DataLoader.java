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
        this.file = file;
        config = new YamlConfiguration();

        avoidNonExistentFile(file);

        reload();
    }

    public void avoidNonExistentFile(File file) {
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
