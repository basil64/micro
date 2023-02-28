package com.infinitum.controllers;

import java.util.UUID;

public class BookStoreErrorMessage {
    private final UUID uuid;
    private final String message;

    public BookStoreErrorMessage(UUID uuid, String message) {
        this.uuid = uuid;
        this.message = message;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getMessage() {
        return message;
    }
}
