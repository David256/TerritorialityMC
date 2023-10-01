package co.superstuff.utils.environment;

import co.superstuff.saved.ChunkData;
import co.superstuff.saved.TerritoryData;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

public class TerritoryDataLoader extends DataLoader {

    public TerritoryDataLoader(JavaPlugin plugin) {
        super(new File(plugin.getDataFolder(), "territories.yml"));
    }

    public TerritoryDataLoader(File dataFile) {
        super(dataFile);
    }

    // TODO: Add method to avoid that two territories have the same name

    public TerritoryData create(String name, Player player) {
        String uid = player.getUniqueId().toString();

        TerritoryData territoryData = new TerritoryData(name, uid);

        String territoryId = territoryData.getId();
        System.out.println("Creating territory Id: " + territoryId);
        config.set(territoryId + ".id", territoryId);
        config.set(territoryId + ".name", territoryData.getName());
        config.set(territoryId + ".ownerId", territoryData.getOwnerId());
        config.set(territoryId + ".chunkDataList", territoryData.getChunkDataList());

        save();

        return territoryData;
    }

    public TerritoryData getTerritoryByOwnerId(String ownerId) {
        TerritoryData territoryData = null;
        Set<String> set = config.getKeys(false);

        for (String territoryId : set) {
            String foundOwnerId = config.getString(territoryId + ".ownerId");

            if (foundOwnerId != null && foundOwnerId.equals(ownerId)) {
                territoryData = new TerritoryData(
                        territoryId,
                        config.getString(territoryId + ".name"),
                        foundOwnerId,
                        (ArrayList<ChunkData>) config.getList(territoryId + ".chunkDataList", new ArrayList<ChunkData>())
                );
                break;
            }
        }

        return territoryData;
    }
}
