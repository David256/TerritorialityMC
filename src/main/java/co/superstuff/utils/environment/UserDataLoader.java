package co.superstuff.utils.environment;

import co.superstuff.saved.TerritoryData;
import co.superstuff.saved.UserData;
import org.bukkit.entity.Player;
import org.yaml.snakeyaml.Yaml;

import javax.annotation.Nullable;
import java.io.*;
import java.util.Map;

public class UserDataLoader extends FileDataLoader<String, UserData> {
    private final DataLoader<String, UserData> dataLoader;
    public UserDataLoader(File file) {
        super(file);

        dataLoader = new DataLoader<>();

        reload();
    }

    @Nullable
    public UserData findUserById(String id) {
        return dataLoader.get().get(id);
    }

    public void addUser(Player player, TerritoryData territoryData) {
        String uid = player.getUniqueId().toString();
        UserData userData = new UserData(
                uid,
                player.getName(),
                territoryData.getId()
        );

        Map<String, UserData> userDataMap = dataLoader.get();
        userDataMap.put(uid, userData);
        dataLoader.update(userDataMap);
        save();
    }
}
