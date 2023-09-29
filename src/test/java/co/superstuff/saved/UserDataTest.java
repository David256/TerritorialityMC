package co.superstuff.saved;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserDataTest {
    private UserData userData;

    @BeforeEach
    void setUp() {
        userData = new UserData("user-100", "username-1", "territory-1");
    }

    @DisplayName("Get the user ID")
    @Test
    void getId() {
        assertEquals("user-100", userData.getId());
    }

    @DisplayName("Set the user ID")
    @Test
    void setId() {
        userData.setId("user-000");
        assertEquals("user-000", userData.getId());
    }

    @DisplayName("Get the username")
    @Test
    void getUsername() {
        assertEquals("username-1", userData.getUsername());
    }

    @DisplayName("Set the username")
    @Test
    void setUsername() {
        userData.setUsername("username-0");
        assertEquals("username-0", userData.getUsername());
    }

    @DisplayName("Get the territory Id")
    @Test
    void getTerritoryId() {
        assertEquals("territory-1", userData.getTerritoryId());
    }

    @DisplayName("Set the territory Id")
    @Test
    void setTerritoryId() {
        userData.setTerritoryId("territory-2");
        assertEquals("territory-2", userData.getTerritoryId());
    }
}