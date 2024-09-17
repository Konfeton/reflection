package com.onkonfeton.hierarchy;

import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class JsonArray extends JsonElement{
    private final List<JsonElement> value;

    public JsonArray(List<JsonElement> value) {
        this.value = value;
    }
}
