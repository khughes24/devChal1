package com.appBacky.model;

import org.bson.Document;
import org.bson.json.JsonWriterSettings;

public class JSONConverter {

    /**
     * Converts any doc to a json
     * @param doc
     * @return
     */
    public String convertDoctoJson(Document doc){


        String json = doc.toJson();
        return json;
    }
}
