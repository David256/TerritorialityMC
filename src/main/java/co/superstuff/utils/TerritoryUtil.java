package co.superstuff.utils;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Rotatable;
import org.bukkit.entity.Player;

/**
 * TerritoryUtil class.
 */
public class TerritoryUtil {

    /**
     * According to block and player returns the rotatable value.
     * @param bannerBlock The banner block.
     * @param player The player.
     * @return {@link Rotatable}
     */
    public static Rotatable getBannerRotation(Block bannerBlock, Player player) {
        Rotatable rotatable = ((Rotatable) bannerBlock.getBlockData());
        Location location = player.getEyeLocation();

        double rotationDegree = (location.getYaw() - 90) % 360;
        if (rotationDegree < 0) {
            rotationDegree += 360.0;
        }
        System.out.println("rotationDegree: " + rotationDegree);
        if (rotationDegree >= 45 && rotationDegree < 135) {
            rotatable.setRotation(BlockFace.NORTH);

        } else if (rotationDegree >= 135 && rotationDegree < 225) {
            rotatable.setRotation(BlockFace.WEST);
        } else if (rotationDegree >= 225 && rotationDegree < 315) {
            rotatable.setRotation(BlockFace.EAST);
        } else {
            rotatable.setRotation(BlockFace.SOUTH);
        }

        return rotatable;
    }

}
