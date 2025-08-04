package com.invadermonky.hearthfire.api.properties.base;

public abstract class PropertiesBuilder<T extends PropertiesBuilder<T, S>, S extends ObjectProperties<T, S>> {
    @SuppressWarnings("unchecked")
    public T getThis() {
        return (T) this;
    }

    public abstract S build();
}
