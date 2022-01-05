package advisor.core;

import java.io.Serializable;
import java.util.UUID;

public abstract class User implements Serializable {

    private final UUID uuid;

    protected User() {
        this.uuid = UUID.randomUUID();
    }

    public UUID getUuid() {
        return uuid;
    }

}
