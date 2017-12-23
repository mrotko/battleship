package com.example.michal.battleship.utils;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by michal on 17.12.17.
 */

public class SerializeInterfaceAdapter <T> implements JsonSerializer <T>, JsonDeserializer <T> {

    private static final String CLASSNAME = "CLASSNAME";

    private static final String DATA = "DATA";

    @Override
    public JsonElement serialize(T object, Type type, JsonSerializationContext context) {
        JsonObject wrapper = new JsonObject();
        wrapper.addProperty(CLASSNAME, object.getClass().getName());
        wrapper.add(DATA, context.serialize(object));
        return wrapper;
    }

    @Override
    public T deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject wrapper = json.getAsJsonObject();
        JsonPrimitive prim = (JsonPrimitive) wrapper.get(CLASSNAME);
        String className = prim.getAsString();
        Class clazz = getObjectClass(className);
        return context.deserialize(wrapper.get(DATA), clazz);
    }

    public Class getObjectClass(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new JsonParseException(e.getMessage());
        }
    }
}
