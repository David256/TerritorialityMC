package co.superstuff.saved;

public class ChunkData {
    static short MAX_LIFE = 10;

    private int x;
    private int z;
    private String territoryId;
    private short life;

    public ChunkData(int x, int z, String territoryId, short life) {
        this.x = x;
        this.z = z;
        this.territoryId = territoryId;
        this.life = life;
    }

    public ChunkData(int x, int z, String territoryId) {
        this.x = x;
        this.z = z;
        this.territoryId = territoryId;
        this.life = ChunkData.MAX_LIFE;
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

    public String getTerritoryId() {
        return territoryId;
    }

    public void setTerritoryId(String territoryId) {
        this.territoryId = territoryId;
    }

    public short getLife() {
        return life;
    }

    public void setLife(short life) {
        this.life = life;
    }
}
