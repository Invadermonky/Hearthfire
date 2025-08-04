package com.invadermonky.hearthfire.api.properties.base;

public abstract class ObjectProperties<T extends PropertiesBuilder<T, S>, S extends ObjectProperties<T, S>> {
    public ObjectProperties(T builder) {}
}
