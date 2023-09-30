package co.superstuff.utils.environment;

import co.superstuff.saved.TerritoryData;
import co.superstuff.saved.UserData;
import org.bukkit.entity.Player;

import java.io.File;

public class TerritoryDataLoader extends FileDataLoader<String, TerritoryData> {

    public TerritoryDataLoader(File file) {
        super(file);
    }

    // TODO: Add method to avoid that two territories have the same name

    public TerritoryData create(String name, Player player) {
        String uid = player.getUniqueId().toString();

        TerritoryData territoryData = new TerritoryData(name, uid);

        dataLoader.put(territoryData.getId(), territoryData);

        return territoryData;
    }


}
