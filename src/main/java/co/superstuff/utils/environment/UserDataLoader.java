package co.superstuff.utils.environment;

import co.superstuff.saved.TerritoryData;
import co.superstuff.saved.UserData;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nullable;
import java.io.*;

public class UserDataLoader extends DataLoader {
    public UserDataLoader(File dataFile) {
        super(dataFile);
    }

    public static UserDataLoader fromDefault(JavaPlugin plugin) {
        return new UserDataLoader(new File(plugin.getDataFolder(), "users.yml"));
    }

    @Nullable
    public UserData findUserById(String id) {
        return null;
    }

    public UserData addUser(Player player, TerritoryData territoryData) {
        String uid = player.getUniqueId().toString();
        return addUser(uid, player.getName(), territoryData.getId());
    }

    public UserData addUser(String playerId, String playerName, String territoryId) {
        UserData userData = new UserData(
                playerId,
                playerName,
                territoryId
        );

        String userId = userData.getId();
        System.out.println("Creating user Id: " + userId);

        config.set(userId + ".id", userId);
        config.set(userId + ".username", userData.getUsername());
        config.set(userId + "territoryId", userData.getTerritoryId());

        save();

        return userData;
    }
}
