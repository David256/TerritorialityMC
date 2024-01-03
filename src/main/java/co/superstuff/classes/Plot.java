package co.superstuff.classes;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SerializableAs("Plot")
abstract public class Plot implements ConfigurationSerializable {
    protected int x;
    protected int z;
    protected int life;
    protected @Nullable TerritorialTurret turret;
    protected List<Chunk> chunks;
    String territoryId;

    public Plot(int x, int z, int life, String territoryId) {
        this.x = x;
        this.z = z;
        this.life = life;
        this.territoryId = territoryId;
        this.chunks = new ArrayList<>();
        this.turret = null;
    }

    public Plot(int x, int z, int life, String territoryId, List<Chunk> chunks) {
        this(x, z, life, territoryId);
        this.territoryId = territoryId;
        this.chunks = chunks;
    }

    public Plot(Map<String, Object> map) {
        x = (int) map.get("x");
        z = (int) map.get("z");
        life = (int) map.get("life");

        chunks = new ArrayList<>();
        List<?> rawChunks = (List<?>) map.get("chunks");
        if (rawChunks != null) {
            rawChunks.forEach(rawChunk -> {
                if (rawChunk instanceof Chunk chunk) {
                    chunks.add(chunk);
                }
            });
        }
        turret = (TerritorialTurret) map.get("turret");

        territoryId = (String) map.get("territoryId");
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

    public boolean contains(Chunk chunk) {
        return chunks.contains(chunk);
    }

    public double calcOverlap(Plot plot) {
        if (chunks.isEmpty()) return 0d;

        double factor = plot.getChunks().stream().map(otherChunk -> {
           if (chunks.contains(otherChunk)) {
               return 1.0;
           }
           return 0.0;
        }).count();

        return factor / chunks.size();
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

    public void setTurret(TerritorialTurret turret) {
        this.turret = turret;
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
        map.put("turret", turret);

        return map;
    }
}
