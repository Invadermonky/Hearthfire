package com.invadermonky.hearthfire.api.blocks.properties;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class CropProperties {
    private final String cropId;
    private final String seedId;
    private Item cropItem;
    private Item seedItem;

    public CropProperties(CropBuilder builder) {
        this.cropId = builder.cropId;
        this.seedId = builder.seedId;
    }

    public Item getCrop() {
        if(this.cropItem == null || this.cropItem == Items.AIR) {
            this.cropItem = this.getRegisteredItem(this.cropId);
        }
        return this.cropItem;
    }

    public Item getSeed() {
        if(this.seedItem == null || this.seedItem == Items.AIR) {
            this.seedItem = this.getRegisteredItem(this.seedId);
        }
        return this.seedItem;
    }

    private Item getRegisteredItem(String itemId) {
        Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(itemId));
        return item != null ? item : Items.AIR;
    }


    public static class CropBuilder {
        protected String cropId;
        protected String seedId;

        public CropBuilder(String cropId, String seedId) {
            this.cropId = cropId;
            this.seedId = seedId;
        }

        public CropBuilder(String cropId) {
            this(cropId, cropId);
        }

        public CropProperties build() {
            return new CropProperties(this);
        }
    }
}
