package co.superstuff.events;

import co.superstuff.Territoriality;
import co.superstuff.TerritorialityMCPlugin;

import co.superstuff.classes.Territory;
import co.superstuff.items.TerritoryTurretItem;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;
import java.util.Set;
import java.util.logging.Logger;

public class OnPutTerritoryTurret implements Listener {

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

            // bannerBlock.setBlockData(TerritoryUtil.getBannerRotation(bannerBlock, player));

            boolean success = territory.placeTurret(block.getLocation());
            if (success) {
                logger.info("set the main plot");
                TerritorialityMCPlugin.getInstance().saveTerritories();
            } else {
                player.sendMessage(ChatColor.RED + "Cannot place the turret here - maybe this chunk is registered yet");
                return;
            }

            player.sendMessage(ChatColor.AQUA + "Place the turret for: " + ChatColor.LIGHT_PURPLE + territory.getName());
        }
    }




}
