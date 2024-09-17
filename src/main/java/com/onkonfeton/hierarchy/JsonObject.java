package com.onkonfeton.hierarchy;

import lombok.Getter;
import lombok.ToString;

import java.util.Map;
@Getter
@ToString
public class JsonObject extends JsonElement{
    private final Map<String, JsonElement> value;

    public JsonObject(Map<String, JsonElement> value) {
        this.value = value;
    }

}
