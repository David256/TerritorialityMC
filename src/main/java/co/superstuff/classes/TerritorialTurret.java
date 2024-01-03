package co.superstuff.classes;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.Rotatable;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents the turret blocks.
 */
@SerializableAs("Turret")
public class TerritorialTurret implements ConfigurationSerializable {
    private Location location;

    public TerritorialTurret(Location location) {
        this.location = location;
    }

    public TerritorialTurret(Map<String, Object> map) {
        location = (Location) map.get("location");
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * Only place a territorial turret replace at location.
     * @param rotatable The rotatable config.
     * @return true if the turret was placed.
     */
    public boolean place(@Nullable Rotatable rotatable) {
        World world = location.getWorld();
        Block block = location.getBlock();

        if (world == null) {
            System.err.println("placeTurret: world is null");
            return false;
        }

        // Put the base
        world.getBlockAt(block.getX(), block.getY(), block.getZ()).setType(Material.STONE_BRICKS);
        world.getBlockAt(block.getX(), block.getY()+1, block.getZ()).setType(Material.STONE_BRICK_WALL);

        // Put the banner
        Block bannerBlock = world.getBlockAt(block.getX(), block.getY()+2, block.getZ());
        bannerBlock.setType(Material.WHITE_BANNER);

        // Maybe a rotatable value exists
        if (rotatable != null) {
            bannerBlock.setBlockData(rotatable);
        }

        // Put the lighting rods
        putLightingRods(block, world);

        return true;
    }

    private void putLightingRods(Block block, World world) {
        Block rodBlock;
        Directional directional;

        // Put a lighting rod at east
        rodBlock = world.getBlockAt(block.getX()+1, block.getY(), block.getZ());
        rodBlock.setType(Material.LIGHTNING_ROD);
        directional = ((Directional) rodBlock.getBlockData());
        directional.setFacing(BlockFace.EAST);
        rodBlock.setBlockData(directional);
        // Put a lighting rod at west
        rodBlock = world.getBlockAt(block.getX()-1, block.getY(), block.getZ());
        rodBlock.setType(Material.LIGHTNING_ROD);
        directional = ((Directional) rodBlock.getBlockData());
        directional.setFacing(BlockFace.WEST);
        rodBlock.setBlockData(directional);
        // Put a lighting rod at south
        rodBlock = world.getBlockAt(block.getX(), block.getY(), block.getZ()+1);
        rodBlock.setType(Material.LIGHTNING_ROD);
        directional = ((Directional) rodBlock.getBlockData());
        directional.setFacing(BlockFace.SOUTH);
        rodBlock.setBlockData(directional);
        // Put a lighting rod at north
        rodBlock = world.getBlockAt(block.getX(), block.getY(), block.getZ()-1);
        rodBlock.setType(Material.LIGHTNING_ROD);
        directional = ((Directional) rodBlock.getBlockData());
        directional.setFacing(BlockFace.NORTH);
        rodBlock.setBlockData(directional);
    }

    @Override
    @Nonnull
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();

        map.put("location", location);

        return map;
    }
}
