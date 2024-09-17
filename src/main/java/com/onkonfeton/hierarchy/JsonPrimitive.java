package com.onkonfeton.hierarchy;


import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
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


    public String getAsString(){
        if (value instanceof String){
            return (String) value;
        }
        else{
            throw new RuntimeException();
        }
    }



}
