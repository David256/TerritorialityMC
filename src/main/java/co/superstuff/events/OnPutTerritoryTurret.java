package co.superstuff.events;

import co.superstuff.Territoriality;
import co.superstuff.TerritorialityMCPlugin;
import co.superstuff.classes.Territory;
import co.superstuff.classes.TurretPosition;
import co.superstuff.items.TerritoryTurretItem;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.Rotatable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Set;
import java.util.logging.Logger;

public class OnPutTerritoryTurret implements Listener {

    private final ArrayList<TurretPosition> turretPositions;

    public OnPutTerritoryTurret() {
        turretPositions = new ArrayList<>();
    }

    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent event) {
        Logger logger = TerritorialityMCPlugin.getInstance().getLogger();

        Player player = event.getPlayer();

        ItemStack itemInHand = player.getInventory().getItemInMainHand();

        if (TerritoryTurretItem.check(itemInHand)) {
            @Nullable String territoryId = TerritoryTurretItem.getTerritoryId(itemInHand);
            if (territoryId == null) {
                player.sendMessage(ChatColor.GOLD + "item invalid");
                event.setCancelled(true);
                return;
            }

            Territory territory = Territoriality.findTerritoryById(territoryId);
            if (territory == null) {
                player.sendMessage("Cannot find the territory if this blueprints");
                event.setCancelled(true);
                return;
            }

            logger.info("Put the territory turret here!");
            event.setCancelled(true);

            Block block = player.getTargetBlock((Set<Material>) null, 5);

            World world = player.getWorld();

            // Maybe the block is the ground, then we have to go up until we find a non-air block
            if (!block.getType().isAir() && block.getType().isSolid()) {
                Block overBlock = world.getBlockAt(block.getX(), block.getY() + 1, block.getZ());

                if (overBlock.getType().isAir() || !overBlock.getType().isSolid()) {
                    block = overBlock;
                } else {
                    player.sendMessage("Air block not found");
                    return;
                }
            }

            // Check if the block under is the ground
            if (!world.getBlockAt(block.getX(), block.getY() - 1, block.getZ()).getType().equals(Material.GRASS_BLOCK)) {
                System.out.println("It is not the ground");
                return;
            }

            boolean isValidedGround = true;
            for (int i = 0; i < 10; i++) {
                Block offsetBlock = world.getBlockAt(block.getX(), block.getY() + i, block.getZ());
                if (!offsetBlock.getType().isAir() /*&& block.getType().isSolid()*/) {
                    isValidedGround = false;
                    break;
                }
            }

            if (!isValidedGround) {
                player.sendMessage("It is a invalid ground");
                return;
            }

            TurretPosition turretPosition = new TurretPosition(block.getX(), block.getZ());
            if (turretPositions.contains(turretPosition)) {
                System.out.println("Stop twice calling");
                return;
            }

            world.getBlockAt(block.getX(), block.getY(), block.getZ()).setType(Material.STONE_BRICKS);
            world.getBlockAt(block.getX(), block.getY()+1, block.getZ()).setType(Material.STONE_BRICK_WALL);
//                world.getBlockAt(block.getX(), block.getY()+2, block.getZ()).setType(Material.WHITE_BANNER);
            Block bannerBlock = world.getBlockAt(block.getX(), block.getY()+2, block.getZ());
            bannerBlock.setType(Material.WHITE_BANNER);

            bannerBlock.setBlockData(getBannerRotation(bannerBlock, player));

            putLightingRods(block, world);

                /*
                Put the rod
                */
            turretPositions.add(turretPosition);

            player.sendMessage("Place the turret for: " + territory.getName());
        }
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

    private static Rotatable getBannerRotation(Block bannerBlock, Player player) {
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
