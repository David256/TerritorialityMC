package co.superstuff.classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

abstract public class Plot implements Mappable {
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
        this.x = x;
        this.z = z;
        this.life = life;
        this.chunks = chunks;
        this.territoryId = territoryId;
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

    public void setX(int x) {
        this.x = x;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public List<Chunk> getChunks() {
        return chunks;
    }

    public void setChunks(List<Chunk> chunks) {
        this.chunks = chunks;
    }

    public String getTerritoryId() {
        return territoryId;
    }

    public void setTerritoryId(String territoryId) {
        this.territoryId = territoryId;
    }

    @Override
    public Map<String, Object> dumpAsMap() {
        Map<String, Object> map = new HashMap<>();

        map.put("x", x);
        map.put("z", z);
        map.put("life", life);
        map.put("territoryId", territoryId);

        List<Map<String, Object>> chunkMaps = new ArrayList<>();

        for (Chunk chunk: chunks) {
            chunkMaps.add(chunk.dumpAsMap());
        }

        map.put("chunks", chunkMaps);

        return map;
    }
}
