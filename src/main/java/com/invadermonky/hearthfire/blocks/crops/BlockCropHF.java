package com.invadermonky.hearthfire.blocks.crops;

import com.invadermonky.hearthfire.Hearthfire;
import com.invadermonky.hearthfire.api.properties.blocks.crops.CropProperties;
import com.invadermonky.hearthfire.client.gui.CreativeTabsHF;
import com.invadermonky.hearthfire.util.MathUtils;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockCropHF extends AbstractBlockCropHF<CropProperties> {

    public BlockCropHF(String unlocName, String modId, CreativeTabs creativeTab, CropProperties properties) {
        super(unlocName, modId, creativeTab, properties);
    }

    /** Internal constructor. Used only for Hearthfire blocks. */
    public BlockCropHF(String unlocName, CropProperties properties) {
        this(unlocName, Hearthfire.MOD_ID, CreativeTabsHF.TAB_FARM_AND_FEAST, properties);
    }

    @Override
    public void handleCropGrowth(World world, BlockPos pos, IBlockState state, int age) {
        world.setBlockState(pos, state.withProperty(AGE, MathUtils.clamp(age, 0, this.getMaxAge())));
    }
}
