package co.superstuff.classes;

import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class Member implements Mappable, Writable {
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

        member.write(persistent);

        return member;
    }

    public static Member create(Player player, Territory territory) {

        return new Member(
                player.getUniqueId().toString(),
                player.getName(),
                territory.getId()
        );
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
    public void write(PersistentManager persistent) {
        Map<String, Object> map = dumpAsMap();
        persistent.set(id, map);
        persistent.reload();
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
    public static Member fromMap(Map<?, ?> map) {
        if (map == null) {
            System.err.println("Member.fromMap receives null");
            return null;
        }

        String id = (String) map.get("id");
        String name = (String) map.get("name");
        String territoryId = (String) map.get("territoryId");

        return new Member(id, name, territoryId);

    }
}
