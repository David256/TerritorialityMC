package co.superstuff.saved;

public class UserData {
    private String id;
    private String username;
    private String territoryId;

    public UserData(String id, String username, String territoryId) {
        this.id = id;
        this.username = username;
        this.territoryId = territoryId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTerritoryId() {
        return territoryId;
    }

    public void setTerritoryId(String territoryId) {
        this.territoryId = territoryId;
    }
}
