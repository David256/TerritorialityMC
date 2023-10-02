package co.superstuff.classes;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class PersistentManager {
    protected File file;
    protected YamlConfiguration config;

    public PersistentManager(File file) {
        this.file = file;

        config = new YamlConfiguration();

        reload();
    }

    @Override
    public String toString() {
        return "PersistentManager{" +
                "file=" + file +
                ", config=" + config +
                '}';
    }

    public void reload() {
        config = YamlConfiguration.loadConfiguration(file);
    }

    public void save() {
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void set(String path, Object object) {
        config.set(path, object);
    }

    /**
     * It will be removed, but for how it exists.
     * @return YamlConfiguration
     */
    public YamlConfiguration getConfig() {
        return config;
    }
}
