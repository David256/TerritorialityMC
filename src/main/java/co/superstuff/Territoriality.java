package co.superstuff;

import co.superstuff.classes.Member;
import co.superstuff.classes.Territory;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class Territoriality {
    private static final List<Territory> territories = new ArrayList<>();
    private static final List<Member> members = new ArrayList<>();

    public static List<Territory> getTerritories() {
        return territories;
    }

    public static List<Member> getMembers() {
        return members;
    }

    public static @Nullable Territory findTerritoryByOwnerId(String ownerId) {
        return territories
                .stream()
                .filter(territory -> territory.getOwnerId().equals(ownerId))
                .findFirst()
                .orElse(null);
    }

    public static @Nullable Territory findTerritoryByOwner(Player player) {
        return findTerritoryByOwnerId(player.getUniqueId().toString());
    }

    public static @Nullable Member findMemberById(String id) {
        return members
                .stream()
                .filter(member -> member.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public static @Nullable Member findMemberByPlayer(Player player) {
        return findMemberById(player.getUniqueId().toString());
    }

    public static List<Member> getAllMembersByTerritoryId(String territoryId) {
        return members
                .stream()
                .filter(member -> member.getTerritoryId().equals(territoryId))
                .toList();
    }

    public static List<Member> getAllMembersByTerritory(Territory territory) {
        return getAllMembersByTerritoryId(territory.getId());
    }

    public static @Nullable Territory createTerritory(Player player, String name) {
        String ownerId = player.getUniqueId().toString();
        Territory existentTerritory = findTerritoryByOwnerId(ownerId);

        if (existentTerritory == null) {
            Territory territory = new Territory(name, player);

            Member member = new Member(player, territory);

            territories.add(territory);
            members.add(member);

            TerritorialityMCPlugin.getInstance().saveTerritories();
            TerritorialityMCPlugin.getInstance().saveMembers();

            return territory;
        } else {
            player.sendMessage("You are register as member of the territory: " + existentTerritory.getName());
            if (existentTerritory.isPlayerOwner(player)) {
                player.sendMessage("And you are the owner");
            }
        }

        return null;
    }

    public static boolean deleteTerritoryById(String id) {
        boolean deleted = territories.removeIf(territory -> territory.getId().equals(id));
        members.removeIf(member -> member.getTerritoryId().equals(id));
        TerritorialityMCPlugin.getInstance().saveTerritories();
        TerritorialityMCPlugin.getInstance().saveMembers();
        return deleted;
    }

    public static boolean deleteTerritoryByOwner(Player player) {
        return deleteTerritoryByOwnerId(player.getUniqueId().toString());
    }

    public static boolean deleteTerritoryByOwnerId(String ownerId) {
        Territory territory = findTerritoryByOwnerId(ownerId);
        if (territory != null) {
            return deleteTerritoryById(territory.getId());
        }
        return false;
    }

    public static boolean deleteMemberByOwner(Player player) {
        boolean removed = members.removeIf(member -> member.getId().equals(player.getUniqueId().toString()));
        if (removed) {
            TerritorialityMCPlugin.getInstance().saveMembers();
        }
        return removed;
    }
}
