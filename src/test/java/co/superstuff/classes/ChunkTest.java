package co.superstuff.classes;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ChunkTest {

    @Test
    void dumpAsMap() {
        Chunk chunk = new Chunk(30, 50);

        Map<String, Object> map = chunk.dumpAsMap();

        assertEquals(30, map.get("x"));
        assertEquals(50, map.get("z"));
    }

    @Test
    void fromMap() {
        Map<String, Object> map = new HashMap<>();

        map.put("x",-400);
        map.put("z", 0);

        Chunk chunk = Chunk.fromMap(map);

        assertNotNull(chunk);
        assertEquals(-400, chunk.getX());
        assertEquals(0, chunk.getZ());
    }
}