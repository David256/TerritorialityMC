package co.superstuff.classes;

import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class Member implements Mappable {
    private String id;
    private String name;
    private String territoryId;

    public Member(String id, String name, String territoryId) {
        this.id = id;
        this.name = name;
        this.territoryId = territoryId;
    }

    public static Member create(PersistentManager persistent, Player player, Territory territory) {
        Member member = new Member(
                player.getUniqueId().toString(),
                player.getName(),
                territory.getId()
        );

        Map<String, Object> map = member.dumpAsMap();

        persistent.set(member.getId(), map);
        persistent.save();
        persistent.reload();

        return member;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTerritoryId() {
        return territoryId;
    }

    public void setTerritoryId(String territoryId) {
        this.territoryId = territoryId;
    }

    @Override
    public Map<String, Object> dumpAsMap() {
        Map<String, Object> map = new HashMap<>();

        map.put("id", id);
        map.put("name", name);
        map.put("territoryId", territoryId);

        return map;
    }

    @Nullable
    static Member fromMap(Map<?, ?> map) {
        if (map == null) {
            return null;
        }

        String id = (String) map.get("id");
        String name = (String) map.get("name");
        String territoryId = (String) map.get("territoryId");

        return new Member(id, name, territoryId);

    }
}
