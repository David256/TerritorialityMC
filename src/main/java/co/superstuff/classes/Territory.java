package co.superstuff.classes;

import org.bukkit.configuration.MemorySection;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.*;

public class Territory implements Mappable, Writable {
    private String id;
    private String name;
    private String ownerId;
    private MainPlot mainPlot;
    private List<ExtensionPlot> extensionPlots;
    private List<Member> members;

    public Territory(String name, String ownerId) {
        this(
                name,
                ownerId,
                null,
                new ArrayList<>(),
                new ArrayList<>()
        );
    }

    public Territory(String name, String ownerId, MainPlot mainPlot, List<ExtensionPlot> extensionPlots, List<Member> members) {
        this(
                UUID.randomUUID().toString(),
                name,
                ownerId,
                mainPlot,
                extensionPlots,
                members
        );
    }

    public Territory(String id, String name, String ownerId, MainPlot mainPlot, List<ExtensionPlot> extensionPlots, List<Member> members) {
        this.id = id;
        this.name = name;
        this.ownerId = ownerId;
        this.mainPlot = mainPlot;
        this.extensionPlots = extensionPlots;
        this.members = members;
    }

    public static Territory create(PersistentManager persistent, String name, Player player) {
        Territory territory = new Territory(name, player.getUniqueId().toString());
        territory.write(persistent);
        return territory;
    }

    public static Territory create(String name, Player player) {
        return new Territory(name, player.getUniqueId().toString());
    }

    @Override
    public void write(PersistentManager persistent) {
        Map<String, Object> map = dumpAsMap();
        persistent.set(id, map);
        persistent.reload();
    }

    @Nullable
    public static Territory findByOwnerId(PersistentManager persistent, String ownerId) {
        Set<String> set = persistent.getConfig().getKeys(false);

        for (String territoryId : set) {
            String foundOwnerId = persistent.getConfig().getString(territoryId + ".ownerId");

            if (foundOwnerId != null && foundOwnerId.equals(ownerId)) {
                MemorySection memorySection = (MemorySection) persistent.getConfig().get(territoryId);
                if (memorySection == null) {
                    System.err.println("caught territory for id: " + territoryId + " was null");
                    System.err.println("new finding returned null whiling a MemorySection was founded");
                    return null;
                }
                Map<String, Object> maybeTerritory = memorySection.getValues(true);
                return Territory.fromMap(maybeTerritory);
            }
        }

        return null;
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

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public MainPlot getMainPlot() {
        return mainPlot;
    }

    public void setMainPlot(MainPlot mainPlot) {
        this.mainPlot = mainPlot;
    }

    public List<ExtensionPlot> getExtensionPlots() {
        return extensionPlots;
    }

    public void setExtensionPlots(List<ExtensionPlot> extensionPlots) {
        this.extensionPlots = extensionPlots;
    }

    public List<Member> getUserList() {
        return members;
    }

    public void setUserList(List<Member> members) {
        this.members = members;
    }

    @Override
    public Map<String, Object> dumpAsMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("name", name);
        map.put("ownerId", ownerId);

        map.put("mainPlot", mainPlot == null ? null : mainPlot.dumpAsMap());

        List<Map<String, Object>> extensionPlotMapList = new ArrayList<>();

        for (ExtensionPlot extensionPlot : extensionPlots) {
            extensionPlotMapList.add(extensionPlot.dumpAsMap());
        }

        map.put("extensionPlots", extensionPlotMapList);

        return map;
    }

    @Nullable
    static public Territory fromMap(Map<?, ?> map) {
        if (map == null) {
            System.err.println("Territory.fromMap receives null");
            return null;
        }

        String id = (String) map.get("id");
        String name = (String) map.get("name");
        String ownerId = (String) map.get("ownerId");

        MainPlot mainPlot = MainPlot.fromMap((Map<?, ?>) map.get("mainPlot"));

        Object extensionPlotsObject = map.get("extensionPlots");
        ArrayList<ExtensionPlot> extensionPlots = new ArrayList<>();
        if (extensionPlotsObject instanceof ArrayList<?> extensionPlotArrayList) {
            for (Object item : extensionPlotArrayList) {
                if (item instanceof Map<?, ?> itemMap) {
                    extensionPlots.add(ExtensionPlot.fromMap(itemMap));
                }
            }
        }

        ArrayList<Member> members = new ArrayList<>();
        Object membersObj = map.get("members");
        if (membersObj instanceof ArrayList<?> membersList) {
            for (Object item: membersList) {
                if (item instanceof Map<?, ?> itemMap) {
                    members.add(Member.fromMap(itemMap));
                }
            }
        }

        return new Territory(id, name, ownerId, mainPlot, extensionPlots, members);
    }
}
