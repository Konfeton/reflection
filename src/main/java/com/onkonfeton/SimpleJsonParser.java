package com.onkonfeton;

import java.util.*;
import java.io.*;

public class SimpleJsonParser {

    public static Object parse(String json) throws IOException {
        return parseValue(new StringReader(json));
    }

    private static Object parseValue(Reader reader) throws IOException {
        skipWhitespace(reader);
        int ch = reader.read();
        if (ch == '{') {
            return parseObject(reader);
        } else if (ch == '[') {
            return parseArray(reader);
        } else if (ch == '"') {
            return parseString(reader);
        } else if (ch == 't' || ch == 'f') {
            reader.unread(ch);
            return parseBoolean(reader);
        } else if (ch == 'n') {
            reader.unread(ch);
            return parseNull(reader);
        } else if (Character.isDigit(ch) || ch == '-') {
            reader.unread(ch);
            return parseNumber(reader);
        } else {
            throw new IOException("Unexpected character: " + (char) ch);
        }
    }

    private static Map<String, Object> parseObject(Reader reader) throws IOException {
        Map<String, Object> map = new HashMap<>();
        skipWhitespace(reader);
        int ch = reader.read();
        if (ch != '{') {
            throw new IOException("Expected '{' but found: " + (char) ch);
        }
        while (true) {
            skipWhitespace(reader);
            ch = reader.read();
            if (ch == '}') {
                break;
            } else {
                reader.unread(ch);
                String key = parseString(reader);
                skipWhitespace(reader);
                ch = reader.read();
                if (ch != ':') {
                    throw new IOException("Expected ':' but found: " + (char) ch);
                }
                Object value = parseValue(reader);
                map.put(key, value);
                skipWhitespace(reader);
                ch = reader.read();
                if (ch == '}') {
                    break;
                } else if (ch != ',') {
                    throw new IOException("Expected ',' or '}' but found: " + (char) ch);
                }
            }
        }
        return map;
    }

    private static List<Object> parseArray(Reader reader) throws IOException {
        List<Object> list = new ArrayList<>();
        skipWhitespace(reader);
        int ch = reader.read();
        if (ch != '[') {
            throw new IOException("Expected '[' but found: " + (char) ch);
        }
        while (true) {
            skipWhitespace(reader);
            ch = reader.read();
            if (ch == ']') {
                break;
            } else {
                reader.unread(ch);
                Object value = parseValue(reader);
                list.add(value);
                skipWhitespace(reader);
                ch = reader.read();
                if (ch == ']') {
                    break;
                } else if (ch != ',') {
                    throw new IOException("Expected ',' or ']' but found: " + (char) ch);
                }
            }
        }
        return list;
    }

    private static String parseString(Reader reader) throws IOException {
        StringBuilder sb = new StringBuilder();
        int ch = reader.read();
        if (ch != '"') {
            throw new IOException("Expected '\"' but found: " + (char) ch);
        }
        while ((ch = reader.read()) != '"') {
            if (ch == '\\') {
                ch = reader.read();
                switch (ch) {
                    case '"': sb.append('"'); break;
                    case '\\': sb.append('\\'); break;
                    case '/': sb.append('/'); break;
                    case 'b': sb.append('\b'); break;
                    case 'f': sb.append('\f'); break;
                    case 'n': sb.append('\n'); break;
                    case 'r': sb.append('\r'); break;
                    case 't': sb.append('\t'); break;
                    default: throw new IOException("Unexpected escape character: " + (char) ch);
                }
            } else {
                sb.append((char) ch);
            }
        }
        return sb.toString();
    }

    private static Boolean parseBoolean(Reader reader) throws IOException {
        reader.mark(4);
        String bool = readString(reader, 4);
        if ("true".equals(bool)) {
            return true;
        } else if ("false".equals(bool)) {
            return false;
        } else {
            throw new IOException("Unexpected boolean value: " + bool);
        }
    }

    private static Object parseNull(Reader reader) throws IOException {
        reader.mark(4);
        String nullValue = readString(reader, 4);
        if ("null".equals(nullValue)) {
            return null;
        } else {
            throw new IOException("Unexpected null value: " + nullValue);
        }
    }

    private static Number parseNumber(Reader reader) throws IOException {
        StringBuilder sb = new StringBuilder();
        int ch;
        while ((ch = reader.read()) != -1 && (Character.isDigit(ch) || ch == '-' || ch == '.')) {
            sb.append((char) ch);
        }
        reader.unread(ch);
        String numStr = sb.toString();
        try {
            if (numStr.contains(".")) {
                return Double.parseDouble(numStr);
            } else {
                return Long.parseLong(numStr);
            }
        } catch (NumberFormatException e) {
            throw new IOException("Invalid number format: " + numStr, e);
        }
    }

    private static void skipWhitespace(Reader reader) throws IOException {
        int ch;
        while (Character.isWhitespace(ch = reader.read())) {
            // Skip whitespace
        }
        reader.unread(ch);
    }

    private static String readString(Reader reader, int length) throws IOException {
        char[] buffer = new char[length];
        reader.read(buffer);
        return new String(buffer);
    }

    public static void main(String[] args) {
        String json = "{\"name\":\"John\",\"age\":30,\"isStudent\":false,\"courses\":[\"Math\",\"Science\"]}";
        try {
            Object result = parse(json);
            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
