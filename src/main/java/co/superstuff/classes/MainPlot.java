package co.superstuff.classes;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainPlot extends Plot {
    public MainPlot(int x, int z, int life, String territoryId) {
        super(x, z, life, territoryId);
    }

    public MainPlot(int x, int z, int life, String territoryId, List<Chunk> chunks) {
        super(x, z, life, territoryId, chunks);
    }

    @Override
    public String toString() {
        return "MainPlot{" +
                "x=" + x +
                ", z=" + z +
                ", life=" + life +
                ", chunks.size()=" + chunks.size() +
                ", territoryId='" + territoryId + '\'' +
                '}';
    }

    @Nullable
    static MainPlot fromMap(Map<?, ?> map) {
        if (map == null) {
            return null;
        }

        int x = (int) map.get("x");
        int z = (int) map.get("z");
        int life = (int) map.get("life");
        String territoryId = (String) map.get("territoryId");

        List<Chunk> loadedChunks = new ArrayList<>();

        Object chunksObj = map.get("chunks");

        if (chunksObj instanceof ArrayList<?> list) {
            for (Object item : list) {
                if (item instanceof Map<?, ?> itemMap) {

                    Map<String, Object> temporalMap = new HashMap<>();

                    for (Map.Entry<?, ?> entry :
                            itemMap.entrySet()) {
                        if (entry.getKey() instanceof String && entry.getValue() != null) {
                            temporalMap.put((String) entry.getKey(), entry.getValue());
                        }
                    }

                    Chunk chunk = Chunk.fromMap(temporalMap);
                    loadedChunks.add(chunk);
                }
            }
        }

        return new MainPlot(x, z, life, territoryId, loadedChunks);
    }
}
