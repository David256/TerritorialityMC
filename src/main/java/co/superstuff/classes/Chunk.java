package co.superstuff.classes;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class Chunk implements Mappable {
    int x;
    int z;

    public Chunk(int x, int z) {
        this.x = x;
        this.z = z;
    }

    @Override
    public String toString() {
        return "Chunk{" +
                "x=" + x +
                ", z=" + z +
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

    @Override
    public Map<String, Object> dumpAsMap() {
        Map<String, Object> map = new HashMap<>();

        map.put("x", x);
        map.put("z", z);

        return map;
    }

    @Nullable
    static public Chunk fromMap(Map<?, ?> map) {
        if (map == null) {
            return null;
        }

        int x = (int) map.get("x");
        int z = (int) map.get("z");

        return new Chunk(x, z);
    }
}
