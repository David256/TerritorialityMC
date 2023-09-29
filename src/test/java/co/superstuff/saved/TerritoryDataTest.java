package co.superstuff.saved;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TerritoryDataTest {

    private String firstTerritoryId;
    private TerritoryData territoryData;
    private TerritoryData territoryDataWithDefaultData;
    private TerritoryData territoryDataWithDefaultId;

    @BeforeEach
    void setUp() {
        firstTerritoryId = "id-001";
        territoryData = new TerritoryData("id-001", "territory-1", "user-100", new ArrayList<ChunkData>(){
            {
                add(new ChunkData(10, 10, firstTerritoryId));
                add(new ChunkData(30, 40, firstTerritoryId));
                add(new ChunkData(-50, 0, firstTerritoryId));
            }
        });

        territoryDataWithDefaultData = new TerritoryData("id-002", "territory-2", "user-200");

        territoryDataWithDefaultId = new TerritoryData("territory-3", "user-300");
    }

    @DisplayName("The ID is saved or automatically generated")
    @Test
    void getId() {
        assertEquals(territoryData.getId(), "id-001");
        assertEquals(territoryDataWithDefaultData.getId(), "id-002");
        assertEquals(territoryDataWithDefaultId.getId(), "id-003");
    }

    @DisplayName("The ID can be changed")
    @Test
    void setId() {
        territoryData.setId("---");
        assertEquals(territoryData.getId(), "---");
    }

    @DisplayName("The territory name is saved")
    @Test
    void getName() {
        assertEquals("territory-1", territoryData.getName());
        assertEquals("territory-2", territoryDataWithDefaultData.getName());
        assertEquals("territory-3", territoryDataWithDefaultId.getName());
    }

    @DisplayName("The name can be changed")
    @Test
    void setName() {
        territoryData.setName("territory-0");
        assertEquals("territory-0", territoryData.getName());
    }

    @DisplayName("The ownerId is saved")
    @Test
    void getOwnerId() {
        assertEquals("user-100", territoryData.getOwnerId());
        assertEquals("user-200", territoryDataWithDefaultData.getOwnerId());
        assertEquals("user-300", territoryDataWithDefaultId.getOwnerId());
    }

    @DisplayName("The ownerId can be changed")
    @Test
    void setOwnerId() {
        territoryData.setOwnerId("user-000");
        assertEquals("user-000", territoryData.getOwnerId());
    }

    @DisplayName("Can get the chunk data list")
    @Test
    void getChunkDataList() {
        ArrayList<ChunkData> chunkDataArrayList = (ArrayList<ChunkData>) territoryData.getChunkDataList();
        assertEquals(3, chunkDataArrayList.size());

        assertEquals(10, chunkDataArrayList.get(0).getX());
        assertEquals(10, chunkDataArrayList.get(0).getZ());
        assertEquals(firstTerritoryId, chunkDataArrayList.get(0).getTerritoryId());

        assertEquals(30, chunkDataArrayList.get(1).getX());
        assertEquals(40, chunkDataArrayList.get(1).getX());
        assertEquals(firstTerritoryId, chunkDataArrayList.get(1).getTerritoryId());

        assertEquals(50, chunkDataArrayList.get(2).getX());
        assertEquals(0, chunkDataArrayList.get(3).getX());
        assertEquals(firstTerritoryId, chunkDataArrayList.get(2).getTerritoryId());
    }

    @DisplayName("Chunk data list can be changed")
    @Test
    void setChunkDataList() {
        ChunkData chunkData = new ChunkData(300, -300, firstTerritoryId);
        ArrayList<ChunkData> chunkDataArrayList = (ArrayList<ChunkData>) territoryData.getChunkDataList();
        chunkDataArrayList.add(chunkData);
        // TODO: TerritoryData should have a method to add ChunkData, I think.
        territoryData.setChunkDataList(chunkDataArrayList);

        ArrayList<ChunkData> chunkDataArrayListUpdated = (ArrayList<ChunkData>) territoryData.getChunkDataList();
        assertEquals(4, chunkDataArrayListUpdated.size());

        assertEquals(300, ((ArrayList<ChunkData>) territoryData.getChunkDataList()).get(3).getX());
        assertEquals(-300, ((ArrayList<ChunkData>) territoryData.getChunkDataList()).get(3).getZ());
    }
}