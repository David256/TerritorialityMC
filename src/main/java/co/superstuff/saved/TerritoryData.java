package co.superstuff.saved;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TerritoryData {
    private String id;
    private String name;
    private String ownerId;
    private List<ChunkData> chunkDataList;

    public TerritoryData(String id, String name, String ownerId, List<ChunkData> chunkDataList) {
        this.id = id;
        this.name = name;
        this.ownerId = ownerId;
        this.chunkDataList = chunkDataList;
    }

    public TerritoryData(String id, String name, String ownerId) {
        this.id = id;
        this.name = name;
        this.ownerId = ownerId;
        this.chunkDataList = new ArrayList<ChunkData>();
    }

    public TerritoryData(String name, String ownerId, List<ChunkData> chunkDataList) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.ownerId = ownerId;
        this.chunkDataList = chunkDataList;
    }

    public TerritoryData(String name, String ownerId) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.ownerId = ownerId;
        this.chunkDataList = new ArrayList<ChunkData>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public List<ChunkData> getChunkDataList() {
        return chunkDataList;
    }

    public void setChunkDataList(List<ChunkData> chunkDataList) {
        this.chunkDataList = chunkDataList;
    }
}
