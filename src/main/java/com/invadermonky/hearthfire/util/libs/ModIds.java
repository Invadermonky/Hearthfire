package com.invadermonky.hearthfire.util.libs;

import com.invadermonky.hearthfire.util.helpers.ModHelper;

import javax.annotation.Nullable;

public enum ModIds {
    crafttweaker(ConstIds.crafttweaker),
    groovyscript(ConstIds.groovyscript);

    public final String modId;
    public final String version;
    public final boolean isLoaded;

    ModIds(String modId, @Nullable String version, boolean isMinVersion, boolean isMaxVersion) {
        this.modId = modId;
        this.version = version;
        this.isLoaded = ModHelper.isModLoaded(modId, version, isMinVersion, isMaxVersion);
    }

    ModIds(String modId, @Nullable String version) {
        this.modId = modId;
        this.version = version;
        this.isLoaded = ModHelper.isModLoaded(modId, version);
    }

    ModIds(String modId) {
        this(modId, null);
    }

    @Override
    public String toString() {
        return this.modId;
    }

    public static class ConstIds {
        public static final String crafttweaker = "crafttweaker";
        public static final String groovyscript = "groovyscript";
    }

    public static class ConstVersions {

    }
}
