package com.infinitum.exceptions;

import java.util.UUID;

public class BookNotFoundException extends RuntimeException {
    private final UUID uuid;

    public BookNotFoundException(UUID uuid) {
        super();
        this.uuid = uuid;
    }

    public UUID getUuid() {
        return uuid;
    }
}
