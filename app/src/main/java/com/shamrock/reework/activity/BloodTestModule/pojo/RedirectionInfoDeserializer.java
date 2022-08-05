package com.shamrock.reework.activity.BloodTestModule.pojo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.shamrock.reework.activity.BloodTestModule.pojo.comparereport.ClsCompareData;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RedirectionInfoDeserializer implements JsonDeserializer<ClsCompareData> {
    private static final String Message = "Message";
    private static final int Code =0;
    private ArrayList<ClsCompareData> Data;


    @Override
    public ClsCompareData deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        final JsonObject jsonObject = json.getAsJsonObject();

        // Read simple String values.
        final String uri = jsonObject.get("Code").getAsString();
        final String httpMethod = jsonObject.get("Message").getAsString();

        // Read the dynamic parameters object.
        final Map<String, String> parameters = readParametersMap(jsonObject);

        ClsCompareData result = new ClsCompareData();
        result.setCode(uri);
        result.setMessage(httpMethod);
        result.setData(parameters);
        return result;
    }
    @Nullable
    private Map<String, String> readParametersMap(@NonNull final JsonObject jsonObject) {
        final JsonElement paramsElement = jsonObject.get("Data");
        if (paramsElement == null) {
            // value not present at all, just return null
            return null;
        }

        final JsonObject parametersObject = paramsElement.getAsJsonObject();
        final Map<String, String> parameters = new HashMap<>();
        for (Map.Entry<String, JsonElement> entry : parametersObject.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue().getAsString();
            parameters.put(key, value);
        }
        return parameters;
    }
}
