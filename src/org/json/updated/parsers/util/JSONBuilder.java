package org.json.updated.parsers.util;

public class JSONBuilder {
    private String value = "";

    public JSONBuilder() {
        this.value += "{";
    }

    public JSONBuilder value(String key, String value) {
        this.value += "\"" + key + "\": \"" + value + "\",";
        return this;
    }

    public JSONBuilder array(String key, Object... values) {
        this.value += "\"" + key + "\": [";
        for (Object object : values) {
            this.value += object.toString();
        }

        this.value += "]";
        return this;
    }

    public String build() {
        if (this.value.endsWith(",")) {
            this.value = this.value.substring(0, this.value.length() - 1);
        }

        if (!this.value.endsWith("}")) {
            this.value += "}";
        }

        return this.value;
    }

    public static class Object {
        private final String key, value;

        public Object(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return "{\"" + this.key + "\":\"" + this.value + "\"}";
        }
    }
}
