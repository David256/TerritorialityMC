package co.superstuff.saved;

public class ChunkData {
    static short MAX_LIFE = 10;

    private int x;
    private int z;
    private String territorialityId;
    private short life;

    public ChunkData(int x, int z, String territorialityId, short life) {
        this.x = x;
        this.z = z;
        this.territorialityId = territorialityId;
        this.life = life;
    }

    public ChunkData(int x, int z, String territorialityId) {
        this.x = x;
        this.z = z;
        this.territorialityId = territorialityId;
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

    public String getTerritorialityId() {
        return territorialityId;
    }

    public void setTerritorialityId(String territorialityId) {
        this.territorialityId = territorialityId;
    }

    public short getLife() {
        return life;
    }

    public void setLife(short life) {
        this.life = life;
    }
}
