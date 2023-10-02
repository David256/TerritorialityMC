package co.superstuff.classes;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ExtensionPlotTest {

    @DisplayName("Load a ExtensionPlot without chunks")
    @Test
    public void loadWithoutChunks() {
        Map<String, Object> map = new HashMap<>();

        map.put("x", 34);
        map.put("z", -400);
        map.put("life", 3);
        map.put("territoryId", "id-1000");

        ExtensionPlot mainPlot = ExtensionPlot.fromMap(map);

        assertNotNull(mainPlot);
        assertEquals(34, mainPlot.getX());
        assertEquals(-400, mainPlot.getZ());
        assertEquals(3, mainPlot.getLife());
        assertEquals("id-1000", mainPlot.getTerritoryId());
        assertEquals(0, mainPlot.getChunks().size());
    }

    @DisplayName("Load a ExtensionPlot with chunks")
    @Test
    public void loadWithChunks() {
        Map<String, Object> map = new HashMap<>();

        ArrayList<Map<String, Object>> chunks = new ArrayList<>();
        chunks.add(new Chunk(100, 200).dumpAsMap());
        chunks.add(new Chunk(-100, -200).dumpAsMap());

        map.put("x", 34);
        map.put("z", -400);
        map.put("life", 3);
        map.put("territoryId", "id-2000");
        map.put("chunks", chunks);

        ExtensionPlot mainPlot = ExtensionPlot.fromMap(map);

        assertNotNull(mainPlot);
        assertEquals(34, mainPlot.getX());
        assertEquals(-400, mainPlot.getZ());
        assertEquals(3, mainPlot.getLife());
        assertEquals("id-2000", mainPlot.getTerritoryId());
        assertEquals(2, mainPlot.getChunks().size());

        assertEquals(100, mainPlot.getChunks().get(0).getX());
        assertEquals(200, mainPlot.getChunks().get(0).getZ());

        assertEquals(-100, mainPlot.getChunks().get(1).getX());
        assertEquals(-200, mainPlot.getChunks().get(1).getZ());
    }

    @DisplayName("dump a ExtensionPlot object")
    @Test
    public void dumpAsMap() {
        List<Chunk> chunks = new ArrayList<>();

        chunks.add(new Chunk(30, 30));
        chunks.add(new Chunk(100, -1));
        chunks.add(new Chunk(0, 2));

        ExtensionPlot mainPlot = new ExtensionPlot(100, -100, 10, "id-1000", chunks);

        Map<String, Object> map = mainPlot.dumpAsMap();

        assertEquals(100, map.get("x"));
        assertEquals(-100, map.get("z"));
        assertEquals(10, map.get("life"));
        assertEquals("id-1000", map.get("territoryId"));

        Object dumpedChunks = map.get("chunks");

        if (dumpedChunks instanceof ArrayList<?> items) {
            ArrayList<Object> chunkObjects = new ArrayList<>(items);
            ArrayList<Chunk> gotChunks = new ArrayList<>();
            for (Object item: chunkObjects) {
                gotChunks.add(Chunk.fromMap((Map<?, ?>) item));
            }

            assertEquals(3, gotChunks.size());

            assertEquals(30, gotChunks.get(0).getX());
            assertEquals(30, gotChunks.get(0).getZ());
            assertEquals(100, gotChunks.get(1).getX());
            assertEquals(-1, gotChunks.get(1).getZ());
            assertEquals(0, gotChunks.get(2).getX());
            assertEquals(2, gotChunks.get(2).getZ());
        } else {
            fail("Dumped chunks are not a list");
        }
    }
}