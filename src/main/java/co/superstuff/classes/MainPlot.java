package co.superstuff.classes;

import org.bukkit.configuration.serialization.SerializableAs;

import java.util.List;


@SerializableAs("MainPlot")
public class MainPlot extends Plot {
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
}
