package com.api.google;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DirectionsParser {

    public List<HashMap<String, String>> parse(JSONObject jObject) {

        List<HashMap<String, String>> routes = new ArrayList<>();
        JSONArray jRoutes = null;
        JSONArray jLegs = null;
        JSONArray jSteps = null;

        try {

            jRoutes = jObject.getJSONArray("routes");

            for (int i = 0; i < jRoutes.length(); i++) {
                jLegs = ((JSONObject) jRoutes.get(i)).getJSONArray("legs");

                for (int j = 0; j < jLegs.length(); j++) {
                    jSteps = ((JSONObject) jLegs.get(j)).getJSONArray("steps");

                    for (int k = 0; k < jSteps.length(); k++) {
                        String lat = "";
                        String lng = "";
                        JSONObject lv1 = (JSONObject) jSteps.get(k);
                        JSONObject lv2 = (JSONObject) lv1.get("end_location");
                        HashMap<String, String> hm = new HashMap<>();
                        hm.put("lat", lv2.get("lat").toString());
                        hm.put("lng", lv2.get("lng").toString());
                        routes.add(hm);
                    }
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
        }
        return routes;
    }
}
