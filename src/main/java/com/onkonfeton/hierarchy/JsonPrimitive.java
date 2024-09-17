package com.onkonfeton.hierarchy;


import java.util.Objects;

public class JsonPrimitive extends JsonElement{
    private final Object value;

    public JsonPrimitive(Boolean bool) {
        this.value = bool;
    }

    public JsonPrimitive(Number number) {
        this.value = number;
    }

    public JsonPrimitive(String string) {
        this.value = string;
    }

    public JsonPrimitive(Character c) {
        this.value = c;
    }

}
