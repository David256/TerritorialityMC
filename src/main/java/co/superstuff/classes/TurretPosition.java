package co.superstuff.classes;

public class TurretPosition {
    private int x;
    private int z;

    public TurretPosition(int x, int y) {
        this.x = x;
        this.z = y;
    }

    @Override
    public String toString() {
        return "TurretPosition{" +
                "x=" + x +
                ", z=" + z +
                '}';
    }

    private boolean equals(TurretPosition turretPosition) {
        return turretPosition.x == x && turretPosition.z == z;
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
}
