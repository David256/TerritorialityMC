package co.superstuff.classes;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;

import javax.annotation.Nonnull;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Class to handle the Chunk position that can be belonged by {@link Plot}.
 */
@SerializableAs("Chunk")
public class Chunk implements ConfigurationSerializable {
    private final int x;
    private final int z;

    /**
     * Constructor.
     * @param x The X coordinate of this chunk
     * @param z The Z coordinate of this chunk
     */
    public Chunk(int x, int z) {
        this.x = x;
        this.z = z;
    }

    /**
     * Constructor to deserialize the object.
     * @param map a {@link Map}
     */
    public Chunk(Map<String, Object> map) {
        x = (int) map.get("x");
        z = (int) map.get("z");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chunk chunk = (Chunk) o;
        return x == chunk.x && z == chunk.z;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, z);
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
