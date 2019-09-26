package com.tavisca.inmemorycache.model;
import java.util.UUID;

public class UUIDGenerator {

    private String uuid;

    public String getUuid() {
    	uuid = UUID.randomUUID().toString();
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
