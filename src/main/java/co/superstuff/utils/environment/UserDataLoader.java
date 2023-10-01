package co.superstuff.utils.environment;

import co.superstuff.saved.TerritoryData;
import co.superstuff.saved.UserData;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nullable;
import java.io.*;

public class UserDataLoader extends DataLoader {
    public UserDataLoader(JavaPlugin plugin) {
        super(new File(plugin.getDataFolder(), "users.yml"));
    }

    public UserDataLoader(File dataFile) {
        super(dataFile);
    }

    @Nullable
    public UserData findUserById(String id) {
        return null;
    }

    public void addUser(Player player, TerritoryData territoryData) {
        String uid = player.getUniqueId().toString();
        UserData userData = new UserData(
                uid,
                player.getName(),
                territoryData.getId()
        );

    }
}
