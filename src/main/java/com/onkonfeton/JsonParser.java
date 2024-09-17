package com.onkonfeton;

import com.onkonfeton.hierarchy.JsonArray;
import com.onkonfeton.hierarchy.JsonElement;
import com.onkonfeton.hierarchy.JsonNull;
import com.onkonfeton.hierarchy.JsonObject;
import com.onkonfeton.hierarchy.JsonPrimitive;
import lombok.SneakyThrows;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class JsonParser {


    public <T> T parse(String json, Class<T> classOf) throws IOException {
        JSONParser jsonParser = new JSONParser();
        Map<String, JsonElement> parse = jsonParser.parse(json);
        System.out.println(parse);

        return null;
    }



    class JSONParser {

        public static final char OBJECT_START = '{';
        private static final char OBJECT_END = '}';
        private static final char ARRAY_START = '[';
        private static final char ARRAY_END = ']';
        private static final char QUOTE = '"';
        private static final char COMMA = ',';
        private static final char COLON = ':';

        private String json;
        private int index = 0;

        @SneakyThrows
        public Map<String, JsonElement> parse(String json) {
            this.json = json;
            JsonElement jsonElement = parseValue();
            JsonObject obj = (JsonObject) jsonElement;
            Map<String, JsonElement> value = obj.getValue();
            return value;
        }


        public JsonElement parseValue() throws IOException {
            skipWhitespaces();
            char currentChar = peek();
            if (currentChar == OBJECT_START) {
                return parseObject();
            } else if (currentChar == ARRAY_START) {
                return parseArray();
            } else if (currentChar == QUOTE) {
                return parseString();
            } else if (Character.isDigit(currentChar) || currentChar == '-') {
                return parseNumber();
            } else if (json.startsWith("true", index)) {
                index += 4;
                return new JsonPrimitive(true);
            } else if (json.startsWith("false", index)) {
                index += 5;
                return new JsonPrimitive(false);
            } else if (json.startsWith("null", index)) {
                index += 4;
                return new JsonNull();
            } else {
                throw new IOException("Unexpected character: " + currentChar);
            }
        }

        private JsonArray parseArray() throws IOException {
            List<JsonElement> array = new ArrayList<>();
            index++; // Skip '['
            skipWhitespaces();
            while (peek() != ARRAY_END) {
                JsonElement value = parseValue();
                array.add(value);
                skipWhitespaces();
                if (peek() == COMMA) {
                    index++; // Skip ','
                    skipWhitespaces();
                } else if (peek() != ARRAY_END) {
                    throw new IOException("Expected comma or closing bracket");
                }
            }
            index++; // Skip ']'
            return new JsonArray(array);
        }

        private JsonPrimitive parseNumber() {
            StringBuilder sb = new StringBuilder();
            while (Character.isDigit(peek()) || peek() == '-' || peek() == '.') {
                sb.append(peek());
                index++;
            }
            String numberString = sb.toString();
            if (numberString.contains(".")) {
                return new JsonPrimitive(Double.parseDouble(numberString));
            } else {
                return new JsonPrimitive(Integer.parseInt(numberString));
            }
        }

        private JsonObject parseObject() throws IOException {
            Map<String, JsonElement> map = new LinkedHashMap<>();
            index++; // Skip '{'
            skipWhitespaces();
            while (peek() != OBJECT_END) {
                JsonPrimitive key = parseString();
                skipWhitespaces();
                if (peek() != COLON) {
                    throw new RuntimeException("Expected colon after key");
                }
                index++; // Skip ':'
                skipWhitespaces();
                JsonElement value = parseValue();
                map.put(key.getAsString(), value);
                skipWhitespaces();
                if (peek() == COMMA) {
                    index++; // Skip ','
                    skipWhitespaces();
                } else if (peek() != OBJECT_END) {
                    throw new RuntimeException("Expected comma or closing brace");
                }
            }
            index++; // Skip '}'
            return new JsonObject(map);
        }

        private JsonPrimitive parseString() {
            index++; // Skip '"'
            StringBuilder sb = new StringBuilder();
            while (peek() != '"') {
                sb.append(peek());
                index++;
            }
            index++; // Skip '"'
            return new JsonPrimitive(sb.toString());

        }

        private void skipWhitespaces() {
            while (json.charAt(index) == ' ') {
                index++;
            }
        }

        private char peek() {
            return this.json.charAt(index);
        }
    }
}
