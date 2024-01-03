package co.superstuff.classes;

import co.superstuff.TerritorialityMCPlugin;
import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.logging.Logger;

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

    /**
     * Create the main plot and add neighbor {@link Chunk} objects.
     * The provided coordinates are when the territorial turret will be placed.
     * @param location The location coordinate.
     */
    public MainPlot createMainPlot(Location location) {
        int x = location.getChunk().getX();
        int z = location.getChunk().getZ();

        List<Chunk> chunks = getChunkAround(x, z);

        mainPlot = new MainPlot(x, z, 100, id, chunks);;

        return mainPlot;
    }

    public ExtensionPlot createExternalPlot(Location location) {
        int x = location.getChunk().getX();
        int z = location.getChunk().getZ();

        List<Chunk> chunks = getChunkAround(x, z);

        ExtensionPlot extensionPlot = new ExtensionPlot(x, z, 100, id, chunks);;

        extensionPlots.add(extensionPlot);

        return extensionPlot;
    }

    private List<Chunk> getChunkAround(int x, int z) {
        final int offset = 1;
        List<Chunk> chunks = new ArrayList<>();
        for (int i = -offset; i <= offset; i++) {
            for (int j = -offset; j <= offset; j++) {
                chunks.add(new Chunk(x + i, z + j));
            }

        }
        return chunks;
    }

    public boolean placeTurret(Location location) {
        Logger logger = TerritorialityMCPlugin.getInstance().getLogger();

        if (!checkChunkFree(location)) {
            logger.warning("Location at %s is busy".formatted(location));
            return false;
        }

        Chunk chunk = new Chunk(location.getChunk().getX(), location.getChunk().getZ());

        if (mainPlot == null) {
            logger.warning("main plot is null - will create it");
            // no main plot created yet
            // Place the turret
            TerritorialTurret turret = new TerritorialTurret(location);
            boolean isPlaced = turret.place(null);
            if (!isPlaced) {
                logger.warning("Cannot place the territorial turret at: " + location);
                return false;
            }

            mainPlot = createMainPlot(location);
            mainPlot.setTurret(turret);
            logger.info("main plot created - turret set");
        } else {
            if (!mainPlot.contains(chunk)) {
                logger.info("main plot does not have the chunk %s".formatted(chunk));
                // Place the turret
                TerritorialTurret turret = new TerritorialTurret(location);
                boolean isPlaced = turret.place(null);
                if (!isPlaced) {
                    logger.warning("Cannot place the territorial turret at: " + location);
                    return false;
                }
                return true;
            } else {
                // Check for external plots
                if (extensionPlots.stream().anyMatch(plot -> plot.chunks.contains(chunk))) {
                    logger.warning("MainPlot does not have this chunk - but yes a external plot");
                    return false;
                }

                logger.info("will add an external plot");

                TerritorialTurret turret = new TerritorialTurret(location);
                boolean isPlaced = turret.place(null);
                if (!isPlaced) {
                    logger.warning("Cannot place the territorial turret at: " + location);
                    return false;
                }

                ExtensionPlot extensionPlot = createExternalPlot(location);
                extensionPlot.setTurret(turret);

                logger.info("new external plot added: %s".formatted(extensionPlot));

            }
        }

        return false;
    }

    /**
     * Check if the chunk at that location is free.
     * @param location The location where the chunk could be placed.
     * @return true if the chunk is free.
     */
    private boolean checkChunkFree(Location location) {
        Chunk currentChunk = new Chunk(location.getChunk().getX(), location.getChunk().getZ());

        if (mainPlot != null) {
            if (!mainPlot.chunks.contains(currentChunk)) {
                return true;
            }
        }

        if (!extensionPlots.isEmpty()) {
            if (extensionPlots.stream().noneMatch(plot -> plot.getChunks().contains(currentChunk))) {
                return false;
            }
        }

        return true;
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
