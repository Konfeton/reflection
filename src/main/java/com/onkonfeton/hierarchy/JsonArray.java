package com.onkonfeton.hierarchy;

import java.util.List;

public class JsonArray extends JsonElement{
    private final List<JsonElement> value;

    public JsonArray(List<JsonElement> value) {
        this.value = value;
    }
}
