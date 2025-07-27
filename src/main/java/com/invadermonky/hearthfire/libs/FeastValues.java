package com.invadermonky.hearthfire.libs;

import net.minecraft.util.math.AxisAlignedBB;

public class FeastValues {
    /** Used for pie shaped feasts. 12 x 12 x 4 pixel dimensions. */
    public static final AxisAlignedBB PIE_AABB = new AxisAlignedBB(0.125, 0.0, 0.125, 0.875, 0.25, 0.875);

    public static final AxisAlignedBB GOURD_AABB = new AxisAlignedBB(0., 0.0, 0., 0., 0.25, 0.);

    public static final AxisAlignedBB SMALL_ROAST_AABB = new AxisAlignedBB(0.25, 0.0, 0.25, 0.75, 0.4375, 0.75);
    public static final AxisAlignedBB LARGE_ROAST_AABB = new AxisAlignedBB(0.25, 0.0, 0.25, 0.75, 0.50, 0.75);

}
