package com.onkonfeton;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class JsonParser {


    public <T>Person parse(String json, Class<T> classOf) throws IOException {
        JSONParser jsonParser = new JSONParser(json);
        jsonParser.parseValue();


    }



    class JSONParser {


        private String json;

        private int index = 0;

        public JSONParser(String json) {
            this.json = json;
        }


        private Object parseValue() {
            skipWhitespaces();
            if (peek() == '{'){
                return parseObject();
            } else if (peek() == '"') {
                parseString();
            }

        }

        private Object parseObject() {
            Map<String, Object> map = new LinkedHashMap<>();
            skipWhitespaces();
            String key = parseString();
            skipWhitespaces(); //to ':'
            index++; // skip ':'
            skipWhitespaces();
            Object value = parseValue();
            return map;
        }

        private String parseString() {
            index++; // Skip '"'
            StringBuilder sb = new StringBuilder();
            while (peek() != '"') {
                sb.append(peek());
                index++;
            }
            index++; // Skip '"'
            return sb.toString();
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
