package co.superstuff.classes;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

@SerializableAs("Territory")
public class Territory implements ConfigurationSerializable {
    private String id;
    private String name;
    private String ownerId;
    private @Nullable MainPlot mainPlot;
    private List<ExtensionPlot> extensionPlots;
    private List<Member> members;

    public Territory(String name, String ownerId) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.ownerId = ownerId;
        this.mainPlot = null;
        this.extensionPlots = new ArrayList<>();
        this.members = new ArrayList<>();
    }

    public Territory(String name, String ownerId, MainPlot mainPlot, List<ExtensionPlot> extensionPlots, List<Member> members) {
        this(name, ownerId);
        this.mainPlot = mainPlot;
        this.extensionPlots = extensionPlots;
        this.members = members;
    }

    public Territory(String id, String name, String ownerId, MainPlot mainPlot, List<ExtensionPlot> extensionPlots, List<Member> members) {
        this(name, ownerId, mainPlot, extensionPlots, members);
        this.id = id;
    }

    public Territory(String name, Player player) {
        this(name, player.getUniqueId().toString());
    }

    public Territory(Map<String, Object> map) {
        id = (String) map.get("id");
        name = (String) map.get("name");
        ownerId = (String) map.get("ownerId");
        mainPlot = (MainPlot) map.get("mainPlot");

        extensionPlots = new ArrayList<>();
        List<?> extensionPlotList = (List<?>) map.get("extensionPlots");
        if (extensionPlotList != null) {
            extensionPlotList.forEach(raw -> {
                if (raw instanceof ExtensionPlot extensionPlot) {
                    extensionPlots.add(extensionPlot);
                }
            });
        }

        members = new ArrayList<>();
        List<?> memberList = (List<?>) map.get("members");
        if (memberList != null) {
            memberList.forEach(raw -> {
                if (raw instanceof Member member) {
                    members.add(member);
                }
            });
        }

    }

    @Override
    public String toString() {
        return "Territory{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", ownerId='" + ownerId + '\'' +
                ", mainPlot=" + mainPlot +
                ", extensionPlots.size()=" + extensionPlots.size() +
                ", members.size()=" + members.size() +
                '}';
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getOwnerId() {
        return ownerId;
    }

    @Nullable
    public MainPlot getMainPlot() {
        return mainPlot;
    }

    public void setMainPlot(@Nullable MainPlot mainPlot) {
        this.mainPlot = mainPlot;
    }

    public boolean isPlayerOwner(Player player) {
        return player.getUniqueId().toString().equals(ownerId);
    }

    public void createMainPlot(int x, int z) {
        final int offset = 1;
        List<Chunk> chunks = new ArrayList<>();
        for (int i = -offset; i <= offset; i++) {
            for (int j = -offset; j <= offset; j++) {
                chunks.add(new Chunk(x + i, z + j));
            }

        }

        mainPlot = new MainPlot(x, z, 100, id, chunks);;
    }


    @Override
    @Nonnull
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("name", name);
        map.put("ownerId", ownerId);
        map.put("mainPlot", mainPlot);
        map.put("extensionPlots", extensionPlots);
        return map;
    }
}
