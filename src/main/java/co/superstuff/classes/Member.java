package co.superstuff.classes;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a territorial member.
 */
@SerializableAs("Member")
public class Member implements ConfigurationSerializable {
    private String id;
    private String name;
    private String territoryId;

    public Member(String id, String name, String territoryId) {
        this.id = id;
        this.name = name;
        this.territoryId = territoryId;
    }

    public Member(Map<String, Object> map) {
        id = (String) map.get("id");
        name = (String) map.get("name");
        territoryId = (String) map.get("territoryId");
    }

    public Member(Player player, Territory territory) {
        id = player.getUniqueId().toString();
        name = player.getName();
        territoryId = territory.getId();
    }

    @Override
    public String toString() {
        return "Member{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", territoryId='" + territoryId + '\'' +
                '}';
    }

    /**
     * Check if a given player is the same of this member.
     * @param player A {@link Player}
     * @return true if the player is the same.
     */
    public boolean isPlayer(Player player) {
        return player.getUniqueId().toString().equals(id);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTerritoryId() {
        return territoryId;
    }

    @Override
    @Nonnull
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();

        map.put("id", id);
        map.put("name", name);
        map.put("territoryId", territoryId);

        return map;
    }
}
