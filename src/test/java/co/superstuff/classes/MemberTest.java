package co.superstuff.classes;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class MemberTest {

    @Test
    void dumpAsMap() {
        Member member = new Member("user-1", "username", "id-1000");

        Map<String, Object> map = member.dumpAsMap();

        assertEquals("user-1", map.get("id"));
        assertEquals("username", map.get("name"));
        assertEquals("id-1000", map.get("territoryId"));
    }

    @Test
    void fromMap() {

        Map<String, Object> map = new HashMap<>();

        map.put("id", "id-2");
        map.put("name", "user-name");
        map.put("territoryId", "id-2000");

        Member member = Member.fromMap(map);

        assertNotNull(member);
        assertEquals("id-2", member.getId());
        assertEquals("user-name", member.getName());
        assertEquals("id-2000", member.getTerritoryId());
    }
}