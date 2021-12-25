package org.json.updated.parsers.util;

public final class JSONBuilder {
    private String value = "";

    public JSONBuilder() {
        this.value += "{";
    }

    public JSONBuilder value(String key, String value) {
        this.value += "\"" + key + "\": \"" + value + "\",";
        return this;
    }

    public String build() {
        if (this.value.endsWith(",")) this.value = this.value.substring(0, this.value.length() - 1);

        if (!this.value.endsWith("}")) this.value += "}";

        return this.value;
    }

}
