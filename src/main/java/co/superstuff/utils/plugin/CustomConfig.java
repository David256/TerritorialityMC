package co.superstuff.utils.plugin;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;

public class CustomConfig {

    private static File file;
    private static YamlConfiguration config;

    public static void setUp(JavaPlugin plugin) {

        file = new File(plugin.getDataFolder(), "config.yml");

        if (!file.exists()) {
            /*
             * Create the plugin folder
             */
            if (file.getParentFile().mkdirs()) {
                System.out.println(file.getParentFile().toString() + " folder is created");
            }

            /*
             * Take the resource config and copy its content in the plugin config file.
             */
            InputStream is = plugin.getResource("config.yml");
            if (is != null) {
                try {
                    copyFromDefault(is);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        reload();
    }

    public static void save() {
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void reload() {
        config = YamlConfiguration.loadConfiguration(file);
    }

    public static YamlConfiguration get() {
        return config;
    }

    private static void copyFromDefault(InputStream is) throws IOException {
        FileOutputStream fos = new FileOutputStream(file);
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = is.read(buffer)) != -1) {
            fos.write(buffer, 0, bytesRead);
        }

        fos.close();
    }
}
