package com.infinitum.exceptions;

import java.util.UUID;

public class AuthorNotFoundException extends RuntimeException {
    private final UUID uuid;
    public AuthorNotFoundException(UUID uuid) {
        super();
        this.uuid = uuid;
    }

    public UUID getUuid() {
        return uuid;
    }
}
