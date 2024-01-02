package co.superstuff.classes;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExtensionPlot extends Plot {
    public ExtensionPlot(int x, int z, int life, String territoryId, List<Chunk> chunks) {
        super(x, z, life, territoryId, chunks);
    }

    @Override
    public String toString() {
        return "ExtensionPlot{" +
                "x=" + x +
                ", z=" + z +
                ", life=" + life +
                ", chunks.size()=" + chunks.size() +
                ", territoryId='" + territoryId + '\'' +
                '}';
    }
}
