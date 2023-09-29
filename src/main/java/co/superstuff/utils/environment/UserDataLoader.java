package co.superstuff.utils.environment;

import co.superstuff.saved.TerritoryData;
import co.superstuff.saved.UserData;
import org.bukkit.entity.Player;
import org.yaml.snakeyaml.Yaml;

import javax.annotation.Nullable;
import java.io.*;
import java.util.Map;

public class UserDataLoader {
    private final File file;
    private final Yaml yaml;
    private Map<String, UserData> userDataMap;
    public UserDataLoader(File file) {
        this.file = file;
        yaml = new Yaml();

        reload();
    }

    public void reload() {
        try {
            InputStream is = new FileInputStream(file);
            userDataMap = yaml.load(is);

            if (userDataMap == null) {
                System.err.println("Cannot load user data from:" + file.getPath());
            } else if (userDataMap.isEmpty()) {
                System.err.println("Warn: user data is empty. If it is the first time, ignore please");
            }

            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void save() {
        String content = yaml.dump(userDataMap);
        try {
            OutputStream os = new FileOutputStream(this.file);

            os.write(content.getBytes());

            os.close();

            System.out.println("File" + this.file.getPath() + "updated");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Nullable
    public UserData findUserById(String id) {
        return userDataMap.get(id);
    }

    public void addUser(Player player, TerritoryData territoryData) {
        String uid = player.getUniqueId().toString();
        UserData userData = new UserData(
                uid,
                player.getName(),
                territoryData.getId()
        );

        userDataMap.put(uid, userData);

        save();
    }
}
