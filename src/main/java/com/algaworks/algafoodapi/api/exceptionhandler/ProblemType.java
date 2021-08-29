package com.algaworks.algafoodapi.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {

    ENTITY_IN_USE("/entity-in-use", "Entity in use"),
    BUSINESS_EXCEPTION("/business-exception", "Business exception"),
    MESSAGE_NOT_READABLE("/message-not-readable", "Message not readable"),
    RESOURCE_NOT_FOUND("/resource-not-found", "Resource not found"),
    TYPE_MISMATCH("/type-mismatch", "Type mismatch"),
    INVALID_DATA("/invalid-data", "Invalid Data"),
    INTERNAL_SERVER_ERROR("/internal-server-error", "Internal server error");

    private String title;
    private String uri;

    ProblemType(String path, String title) {
        this.uri = String.format("https://algafood.com%s", path);
        this.title = title;
    }
}