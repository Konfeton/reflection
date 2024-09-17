package com.onkonfeton.domain;
import java.util.*;
import java.io.*;

public class SimpleJsonParser {

    private static final char OBJECT_START = '{';
    private static final char OBJECT_END = '}';
    private static final char ARRAY_START = '[';
    private static final char ARRAY_END = ']';
    private static final char STRING_QUOTE = '"';
    private static final char COMMA = ',';
    private static final char COLON = ':';

    public static void main(String[] args) {
        String jsonString = "{\"name\": \"John\", \"age\": 30, \"isStudent\": false, \"courses\": [\"Math\", \"Science\"]}";

        try {
            Map<String, Object> json = (Map<String, Object>) parseJson(jsonString);
            System.out.println(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Object parseJson(String jsonString) throws IOException {
        JSONParser parser = new JSONParser(jsonString);
        return parser.parseValue();
    }

    static class JSONParser {
        private final String jsonString;
        private int index;

        public JSONParser(String jsonString) {
            this.jsonString = jsonString;
            this.index = 0;
        }

        public Object parseValue() throws IOException {
            skipWhitespace();
            char currentChar = peek();
            if (currentChar == OBJECT_START) {
                return parseObject();
            } else if (currentChar == ARRAY_START) {
                return parseArray();
            } else if (currentChar == STRING_QUOTE) {
                return parseString();
            } else if (Character.isDigit(currentChar) || currentChar == '-') {
                return parseNumber();
            } else if (jsonString.startsWith("true", index)) {
                index += 4;
                return true;
            } else if (jsonString.startsWith("false", index)) {
                index += 5;
                return false;
            } else if (jsonString.startsWith("null", index)) {
                index += 4;
                return null;
            } else {
                throw new IOException("Unexpected character: " + currentChar);
            }
        }

        private Map<String, Object> parseObject() throws IOException {
            Map<String, Object> object = new LinkedHashMap<>();
            index++; // Skip '{'
            skipWhitespace();
            while (peek() != OBJECT_END) {
                String key = parseString();
                skipWhitespace();
                if (peek() != COLON) {
                    throw new IOException("Expected colon after key");
                }
                index++; // Skip ':'
                skipWhitespace();
                Object value = parseValue();
                object.put(key, value);
                skipWhitespace();
                if (peek() == COMMA) {
                    index++; // Skip ','
                    skipWhitespace();
                } else if (peek() != OBJECT_END) {
                    throw new IOException("Expected comma or closing brace");
                }
            }
            index++; // Skip '}'
            return object;
        }

        private List<Object> parseArray() throws IOException {
            List<Object> array = new ArrayList<>();
            index++; // Skip '['
            skipWhitespace();
            while (peek() != ARRAY_END) {
                Object value = parseValue();
                array.add(value);
                skipWhitespace();
                if (peek() == COMMA) {
                    index++; // Skip ','
                    skipWhitespace();
                } else if (peek() != ARRAY_END) {
                    throw new IOException("Expected comma or closing bracket");
                }
            }
            index++; // Skip ']'
            return array;
        }

        private String parseString() throws IOException {
            index++; // Skip '"'
            StringBuilder sb = new StringBuilder();
            while (peek() != STRING_QUOTE) {
                sb.append(peek());
                index++;
            }
            index++; // Skip '"'
            return sb.toString();
        }

        private Number parseNumber() throws IOException {
            StringBuilder sb = new StringBuilder();
            while (Character.isDigit(peek()) || peek() == '-' || peek() == '.') {
                sb.append(peek());
                index++;
            }
            String numberString = sb.toString();
            if (numberString.contains(".")) {
                return Double.parseDouble(numberString);
            } else {
                return Integer.parseInt(numberString);
            }
        }

        private char peek() {
            return jsonString.charAt(index);
        }

        private void skipWhitespace() {
            while (index < jsonString.length() && Character.isWhitespace(peek())) {
                index++;
            }
        }
    }
}
