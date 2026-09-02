package lol.pbu.z4j.model;

/**
 * <h1>{@summary Ticket Sideloads}</h1>
 * <p>Entities that can be sideloaded with ticket responses.</p>
 */
public enum TicketSideload {
    USERS("users"),
    GROUPS("groups"),
    ORGANIZATIONS("organizations"),
    LAST_AUDITS("last_audits"),
    METRIC_SETS("metric_sets"),
    DATES("dates"),
    SHARING_AGREEMENTS("sharing_agreements"),
    INCIDENTS("incidents");

    private final String value;

    TicketSideload(String value) {
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
