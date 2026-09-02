package lol.pbu.z4j.model;

/**
 * <h1>{@summary User Sideloads}</h1>
 * <p>Entities that can be sideloaded with user responses.</p>
 */
public enum UserSideload {
    ABILITIES("abilities"),
    ROLES("roles"),
    IDENTITIES("identities"),
    ORGANIZATIONS("organizations"),
    GROUPS("groups");

    private final String value;

    UserSideload(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}
