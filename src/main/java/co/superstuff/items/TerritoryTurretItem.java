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
    public static ItemStack create(String territoryName, String territoryId) {

        ItemStack itemStack = new ItemStack(Material.MAP, 1);

        // Put any enchantment
        itemStack.addUnsafeEnchantment(Enchantment.DURABILITY, 1);

        // Avoid stackable (I think)
        itemStack.setAmount(1);

        // Set the display name and lore
        ItemMeta itemMeta = itemStack.getItemMeta();

        if (itemMeta != null) {
            itemMeta.setDisplayName("Blueprint of territorial turret: %s".formatted(territoryName));

            itemMeta.setLore(Arrays.asList("Blueprint of territorial turret", "Use over the ground to put a territorial turret"));

            // Save this persistent data to identify the item in the using moment
            PersistentDataContainer container = itemMeta.getPersistentDataContainer();
            container.set(
                    new NamespacedKey(
                            TerritorialityMCPlugin.getInstance(),
                            TerritorialityMCPlugin.TERRITORY_TURRET_GENERATOR_ID
                    ),
                    PersistentDataType.BOOLEAN,
                    true);

            container.set(
                    new NamespacedKey(
                            TerritorialityMCPlugin.getInstance(),
                            "id"
                    ),
                    PersistentDataType.STRING,
                    territoryId
            );

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

    public static @Nullable String getTerritoryId(ItemStack item) {
        ItemMeta meta = item.getItemMeta();

        if (meta == null) {
            return null;
        }

        PersistentDataContainer container = meta.getPersistentDataContainer();

        NamespacedKey namespacedKey = new NamespacedKey(TerritorialityMCPlugin.getInstance(), "id");
        String gotId = container.get(namespacedKey, PersistentDataType.STRING);
        if (gotId == null || gotId.isEmpty()) {
            return null;
        }
        return gotId;
    }
}
