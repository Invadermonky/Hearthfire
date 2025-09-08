package com.invadermonky.hearthfire.core;

import net.minecraftforge.common.ForgeVersion;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import org.jetbrains.annotations.Nullable;
import zone.rong.mixinbooter.IEarlyMixinLoader;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@IFMLLoadingPlugin.Name("HearthfireLoaderCore")
@IFMLLoadingPlugin.MCVersion(ForgeVersion.mcVersion)
public class EarlyMixinLoaderHF implements IEarlyMixinLoader, IFMLLoadingPlugin {
    @Override
    public List<String> getMixinConfigs() {
        return Collections.singletonList("mixins.hearthfire.json");
    }

    @Override
    public String[] getASMTransformerClass() {
        return new String[0];
    }

    @Override
    public String getModContainerClass() {
        return null;
    }

    @Override
    public @Nullable String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {}

    @Override
    public String getAccessTransformerClass() {
        return null;
    }
}
