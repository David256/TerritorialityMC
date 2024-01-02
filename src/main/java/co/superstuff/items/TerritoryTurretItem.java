package co.superstuff.items;

import co.superstuff.TerritorialityMCPlugin;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import javax.annotation.Nullable;
import java.util.Arrays;


public class TerritoryTurretItem {
    public static ItemStack create(@Nullable String territoryName) {

        ItemStack itemStack = new ItemStack(Material.MAP, 1);

        // Put any enchantment
        itemStack.addUnsafeEnchantment(Enchantment.DURABILITY, 1);

        // Avoid stackable (I think)
        itemStack.setAmount(1);

        // Set the display name and lore
        ItemMeta itemMeta = itemStack.getItemMeta();

        if (itemMeta != null) {
            String displayName = "Territory turret";

            if (territoryName != null) {
                displayName += ": " + territoryName;
            }

            itemMeta.setDisplayName(displayName);

            itemMeta.setLore(Arrays.asList("Generator of territory", "Use over the ground to put a territory turret"));

            // Save this persistent data to identify the item in the using moment
            PersistentDataContainer container = itemMeta.getPersistentDataContainer();
            container.set(
                    new NamespacedKey(
                            TerritorialityMCPlugin.getInstance(),
                            TerritorialityMCPlugin.TERRITORY_TURRET_GENERATOR_ID
                    ),
                    PersistentDataType.BOOLEAN,
                    true);

            // Set the item metadata
            itemStack.setItemMeta(itemMeta);
        } else {
            System.err.println("WTF item meta is null");
        }

        return itemStack;
    }

    public static boolean check(ItemStack item) {
        if (item.getType() == Material.AIR) {
            return false;
        }

        ItemMeta meta = item.getItemMeta();

        if (meta == null) {
            return false;
        }

        PersistentDataContainer container = meta.getPersistentDataContainer();

        NamespacedKey namespacedKey = new NamespacedKey(TerritorialityMCPlugin.getInstance(), TerritorialityMCPlugin.TERRITORY_TURRET_GENERATOR_ID);
        return Boolean.TRUE.equals(container.get(namespacedKey, PersistentDataType.BOOLEAN));
    }
}
