package com.onkonfeton.hierarchy;

import java.util.Map;

public class JsonObject extends JsonElement{
    private final Map<String, JsonElement> value;

    public JsonObject(Map<String, JsonElement> value) {
        this.value = value;
    }
}
