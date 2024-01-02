package co.superstuff.classes;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SerializableAs("Plot")
abstract public class Plot implements ConfigurationSerializable {
    int x;
    int z;
    int life;
    List<Chunk> chunks;
    String territoryId;

    public Plot(int x, int z, int life, String territoryId) {
        this.x = x;
        this.z = z;
        this.life = life;
        this.territoryId = territoryId;
        this.chunks = new ArrayList<>();
    }

    public Plot(int x, int z, int life, String territoryId, List<Chunk> chunks) {
        this(x, z, life, territoryId);
        this.territoryId = territoryId;
        this.chunks = chunks;
    }

    @Override
    public String toString() {
        return "Plot{" +
                "x=" + x +
                ", z=" + z +
                ", life=" + life +
                ", chunks.size()=" + chunks.size() +
                ", territoryId='" + territoryId + '\'' +
                '}';
    }

    public int getX() {
        return x;
    }

    public int getZ() {
        return z;
    }

    public int getLife() {
        return life;
    }

    public List<Chunk> getChunks() {
        return chunks;
    }

    public String getTerritoryId() {
        return territoryId;
    }

    @Override
    @Nonnull
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();

        map.put("x", x);
        map.put("z", z);
        map.put("life", life);
        map.put("territoryId", territoryId);
        map.put("chunks", chunks);

        return map;
    }
}
