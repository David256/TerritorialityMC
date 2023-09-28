package co.superstuff.saved;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChunkDataTest {

    private ChunkData chunk;
    private ChunkData chunkLifeDefault;

    @BeforeEach
    public void setUp() {
        chunk = new ChunkData(10, 30, "id-001", (short) 500);
        chunkLifeDefault = new ChunkData(0, 0, "id-000");
    }


    @DisplayName("The coordinate X is saved")
    @Test
    void getX() {
        assertEquals(10, chunk.getX());
    }

    @DisplayName("The coordinate X is updated")
    @Test
    void setX() {
        chunk.setX(30);
        assertEquals(30, chunk.getX());
    }

    @DisplayName("The coordinate Z is saved")
    @Test
    void getZ() {
        assertEquals(30, chunk.getZ());
    }

    @DisplayName("The coordinate Z is updated")
    @Test
    void setZ() {
        chunk.setZ(99);
        assertEquals(99, chunk.getZ());;
    }

    @DisplayName("Get the territory ID")
    @Test
    void getTerritoryId() {
        assertEquals("id-001", chunk.getTerritoryId());
    }

    @DisplayName("Set the territory ID")
    @Test
    void setTerritoryId() {
        chunk.setTerritoryId("id-009");
        assertEquals("id-009", chunk.getTerritoryId());
    }

    @DisplayName("Get the life of the chunk")
    @Test
    void getLife() {
        assertEquals((short) 500, chunk.getLife());
    }
    @DisplayName("Set the life of the chunk")
    @Test
    void setLife() {
        chunk.setLife((short) 10);
        assertEquals((short) 10, chunk.getLife());
    }

    @DisplayName("Get the default life value")
    @Test
    void getDefaultLife() {
        assertEquals(ChunkData.MAX_LIFE, chunkLifeDefault.getLife());
    }
}