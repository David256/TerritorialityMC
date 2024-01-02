package co.superstuff.classes;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

@SerializableAs("Chunk")
public class Chunk implements ConfigurationSerializable {
    int x;
    int z;

    public Chunk(int x, int z) {
        this.x = x;
        this.z = z;
    }

    public Chunk(Map<String, Object> map) {
        x = (int) map.get("x");
        z = (int) map.get("z");
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

    public int getZ() {
        return z;
    }

    @Override
    @Nonnull
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();

        map.put("x", x);
        map.put("z", z);

        return map;
    }
}
